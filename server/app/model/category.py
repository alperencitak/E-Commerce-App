from app import db


class Category(db.Model):
    __tablename__ = "categories"

    category_id = db.Column("category_id", db.Integer, primary_key=True, autoincrement=True)
    name = db.Column("name", db.String(100), nullable=False)
