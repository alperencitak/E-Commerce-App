from flask_smorest import Blueprint
from app.service.order_detail_service import OrderDetailService
from app.model.order_detail import OrderDetailSchema
from http import HTTPStatus
from flask.views import MethodView

order_detail_bp = Blueprint("order_details", "order_details", url_prefix="/order_detail", description="Order Details Management API")


@order_detail_bp.route("/<int:order_detail_id>")
class OrderDetailResponse(MethodView):
    @order_detail_bp.response(HTTPStatus.OK, OrderDetailSchema)
    def get(self, order_detail_id):
        return OrderDetailService.get_by_id(order_detail_id)


@order_detail_bp.route("/order/<int:order_id>")
class OrderDetailsByOrderIdResponse(MethodView):
    @order_detail_bp.response(HTTPStatus.OK, OrderDetailSchema(many=True))
    def get(self, order_id):
        return OrderDetailService.get_by_order_id(order_id)


@order_detail_bp.route("/product/<int:product_id>")
class OrderDetailsByProductIdResponse(MethodView):
    @order_detail_bp.response(HTTPStatus.OK, OrderDetailSchema(many=True))
    def get(self, product_id):
        return OrderDetailService.get_by_product_id(product_id)


@order_detail_bp.route("/add")
class AddOrderDetailResponse(MethodView):
    @order_detail_bp.response(HTTPStatus.CREATED, OrderDetailSchema)
    @order_detail_bp.arguments(OrderDetailSchema)
    def post(self, data):
        return OrderDetailService.add(data)


@order_detail_bp.route("/add-all")
class AddOrderDetailResponse(MethodView):
    @order_detail_bp.response(HTTPStatus.CREATED, OrderDetailSchema(many=True))
    @order_detail_bp.arguments(OrderDetailSchema(many=True))
    def post(self, data):
        return OrderDetailService.add_all(data)


@order_detail_bp.route("/delete/<int:order_detail_id>")
class DeleteOrderDetailResponse(MethodView):
    @order_detail_bp.response(HTTPStatus.NO_CONTENT)
    def delete(self, order_detail_id):
        OrderDetailService.delete_by_id(order_detail_id)
        return ""


@order_detail_bp.route("/update")
class UpdateOrderDetailResponse(MethodView):
    @order_detail_bp.response(HTTPStatus.OK, OrderDetailSchema)
    @order_detail_bp.arguments(OrderDetailSchema)
    def put(self, data):
        return OrderDetailService.update(data)
