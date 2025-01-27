from app import db


class User(db.Model):
    __tablename__ = "users"

    user_id = db.Column("user_id", db.Integer, primary_key=True, autoincrement=True)
    first_name = db.Column("first_name", db.String(40), nullable=False)
    last_name = db.Column("last_name", db.String(40), nullable=False)
    email = db.Column("email", db.String(120), nullable=False)
    phone = db.Column("phone", db.String(20), nullable=True)
    created_at = db.Column("created_at", db.DateTime, default=db.func.now())
