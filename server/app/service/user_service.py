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
