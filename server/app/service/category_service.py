from werkzeug.exceptions import NotFound

from app.model.category import Category, CategorySchema
from app import db


class CategoryService:
    category_schema = CategorySchema()
    categories_schema = CategorySchema(many=True)

    @classmethod
    def get_all(cls):
        categories = db.session.scalars(db.select(Category)).all()
        return cls.categories_schema.dump(categories)

    @classmethod
    def get_by_id(cls, category_id):
        category = db.session.get(Category, category_id)
        if not category:
            return NotFound(f"Category not found by id: {category_id}")
        return cls.category_schema.dump(category)

    @classmethod
    def add(cls, data):
        db.session.add(data)
        db.session.commit()

        return cls.category_schema.dump(data)

    @classmethod
    def delete_by_id(cls, category_id):
        category = db.session.get(Category, category_id)
        if not category:
            return NotFound(f"Category not found by id: {category_id}")

        db.session.delete(category)
        db.session.commit()

        return {"message": "Category removed."}

    @classmethod
    def update(cls, data):
        category_id = data.category_id
        name = data.name
        category = db.session.get(Category, category_id)
        if not category:
            return NotFound(f"Category not found by id: {category_id}")

        category.name = name
        db.session.commit()

        return cls.category_schema.dump(data)
