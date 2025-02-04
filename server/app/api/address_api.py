from flask_smorest import Blueprint
from app.service.address_service import AddressService
from http import HTTPStatus
from app.model.address import AddressSchema
from flask.views import MethodView

address_bp = Blueprint("addresses", "addresses", url_prefix="/address", description="Address Management API")


@address_bp.route("/<int:address_id>")
class AddressResponse(MethodView):
    @address_bp.response(HTTPStatus.OK, AddressSchema)
    def get(self, address_id):
        return AddressService.get_by_id(address_id)


@address_bp.route("/user/<int:user_id>")
class AddressesResponse(MethodView):
    @address_bp.response(HTTPStatus.OK, AddressSchema(many=True))
    def get(self, user_id):
        return AddressService.get_by_user_id(user_id)


@address_bp.route("/add")
class AddAddressResponse(MethodView):
    @address_bp.response(HTTPStatus.CREATED, AddressSchema)
    @address_bp.arguments(AddressSchema)
    def post(self, data):
        return AddressService.add(data)


@address_bp.route("/delete/<int:address_id>")
class DeleteAddressResponse(MethodView):
    @address_bp.response(HTTPStatus.NO_CONTENT)
    def delete(self, address_id):
        AddressService.delete_by_id(address_id)
        return ""


@address_bp.route("/update")
class UpdateAddressResponse(MethodView):
    @address_bp.response(HTTPStatus.OK, AddressSchema)
    @address_bp.arguments(AddressSchema)
    def put(self, data):
        return AddressService.update(data)
