from werkzeug.exceptions import NotFound

from app.model.address import Address, AddressSchema
from app import db


class AddressService:
    address_schema = AddressSchema()
    addresses_schema = AddressSchema(many=True)

    @classmethod
    def get_by_id(cls, address_id):
        address = db.session.get(Address, address_id)
        if not address:
            return NotFound(f"Address not found by id: {address_id}")
        return cls.address_schema.dump(address)

    @classmethod
    def get_by_user_id(cls, user_id):
        addresses = db.session.scalars(db.select(Address).where(Address.user_id == user_id)).all()
        return cls.addresses_schema.dump(addresses)

    @classmethod
    def add(cls, data):
        db.session.add(data)
        db.session.commit()

        return cls.address_schema.dump(data)

    @classmethod
    def delete_by_id(cls, address_id):
        address = db.session.get(Address, address_id)
        if not address:
            return NotFound(f"Address not found by id: {address_id}")

        db.session.delete(address)
        db.session.commit()

        return {"message": "Address removed."}

    @classmethod
    def update(cls, data):
        address_id = data.address_id
        address = db.session.get(Address, address_id)
        if not address:
            return NotFound(f"Address not found by id: {address_id}")

        for key, value in data.__dict__.items():
            if hasattr(address, key):
                setattr(address, key, value)

        db.session.commit()

        return cls.address_schema.dump(address)
