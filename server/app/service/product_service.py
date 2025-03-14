from werkzeug.exceptions import NotFound

from app.model.product import Product, ProductSchema
from app import db
from app.service.image_service import ImageService
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np


class ProductService:
    product_schema = ProductSchema()
    products_schema = ProductSchema(many=True)

    @classmethod
    def get_all_products(cls, page=1, per_page=100):
        query = db.select(Product)
        products = db.paginate(query, page=page, per_page=per_page, error_out=False)
        return products.items

    @classmethod
    def generate_tfidf_matrix(cls, all_products):
        product_texts = [f"{product.name} {product.sales_amount} {product.price}" for product in all_products]
        vectorizer = TfidfVectorizer(stop_words='english')
        tfidf_matrix = vectorizer.fit_transform(product_texts)
        return tfidf_matrix

    @classmethod
    def recommend_products(cls, product_id, top_n=10):
        all_products = cls.get_all_products()
        tfidf_matrix = cls.generate_tfidf_matrix(all_products)
        cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)
        product_idx = next((i for i, product in enumerate(all_products) if product.product_id == product_id), None)
        if product_idx is None:
            raise NotFound(f"Product not found by id: {product_id}")
        sim_scores = list(enumerate(cosine_sim[product_idx]))
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)

        recommended_products = []
        for i, score in sim_scores[1:top_n + 1]:
            recommended_products.append(cls.product_schema.dump(all_products[i]))

        return recommended_products

    @classmethod
    def get_by_id(cls, product_id):
        product = db.session.get(Product, product_id)
        if not product:
            return NotFound(f"Product not found by id: {product_id}")

        return cls.product_schema.dump(product)

    @classmethod
    def get_by_category_id(cls, category_id, page=1, per_page=10):
        query = db.select(Product).where(Product.category_id == category_id)
        products = db.paginate(query, page=page, per_page=per_page, error_out=False)
        return {
            "products": cls.products_schema.dump(products.items),
            "total": products.total,
            "pages": products.pages,
            "current_page": products.page,
            "per_page": products.per_page
        }

    @classmethod
    def filter_by_sales_amount(cls):
        query = db.select(Product).order_by(db.desc(Product.sales_amount)).limit(10)
        result = db.session.scalars(query).all()
        return cls.products_schema.dump(result)

    @classmethod
    def search(cls, key, page=1, per_page=10):
        query = db.select(Product).where(Product.name.like(f"%{key}%"))
        products = db.paginate(query, page=page, per_page=per_page, error_out=False)
        return {
            "products": cls.products_schema.dump(products.items),
            "total": products.total,
            "pages": products.pages,
            "current_page": products.page,
            "per_page": products.per_page
        }

    @classmethod
    def add(cls, data, file=None):
        image_url = None
        if file:
            image_url, error = ImageService.save_image(file)
            if error:
                return {"error": error}
        else:
            return {"error": "File required"}

        product = Product(
            name=data["name"],
            price=data["price"],
            stock=data.get("stock", 10),
            category_id=data["category_id"],
            image_url=image_url
        )

        db.session.add(product)
        db.session.commit()
        return cls.product_schema.dump(product)

    @classmethod
    def delete_by_id(cls, product_id):
        product = db.session.get(Product, product_id)
        if not product:
            return NotFound(f"Product not found by id: {product_id}")

        db.session.delete(product)
        db.session.commit()

        return {"message": "Product removed."}

    @classmethod
    def update(cls, data, file=None):
        image_url = None
        if file:
            image_url, error = ImageService.save_image(file)
            if error:
                return {"error": error}
        else:
            return {"error": "File required"}

        product_id = data.product_id
        product = db.session.get(Product, product_id)
        if not product:
            return NotFound(f"Product not found by id: {product_id}")

        for key, value in data.__dict__.items():
            if hasattr(product, key):
                setattr(product, key, value)

        db.session.commit()

        return cls.product_schema.dump(product)
