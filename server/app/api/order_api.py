from flask import Blueprint, request, jsonify
from app.service.order_service import OrderService

order_bp = Blueprint("order_api", __name__)


@order_bp.route("/id")
def get_by_id():
    order_id = request.args.get('id')
    return jsonify(OrderService.get_by_id(order_id)), 200


@order_bp.route("/user")
def get_by_user_id():
    user_id = request.args.get('id')
    return jsonify(OrderService.get_by_user_id(user_id)), 200


@order_bp.route("/add", methods=['POST'])
def add():
    data = request.get_json()
    return jsonify(OrderService.add(data)), 201


@order_bp.route("/delete", methods=['DELETE'])
def delete_by_id():
    order_id = request.args.get('id')
    return jsonify(OrderService.delete_by_id(order_id)), 204


@order_bp.route("/update", methods=['PUT'])
def update():
    data = request.get_json()
    return jsonify(OrderService.update(data)), 200
