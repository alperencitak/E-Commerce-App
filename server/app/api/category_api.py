from flask import Blueprint, request, jsonify
from app.service.category_service import CategoryService

category_bp = Blueprint("category_api", __name__)


@category_bp.route("/all")
def get_all():
    return jsonify(CategoryService.get_all()), 200


@category_bp.route("/id")
def get_by_id():
    category_id = request.args.get('id')
    return jsonify(CategoryService.get_by_id(category_id)), 200


@category_bp.route("/add", methods=['POST'])
def add():
    data = request.get_json()
    return jsonify(CategoryService.add(data)), 201


@category_bp.route("/delete", methods=['DELETE'])
def delete_by_id():
    category_id = request.args.get('id')
    return jsonify(CategoryService.delete_by_id(category_id)), 204


@category_bp.route("/update", methods=['PUT'])
def update():
    data = request.get_json()
    return jsonify(CategoryService.update(data)), 200
