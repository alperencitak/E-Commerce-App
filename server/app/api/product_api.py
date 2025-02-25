from http import HTTPStatus

from flask.views import MethodView
from flask import request
from flask_smorest import Blueprint
from app.service.product_service import ProductService
from app.model.product import ProductSchema, PaginationSchema

product_bp = Blueprint("products", "products", url_prefix="/product", description="Product Management API")


@product_bp.route("/<int:product_id>")
class ProductResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, ProductSchema)
    def get(self, product_id):
        return ProductService.get_by_id(product_id)


@product_bp.route("/category/<int:category_id>")
class ProductWithCategoryResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, PaginationSchema)
    def get(self, category_id):
        page = request.args.get("page", default=1, type=int)
        per_page = request.args.get("per_page", default=10, type=int)
        return ProductService.get_by_category_id(category_id, page, per_page)


@product_bp.route("/bestsellers")
class ProductFilterBySalesAmountResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, ProductSchema(many=True))
    def get(self):
        return ProductService.filter_by_sales_amount()


@product_bp.route("/search")
class SearchResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, PaginationSchema)
    def get(self):
        key = request.args.get("key")
        page = request.args.get("page", default=1, type=int)
        per_page = request.args.get("per_page", default=10, type=int)
        return ProductService.search(key, page, per_page)


@product_bp.route("/add")
class AddProductResponse(MethodView):
    @product_bp.response(HTTPStatus.CREATED, ProductSchema)
    def post(self):
        data = request.form.to_dict()
        file = request.files.get("file")
        return ProductService.add(data, file)


@product_bp.route("/delete/<int:product_id>")
class DeleteProductResponse(MethodView):
    @product_bp.response(HTTPStatus.NO_CONTENT)
    def delete(self, product_id):
        ProductService.delete_by_id(product_id)
        return ""


@product_bp.route("/update")
class UpdateProductResponse(MethodView):
    @product_bp.response(HTTPStatus.OK, ProductSchema)
    def put(self):
        data = request.form.to_dict()
        file = request.files.get("file")
        return ProductService.update(data, file)
