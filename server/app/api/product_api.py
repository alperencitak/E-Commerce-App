from http import HTTPStatus

from flask.views import MethodView
from flask_smorest import Blueprint
from app.service.product_service import ProductService
from app.model.product import ProductSchema

product_bp = Blueprint("products", "products", url_prefix="/product", description="Product Management API")


@product_bp.route("/<int:product_id>")
class ProductResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, ProductSchema)
    def get(self, product_id):
        return ProductService.get_by_id(product_id)


@product_bp.route("/category/<int:category_id>")
class ProductWithCategoryResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, ProductSchema(many=True))
    def get(self, category_id):
        return ProductService.get_by_category_id(category_id)


@product_bp.route("/add")
class AddProductResponse(MethodView):
    @product_bp.response(HTTPStatus.CREATED, ProductSchema)
    @product_bp.arguments(ProductSchema)
    def post(self, data):
        return ProductService.add(data)


@product_bp.route("/delete/<int:product_id>")
class DeleteProductResponse(MethodView):
    @product_bp.response(HTTPStatus.NO_CONTENT)
    def delete(self, product_id):
        ProductService.delete_by_id(product_id)
        return ""


@product_bp.route("/update")
class UpdateProductResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, ProductSchema)
    @product_bp.arguments(ProductSchema)
    def put(self, data):
        return ProductService.update(data)
