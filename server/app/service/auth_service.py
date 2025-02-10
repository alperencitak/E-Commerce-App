from app.model.user import User, UserSchema
from werkzeug.exceptions import NotFound
from app import db
from passlib.hash import argon2


class AuthService:
    users_schema = UserSchema(many=True)
    user_schema = UserSchema()

    @classmethod
    def login(cls, data):
        user = db.session.scalars(db.select(User).where(User.email == data['email'])).first()
        if argon2.verify(data['password'], user.password_hash):
            return cls.user_schema.dump(user)
        return {"error": "Invalid email or password"}

    @classmethod
    def register(cls, data):
        hashed_password = argon2.hash(data['password'])
        user = User(
            first_name=data['first_name'],
            last_name=data['last_name'],
            email=data['email'],
            phone=data['phone'],
            password_hash=hashed_password
        )
        db.session.add(user)
        db.session.commit()
        return cls.user_schema.dump(user)
