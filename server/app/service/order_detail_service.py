from marshmallow import ValidationError
from werkzeug.exceptions import NotFound

from app.model.order_detail import OrderDetail, OrderDetailSchema
from app import db


class OrderDetailService:
    order_detail_schema = OrderDetailSchema()
    order_details_schema = OrderDetailSchema(many=True)

    @classmethod
    def get_by_id(cls, order_detail_id):
        order_detail = db.session.get(OrderDetail, order_detail_id)
        if not order_detail:
            return NotFound(f"Order details not found by id: {order_detail_id}")
        return cls.order_detail_schema.dump(order_detail)

    @classmethod
    def get_by_order_id(cls, order_id):
        order_details = db.session.scalars(db.select(OrderDetail).where(OrderDetail.order_id == order_id)).all()
        return cls.order_details_schema.dump(order_details)

    @classmethod
    def get_by_product_id(cls, product_id):
        order_details = db.session.scalars(db.select(OrderDetail).where(OrderDetail.product_id == product_id)).all()
        return cls.order_details_schema.dump(order_details)

    @classmethod
    def add(cls, data):
        db.session.add(data)
        db.session.commit()
        return cls.order_detail_schema.dump(data)

    @classmethod
    def delete_by_id(cls, order_detail_id):
        order_detail = db.session.get(OrderDetail, order_detail_id)
        if not order_detail:
            return NotFound(f"Order details not found by id: {order_detail_id}")

        db.session.delete(order_detail)
        db.session.commit()

        return {"message": "Order details removed."}

    @classmethod
    def update(cls, data):
        order_detail_id = data.order_detail_id
        order_detail = db.session.get(OrderDetail, order_detail_id)
        if not order_detail:
            return NotFound(f"Order details not found by id: {order_detail_id}")

        for key, value in data.__dict__.items():
            if hasattr(order_detail, key):
                setattr(order_detail, key, value)

        db.session.commit()

        return cls.order_detail_schema.dump(data)
