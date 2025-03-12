from werkzeug.exceptions import NotFound

from collections import defaultdict
from sqlalchemy import text
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
    def get_by_id_with_join(cls, user_id):
        query = text("""
            SELECT o.order_id, o.user_id, o.total_amount, o.status, o.order_date,
                    p.product_id, p.name, p.image_url, p.price
            FROM order_details od
            JOIN products p ON od.product_id = p.product_id
            JOIN orders o ON od.order_id = o.order_id
            WHERE o.user_id = :user_id
        """)
        results = db.session.execute(query, {'user_id': user_id})
        order_dict = defaultdict(lambda: {
            "order_id": None,
            "user_id": None,
            "total_amount": None,
            "status": None,
            "order_date": None,
            "products": []
        })
        for row in results:
            order_id = row.order_id
            if order_dict[order_id]["order_id"] is None:
                order_dict[order_id].update({
                    "order_id": row.order_id,
                    "user_id": row.user_id,
                    "total_amount": row.total_amount,
                    "status": row.status,
                    "order_date": row.order_date
                })

            order_dict[order_id]["products"].append({
                "product_id": row.product_id,
                "name": row.name,
                "image_url": row.image_url,
                "price": row.price
            })

        return list(order_dict.values())

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
