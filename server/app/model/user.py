from app import db, ma
from marshmallow import fields


class User(db.Model):
    __tablename__ = "users"

    user_id = db.Column("user_id", db.Integer, primary_key=True, autoincrement=True)
    first_name = db.Column("first_name", db.String(100), nullable=False)
    last_name = db.Column("last_name", db.String(100), nullable=False)
    email = db.Column("email", db.String(150), nullable=False)
    phone = db.Column("phone", db.String(15), nullable=True)
    created_at = db.Column("created_at", db.DateTime, default=db.func.now())


class UserSchema(ma.SQLAlchemyAutoSchema):
    created_at = fields.String()

    class Meta:
        model = User
        load_instance = True
