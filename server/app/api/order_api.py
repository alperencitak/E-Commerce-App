from http import HTTPStatus

from flask.views import MethodView
from flask_smorest import Blueprint

from app.model.order import OrderSchema
from app.service.order_service import OrderService

order_bp = Blueprint("orders", "orders", url_prefix="/order", description="Order Management API")


@order_bp.route("/<int:order_id>")
class OrderResponse(MethodView):
    @order_bp.response(HTTPStatus.OK, OrderSchema)
    def get(self, order_id):
        return OrderService.get_by_id(order_id)


@order_bp.route("/user/<int:user_id>")
class OrdersResponse(MethodView):
    @order_bp.response(HTTPStatus.OK, OrderSchema(many=True))
    def get(self, user_id):
        return OrderService.get_by_user_id(user_id)


@order_bp.route("/add")
class AddOrderResponse(MethodView):
    @order_bp.response(HTTPStatus.CREATED, OrderSchema)
    @order_bp.arguments(OrderSchema)
    def post(self, data):
        return OrderService.add(data)


@order_bp.route("/delete/<int:order_id>")
class DeleteOrderResponse(MethodView):
    @order_bp.response(HTTPStatus.NO_CONTENT)
    def delete(self, order_id):
        OrderService.delete_by_id(order_id)
        return ""


@order_bp.route("/update")
class UpdateOrderResponse(MethodView):
    @order_bp.response(HTTPStatus.OK, OrderSchema)
    @order_bp.arguments(OrderSchema)
    def put(self, data):
        return OrderService.update(data)
