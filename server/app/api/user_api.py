from flask import Blueprint, request, jsonify
from app.service.user_service import UserService

user_bp = Blueprint("user_api", __name__)


@user_bp.route("/")
def get_all():
    return jsonify(UserService.get_all()), 200


@user_bp.route("/id")
def get_by_id():
    user_id = request.args.get('id')
    return jsonify(UserService.get_by_id(user_id)), 200
