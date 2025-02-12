from marshmallow import fields

from app import db, ma


class Product(db.Model):
    __tablename__ = "products"

    product_id = db.Column("product_id", db.Integer, primary_key=True, autoincrement=True)
    name = db.Column("name", db.String(150), nullable=False)
    price = db.Column("price", db.Numeric(10, 2), nullable=False)
    stock = db.Column("stock", db.Integer, default=10, nullable=False)
    category_id = db.Column("category_id", db.Integer, db.ForeignKey("categories.category_id"), nullable=False)
    created_at = db.Column("created_at", db.DateTime, default=db.func.now())
    updated_at = db.Column("updated_at", db.DateTime, default=db.func.now(), onupdate=db.func.now())


class ProductSchema(ma.SQLAlchemyAutoSchema):
    created_at = fields.String()
    updated_at = fields.String()
    class Meta:
        model = Product
        include_fk = True
        load_instance = True
