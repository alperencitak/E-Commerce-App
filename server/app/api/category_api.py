from http import HTTPStatus

from flask.views import MethodView
from flask_smorest import Blueprint
from app.service.category_service import CategoryService
from app.model.category import CategorySchema

category_bp = Blueprint("categories", "categories", url_prefix="/category", description="Category Management API")


@category_bp.route("/")
class CategoriesResponse(MethodView):
    @category_bp.response(HTTPStatus.OK, CategorySchema(many=True))
    def get(self):
        return CategoryService.get_all()


@category_bp.route("/<int:category_id>")
class CategoryResponse(MethodView):
    @category_bp.response(HTTPStatus.OK, CategorySchema)
    def get(self, category_id):
        return CategoryService.get_by_id(category_id)


@category_bp.route("/add")
class AddCategoryResponse(MethodView):
    @category_bp.response(HTTPStatus.CREATED, CategorySchema)
    @category_bp.arguments(CategorySchema)
    def post(self, data):
        return CategoryService.add(data)


@category_bp.route("/delete/<int:category_id>")
class DeleteCategoryResponse(MethodView):
    @category_bp.response(HTTPStatus.NO_CONTENT)
    def delete(self, category_id):
        CategoryService.delete_by_id(category_id)
        return ""


@category_bp.route("/update")
class UpdateCategoryResponse(MethodView):
    @category_bp.response(HTTPStatus.OK, CategorySchema)
    @category_bp.arguments(CategorySchema)
    def put(self, data):
        return CategoryService.update(data)
