from werkzeug.exceptions import NotFound

from app.model.order import Order, OrderSchema
from app import db


class OrderService:
    order_schema = OrderSchema()
    orders_schema = OrderSchema(many=True)

    @classmethod
    def get_by_id(cls, order_id):
        order = db.session.get(Order, order_id)
        if not order:
            return NotFound(f"Order not found by id: {order_id}")
        return cls.order_schema.dump(order)

    @classmethod
    def get_by_user_id(cls, user_id):
        orders = db.session.scalars(db.select(Order).where(Order.user_id == user_id)).all()
        return cls.orders_schema.dump(orders)

    @classmethod
    def add(cls, data):
        db.session.add(data)
        db.session.commit()
        return cls.order_schema.dump(data)

    @classmethod
    def delete_by_id(cls, order_id):
        order = db.session.get(Order, order_id)
        if not order:
            return NotFound(f"Order not found by id: {order_id}")

        db.session.delete(order)
        db.session.commit()

        return {"message": "Order removed."}

    @classmethod
    def update(cls, data):
        order_id = data.order_id
        order = db.session.get(Order, order_id)
        if not order:
            return NotFound(f"Order not found by id: {order_id}")

        for key, value in data.__dict__.items():
            if hasattr(order, key):
                setattr(order, key, value)

        db.session.commit()

        return cls.order_schema.dump(data)
