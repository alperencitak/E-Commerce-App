from app.model.user import User, UserSchema
from werkzeug.exceptions import NotFound
from app import db
from sqlalchemy import select


class UserService:
    users_schema = UserSchema(many=True)
    user_schema = UserSchema()

    @classmethod
    def get_all(cls):
        users = db.session.execute(select(User)).scalars().all()
        return cls.users_schema.dump(users)

    @classmethod
    def get_by_id(cls, user_id):
        user = db.session.get(User, user_id)
        if not user:
            raise NotFound(f"User not found by id: {user_id}")
        return cls.user_schema.dump(user)

    @classmethod
    def delete_by_id(cls, user_id):
        user = db.session.get(User, user_id)
        db.session.delete(user)
        db.session.commit()
        return {"message": "User account deleted."}

    @classmethod
    def update(cls, data):
        user_id = data.user_id
        user = db.session.get(User, user_id)

        if not user:
            return NotFound(f"User not found by id: {user_id}")

        for key, value in data.__dict__.items():
            if hasattr(user, key):
                setattr(user, key, value)

        db.session.commit()

        return cls.user_schema.dump(user)
