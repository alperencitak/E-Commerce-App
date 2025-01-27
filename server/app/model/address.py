from app import db


class Address(db.Model):
    __tablename__ = "addresses"

    address_id = db.Column("address_id", db.Integer, primary_key=True, autoincrement=True)
    user_id = db.Column("user_id", db.Integer, db.ForeignKey("users.user_id"), nullable=False)
    address_line1 = db.Column("address_line1", db.String(255), nullable=False)
    address_line2 = db.Column("address_line2", db.String(255), nullable=False)
    district = db.Column("district", db.String(100), nullable=False)
    city = db.Column("city", db.String(100), nullable=False)
    country = db.Column("country", db.String(100), nullable=False)
