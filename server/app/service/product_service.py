from werkzeug.exceptions import NotFound

from app.model.product import Product, ProductSchema
from app import db
from app.service.image_service import ImageService


class ProductService:
    product_schema = ProductSchema()
    products_schema = ProductSchema(many=True)

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
