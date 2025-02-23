from app import db, ma


class Category(db.Model):
    __tablename__ = "categories"

    category_id = db.Column("category_id", db.Integer, primary_key=True, autoincrement=True)
    name = db.Column("name", db.String(100), nullable=False)
    parent_id = db.Column("parent_id", db.Integer, db.ForeignKey("categories.category_id"), nullable=True)


class CategorySchema(ma.SQLAlchemyAutoSchema):
    parent_id = ma.Integer(required=False, allow_none=True)
    
    class Meta:
        model = Category
        load_instance = True
