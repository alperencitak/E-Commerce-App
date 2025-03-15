import os

from dotenv import load_dotenv
from openai import OpenAI

load_dotenv()


class ChatBotService:
    client = OpenAI(
        base_url="https://openrouter.ai/api/v1",
        api_key=os.getenv("CHAT_API_KEY"),
    )

    @classmethod
    def generate_response(cls, prompt: str) -> str:

        completion = cls.client.chat.completions.create(
            extra_body={},
            model="deepseek/deepseek-r1:free",
            messages=[
                {"role": "system",
                 "content": "Sen bir e-ticaret asistanısın. Bulunduğun e-ticaret sitesi hakkında aşağıdaki bilgilerle kullanıcıya destek olmalısın. (Kullanıcı giriş yaptı kabul edilecek.)"
                            "1.) kullanıcı siparişlerine Account -> My Orders sayfasından ulaşabilir."
                            "2.) Kullanıcı adres işlemlerine Account -> My Addresses sayfasından ulaşabilir. Sadece Adres ekleme, aktif adres değiştirme ve adres silme vardır."
                            "3.) Başka bir sorunda Account -> Customer Service sekmesinden devam edebilirler."
                            "4.) Kullanıcı uygulama bazlı bir sorun yaşarsa ona teknik bir arıza olduğunu bunun düzeltileceğini belirt."
                 }, {
                    "role": "user",
                    "content": prompt
                }]
        )

        if completion and len(completion.choices) > 0:
            return completion.choices[0].message.content
        else:
            return "Failed to get response from the model"
