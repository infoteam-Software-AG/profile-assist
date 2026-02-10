import os
from openai import OpenAI

from services.prompt_provider_service import PromptProviderService

class AIMappingService:

    def __init__(self, config):
        self.openai_api_key = os.environ['OPENAI_API_KEY']
        self.prompt_provider = PromptProviderService(config)

        self.client = OpenAI()

    def map_scheme_to_persona_with_ai(self, raw_persona_str: str, persona_scheme: dict) -> str:
        prompt = self.prompt_provider.provide_prompt(raw_persona_str, persona_scheme)
        response = self.client.responses.create(
            model="gpt-5.1",
            input=prompt,
            temperature=0.7
        )
        output = remove_json_ai_annotation(response.output_text)
        return output

def remove_json_ai_annotation(json_str: str) -> str:
    json_str = json_str.replace("```json", "")
    json_str = json_str.replace("```", "")
    return json_str.strip()