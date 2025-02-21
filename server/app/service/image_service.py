import os
from werkzeug.utils import secure_filename
from flask import current_app, send_from_directory


class ImageService:
    UPLOAD_FOLDER = "uploads"
    ALLOWED_EXTENSIONS = {"png", "jpg", "jpeg", "gif"}

    @classmethod
    def allowed_file(cls, filename):
        return "." in filename and filename.rsplit(".", 1)[1].lower() in cls.ALLOWED_EXTENSIONS

    @classmethod
    def save_image(cls, file):
        if not file or file.filename == "":
            return None, "No selected file"

        if not cls.allowed_file(file.filename):
            return None, "Invalid file type"

        filename = secure_filename(file.filename)
        file_path = os.path.join(current_app.route, filename)

        os.makedirs(os.path.join(current_app.route, cls.UPLOAD_FOLDER), exist_ok=True)
        file.save(file_path)

        return f"/uploads/{filename}", None

    @classmethod
    def serve_image(cls, filename):
        return send_from_directory("", filename)
