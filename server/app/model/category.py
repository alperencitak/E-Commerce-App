from app import db, ma


class Category(db.Model):
    __tablename__ = "categories"

    category_id = db.Column("category_id", db.Integer, primary_key=True, autoincrement=True)
    name = db.Column("name", db.String(100), nullable=False)


class CategorySchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = Category
        load_instance = True
