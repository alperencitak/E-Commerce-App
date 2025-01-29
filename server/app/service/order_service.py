from marshmallow import ValidationError

from app.model.order import Order, OrderSchema
from app import db


class OrderService:
    @staticmethod
    def get_by_id(order_id):
        order = Order.query.filter_by(order_id=order_id).first()
        if not order:
            return ValueError(f"Order not found by id: {order_id}")
        order_schema = OrderSchema()
        return order_schema.dump(order)

    @staticmethod
    def get_by_user_id(user_id):
        orders = Order.query.filter_by(user_id=user_id).all()
        if not orders:
            return ValueError(f"Orders not found by user id: {user_id}")
        order_schema = OrderSchema(many=True)
        return order_schema.dump(orders)

    @staticmethod
    def add(data):
        order_schema = OrderSchema()

        try:
            order = order_schema.load(data)
        except ValidationError as e:
            return {"errors": e.messages}, 400

        db.session.add(order)
        db.session.commit()

        return {"message": "Order added."}

    @staticmethod
    def delete_by_id(order_id):
        order = Order.query.filter_by(order_id=order_id).first()
        if not order:
            return ValueError(f"Order not found by id: {order_id}")

        db.session.delete(order)
        db.session.commit()

        return {"message": "Order removed."}

    @staticmethod
    def update(data):
        order_id = data.get('order_id')
        order = Order.query.filter_by(order_id=order_id).first()
        if not order:
            return ValueError(f"Order not found by id: {order_id}")

        for key, value in data.items():
            if key != 'order_id' and hasattr(order, key):
                setattr(order, key, value)

        db.session.commit()

        return {"message": "Order updated."}
