from marshmallow import ValidationError

from app.model.product import Product, ProductSchema
from app import db


class ProductService:
    @staticmethod
    def get_by_id(product_id):
        product = Product.query.filter_by(product_id=product_id).first()
        if not product:
            return ValueError(f"Product not found by id: {product_id}")
        product_schema = ProductSchema()
        return product_schema.dump(product)

    @staticmethod
    def get_by_category_id(category_id):
        product = Product.query.filter_by(category_id=category_id).first()
        if not product:
            return ValueError(f"Product not found by category id: {category_id}")
        product_schema = ProductSchema()
        return product_schema.dump(product)

    @staticmethod
    def add(data):
        product_schema = ProductSchema()

        try:
            product = product_schema.load(data)
        except ValidationError as e:
            return {"errors": e.messages}, 400

        db.session.add(product)
        db.session.commit()

        return {"message": "Product added."}

    @staticmethod
    def delete_by_id(product_id):
        product = Product.query.filter_by(product_id=product_id).first()
        if not product:
            return ValueError(f"Product not found by id: {product_id}")

        db.session.delete(product)
        db.session.commit()

        return {"message": "Product removed."}

    @staticmethod
    def update(data):
        product_id = data.get('product_id')
        product = Product.query.filter_by(product_id=product_id).first()
        if not product:
            return ValueError(f"Product not found by id: {product_id}")

        for key, value in data.items():
            if key != 'product_id' and hasattr(product, key):
                setattr(product, key, value)

        db.session.commit()

        return {"message": "Product updated."}
