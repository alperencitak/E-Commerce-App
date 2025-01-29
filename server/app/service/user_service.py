from app.model.user import User, UserSchema


class UserService:
    @staticmethod
    def get_all():
        users = User.query.all()
        user_schema = UserSchema(many=True)
        return user_schema.dump(users)

    @staticmethod
    def get_by_id(user_id):
        user = User.query.filter_by(user_id=user_id).first()
        if not user:
            return ValueError(f"User not found by id: {user_id}")
        user_schema = UserSchema()
        return user_schema.dump(user)
