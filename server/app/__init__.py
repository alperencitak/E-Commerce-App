from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
from flask_marshmallow import Marshmallow
from config import Config
from flask_smorest import Api
import os


db = SQLAlchemy()
migrate = Migrate()
ma = Marshmallow()
api = Api()


def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)
    app.config['UPLOAD_FOLDER'] = os.path.join(app.root_path, 'uploads')

    db.init_app(app)
    migrate.init_app(app, db)
    ma.init_app(app)
    api.init_app(app)

    from .api.user_api import user_bp
    from .api.category_api import category_bp
    from .api.order_api import order_bp
    from .api.address_api import address_bp
    from .api.product_api import product_bp
    from .api.order_detail_api import order_detail_bp
    from .api.auth_api import auth_bp
    from .api.image_api import image_bp

    app.register_blueprint(auth_bp)
    app.register_blueprint(user_bp)
    app.register_blueprint(address_bp)
    app.register_blueprint(category_bp)
    app.register_blueprint(order_bp)
    app.register_blueprint(product_bp)
    app.register_blueprint(order_detail_bp)
    app.register_blueprint(image_bp)

    return app
