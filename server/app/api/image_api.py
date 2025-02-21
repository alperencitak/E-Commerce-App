from flask_smorest import Blueprint
from app.service.image_service import ImageService
from http import HTTPStatus
from flask.views import MethodView

image_bp = Blueprint("images", "images", url_prefix="/image", description="Image API")


@image_bp.route("/<path:filename>")
class ImageResponse(MethodView):
    @image_bp.response(HTTPStatus.OK)
    def get(self, filename):
        return ImageService.serve_image(filename)
