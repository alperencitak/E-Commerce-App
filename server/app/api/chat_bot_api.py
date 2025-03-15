from flask_smorest import Blueprint
from flask import request
from flask.views import MethodView
from http import HTTPStatus
from app.service.chat_bot_service import ChatBotService

chat_bot_bp = Blueprint("chat_bot", "chat_bot", url_prefix="/assistant", description="Assistant bot")


@chat_bot_bp.route("/ask")
class ask(MethodView):
    @chat_bot_bp.response(HTTPStatus.OK)
    def get(self):
        prompt = request.args.get("message")
        return ChatBotService.generate_response(prompt)
