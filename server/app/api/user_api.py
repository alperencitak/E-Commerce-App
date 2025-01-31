from flask.views import MethodView
from werkzeug.exceptions import NotFound
from flask import abort
from app.service.user_service import UserService
from app.model.user import UserSchema
from flask_smorest import Blueprint
from http import HTTPStatus

user_bp = Blueprint("users", "users", url_prefix="/user", description="User management API")


@user_bp.route("/")
class UserListResource(MethodView):
    @user_bp.response(HTTPStatus.OK, UserSchema(many=True))
    def get(self):
        return UserService.get_all()


@user_bp.route("/<int:user_id>")
class UserResource(MethodView):
    @user_bp.response(HTTPStatus.OK, UserSchema)
    def get(self, user_id):
        try:
            return UserService.get_by_id(user_id)
        except NotFound as e:
            abort(404,str(e))

