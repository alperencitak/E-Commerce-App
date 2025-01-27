from app import db, ma


class OrderDetail(db.Model):
    __tablename__ = "order_details"

    order_detail_id = db.Column("order_detail_id", db.Integer, primary_key=True, autoincrement=True)
    order_id = db.Column("order_id", db.Integer, db.ForeignKey("orders.order_id"), nullable=False)
    product_id = db.Column("product_id", db.Integer, db.ForeignKey("products.product_id"), nullable=False)
    quantity = db.Column("quantity", db.Integer, nullable=False)
    price = db.Column("price", db.Numeric(10, 2), nullable=False)


class OrderDetailSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = OrderDetail
        include_fk = True
        load_instance = True
