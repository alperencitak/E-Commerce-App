from flask import Blueprint, request, jsonify
from app.service.product_service import ProductService

product_bp = Blueprint("product_api", __name__)


@product_bp.route("/id")
def get_by_id():
    product_id = request.args.get('id')
    return jsonify(ProductService.get_by_id(product_id)), 200


@product_bp.route("/category")
def get_by_category_id():
    category_id = request.args.get('id')
    return jsonify(ProductService.get_by_category_id(category_id)), 200


@product_bp.route("/add", methods=['POST'])
def add():
    data = request.get_json()
    return jsonify(ProductService.add(data)), 201


@product_bp.route("/delete", methods=['DELETE'])
def delete_by_id():
    product_id = request.args.get('id')
    return jsonify(ProductService.delete_by_id(product_id)), 204


@product_bp.route("/update", methods=['PUT'])
def update():
    data = request.get_json()
    return jsonify(ProductService.update(data)), 200
