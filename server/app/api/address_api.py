from flask import Blueprint, request, jsonify
from app.service.address_service import AddressService

address_bp = Blueprint("address_api", __name__)


@address_bp.route("/id")
def get_by_id():
    address_id = request.args.get('id')
    return jsonify(AddressService.get_by_id(address_id)), 200


@address_bp.route("/user")
def get_by_user_id():
    user_id = request.args.get('id')
    return jsonify(AddressService.get_by_user_id(user_id)), 200


@address_bp.route("/add", methods=['POST'])
def add():
    data = request.get_json()
    return jsonify(AddressService.add(data)), 201


@address_bp.route("/delete", methods=['DELETE'])
def delete_by_id():
    address_id = request.args.get('id')
    return jsonify(AddressService.delete_by_id(address_id)), 204


@address_bp.route("/update", methods=['PUT'])
def update():
    data = request.get_json()
    return jsonify(AddressService.update(data)), 200
