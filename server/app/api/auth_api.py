from flask.views import MethodView
from werkzeug.exceptions import NotFound
from flask import abort

from app.service.auth_service import AuthService
from app.service.user_service import UserService
from app.model.user import UserSchema, LoginSchema, RegisterSchema
from flask_smorest import Blueprint
from http import HTTPStatus

auth_bp = Blueprint("auths", "auths", url_prefix="/auth", description="Authentication API")


@auth_bp.route("/login")
class LoginResource(MethodView):
    @auth_bp.response(HTTPStatus.OK, UserSchema)
    @auth_bp.arguments(LoginSchema)
    def post(self, data):
        try:
            return AuthService.login(data)
        except NotFound:
            return abort(HTTPStatus.UNAUTHORIZED, description="Invalid email or password")


@auth_bp.route("/register")
class RegisterResource(MethodView):
    @auth_bp.response(HTTPStatus.OK, UserSchema)
    @auth_bp.arguments(RegisterSchema)
    def post(self, data):
        return AuthService.register(data)
