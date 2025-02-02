from werkzeug.exceptions import NotFound

from app.model.product import Product, ProductSchema
from app import db


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
    def get_by_category_id(cls, category_id):
        products = db.session.scalars(db.select(Product).where(Product.category_id == category_id)).all()
        return cls.product_schema.dump(products)

    @classmethod
    def add(cls, data):
        db.session.add(data)
        db.session.commit()
        return cls.product_schema.dump(data)

    @classmethod
    def delete_by_id(cls, product_id):
        product = db.session.get(Product, product_id)
        if not product:
            return NotFound(f"Product not found by id: {product_id}")

        db.session.delete(product)
        db.session.commit()

        return {"message": "Product removed."}

    @classmethod
    def update(cls, data):
        product_id = data.product_id
        product = db.session.get(Product, product_id)
        if not product:
            return NotFound(f"Product not found by id: {product_id}")

        for key, value in data.__dict__.items():
            if hasattr(product, key):
                setattr(product, key, value)

        db.session.commit()

        return cls.product_schema.dump(data)
