from marshmallow import ValidationError
from app.model.order_detail import OrderDetail, OrderDetailSchema
from app import db


class OrderDetailService:
    @staticmethod
    def get_by_id(order_detail_id):
        order_detail = OrderDetail.query.filter_by(order_detail_id=order_detail_id).first()
        if not order_detail:
            return ValueError(f"Order details not found by id: {order_detail_id}")
        order_detail_schema = OrderDetailSchema()
        return order_detail_schema.dump(order_detail)

    @staticmethod
    def get_by_order_id(order_id):
        order_details = OrderDetail.query.filter_by(order_id=order_id).all()
        if not order_details:
            return ValueError(f"Order details not found for order id: {order_id}")
        order_detail_schema = OrderDetailSchema(many=True)
        return order_detail_schema.dump(order_details)

    @staticmethod
    def get_by_product_id(product_id):
        order_details = OrderDetail.query.filter_by(product_id=product_id).all()
        if not order_details:
            return ValueError(f"Order details not found for product id: {product_id}")
        order_detail_schema = OrderDetailSchema(many=True)
        return order_detail_schema.dump(order_details)

    @staticmethod
    def add(data):
        order_detail_schema = OrderDetailSchema()

        try:
            order_detail = order_detail_schema.load(data)
        except ValidationError as e:
            return {"errors": e.messages}, 400

        db.session.add(order_detail)
        db.session.commit()

        return {"message": "Order details added."}

    @staticmethod
    def delete_by_id(order_detail_id):
        order_detail = OrderDetail.query.filter_by(order_detail_id=order_detail_id).first()
        if not order_detail:
            return ValueError(f"Order details not found by id: {order_detail_id}")

        db.session.delete(order_detail)
        db.session.commit()

        return {"message": "Order details removed."}

    @staticmethod
    def update(data):
        order_detail_id = data.get('order_detail_id')
        order_detail = OrderDetail.query.filter_by(order_detail_id=order_detail_id).first()
        if not order_detail:
            return ValueError(f"Order details not found by id: {order_detail_id}")

        for key, value in data.items():
            if key != 'order_detail_id' and hasattr(order_detail, key):
                setattr(order_detail, key, value)

        db.session.commit()

        return {"message": "Order details updated."}
