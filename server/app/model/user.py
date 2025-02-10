from app import db, ma
from marshmallow import fields


class User(db.Model):
    __tablename__ = "users"

    user_id = db.Column("user_id", db.Integer, primary_key=True, autoincrement=True)
    first_name = db.Column("first_name", db.String(100), nullable=False)
    last_name = db.Column("last_name", db.String(100), nullable=False)
    email = db.Column("email", db.String(150), nullable=False)
    phone = db.Column("phone", db.String(15), nullable=True)
    password_hash = db.Column("password_hash", db.String(255), nullable=False)
    created_at = db.Column("created_at", db.DateTime, default=db.func.now())


class UserSchema(ma.SQLAlchemyAutoSchema):
    created_at = fields.String()

    class Meta:
        model = User
        load_instance = True
        exclude = ("password_hash",)


class RegisterSchema(ma.Schema):
    first_name = fields.String(required=True)
    last_name = fields.String(required=True)
    email = fields.String(required=True)
    phone = fields.String()
    password = fields.String(required=True)


class LoginSchema(ma.Schema):
    email = fields.String(required=True)
    password = fields.String(required=True)
