from marshmallow import ValidationError

from app.model.category import Category, CategorySchema
from app import db


class CategoryService:
    @staticmethod
    def get_all():
        categories = Category.query.all()
        category_schema = CategorySchema(many=True)
        return category_schema.dump(categories)

    @staticmethod
    def get_by_id(category_id):
        category = Category.query.filter_by(category_id=category_id).first()
        if not category:
            return ValueError(f"Category not found by id: {category_id}")
        category_schema = CategorySchema()
        return category_schema.dump(category)

    @staticmethod
    def add(data):
        category_schema = CategorySchema()

        try:
            category = category_schema.load(data)
        except ValidationError as e:
            return {"errors": e.messages}, 400

        db.session.add(category)
        db.session.commit()

        return {"message": "Category added."}

    @staticmethod
    def delete_by_id(category_id):
        category = Category.query.filter_by(category_id=category_id).first()
        if not category:
            return ValueError(f"Category not found by id: {category_id}")

        db.session.delete(category)
        db.session.commit()

        return {"message": "Category removed."}

    @staticmethod
    def update(data):
        category_id = data.get('category_id')
        name = data.get('name')
        category = Category.query.filter_by(category_id=category_id).first()
        if not category:
            return ValueError(f"Category not found by id: {category_id}")

        category.name = name

        db.session.commit()

        return {"message": "Category updated."}
