from flask import Blueprint, request, jsonify
from app.service.order_detail_service import OrderDetailService

order_detail_bp = Blueprint("order_detail_api", __name__)


@order_detail_bp.route("/id")
def get_by_id():
    order_detail_id = request.args.get('id')
    return jsonify(OrderDetailService.get_by_id(order_detail_id)), 200


@order_detail_bp.route("/order")
def get_by_order_id():
    order_id = request.args.get('id')
    return jsonify(OrderDetailService.get_by_order_id(order_id)), 200


@order_detail_bp.route("/product")
def get_by_product_id():
    product_id = request.args.get('id')
    return jsonify(OrderDetailService.get_by_product_id(product_id)), 200


@order_detail_bp.route("/add", methods=['POST'])
def add():
    data = request.get_json()
    return jsonify(OrderDetailService.add(data)), 201


@order_detail_bp.route("/delete", methods=['DELETE'])
def delete_by_id():
    order_detail_id = request.args.get('id')
    return jsonify(OrderDetailService.delete_by_id(order_detail_id)), 204


@order_detail_bp.route("/update", methods=['PUT'])
def update():
    data = request.get_json()
    return jsonify(OrderDetailService.update(data)), 200
