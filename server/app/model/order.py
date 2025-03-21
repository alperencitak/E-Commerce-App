from marshmallow import fields
from app import db, ma


class Order(db.Model):
    __tablename__ = "orders"

    order_id = db.Column("order_id", db.Integer, primary_key=True, autoincrement=True)
    user_id = db.Column("user_id", db.Integer, db.ForeignKey("users.user_id"), nullable=False)
    total_amount = db.Column("total_amount", db.Numeric(10, 2), nullable=False)
    status = db.Column("status", db.String(20), default="PENDING")
    order_date = db.Column("order_date", db.DateTime, default=db.func.now())


class OrderSchema(ma.SQLAlchemyAutoSchema):
    order_date = fields.String()

    class Meta:
        model = Order
        include_fk = True
        load_instance = True
