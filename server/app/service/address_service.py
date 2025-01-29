from marshmallow import ValidationError

from app.model.address import Address, AddressSchema
from app import db


class AddressService:
    @staticmethod
    def get_by_id(address_id):
        address = Address.query.filter_by(address_id=address_id).first()
        if not address:
            return ValueError(f"Address not found by id: {address_id}")
        address_schema = AddressSchema()
        return address_schema.dump(address)

    @staticmethod
    def get_by_user_id(user_id):
        address = Address.query.filter_by(user_id=user_id).first()
        if not address:
            return ValueError(f"Address not found by user id: {user_id}")
        address_schema = AddressSchema()
        return address_schema.dump(address)

    @staticmethod
    def add(data):
        address_schema = AddressSchema()

        try:
            address = address_schema.load(data)
        except ValidationError as e:
            return {"errors": e.messages}, 400

        db.session.add(address)
        db.session.commit()

        return {"message": "Address added."}

    @staticmethod
    def delete_by_id(address_id):
        address = Address.query.filter_by(address_id=address_id).first()
        if not address:
            return ValueError(f"Address not found by id: {address_id}")

        db.session.delete(address)
        db.session.commit()

        return {"message": "Address removed."}

    @staticmethod
    def update(data):
        address_id = data.get('address_id')
        address = Address.query.filter_by(address_id=address_id).first()
        if not address:
            return ValueError(f"Address not found by id: {address_id}")

        for key, value in data.items():
            if key != 'address_id' and hasattr(address, key):
                setattr(address, key, value)

        db.session.commit()

        return {"message": "Address updated."}
