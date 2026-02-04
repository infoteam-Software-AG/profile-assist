from services.json_service import JsonService

from services.file_reader_service import FileReaderService

from services.ai_mapping_service import AIMappingService

class PersonaController:

    def __init__(self, config):

        self.json_service = JsonService(config)
        self.file_reader_service = FileReaderService()
        self.ai_mapping_service = AIMappingService(config)

    def generate_persona_from_scheme(self, path_to_persona: str) -> None:
        try:
            scheme = self.json_service.get_scheme()
            raw_persona_str = self.file_reader_service.read_file_as_string(path_to_persona)
            persona = self.ai_mapping_service.map_scheme_to_persona_with_ai(raw_persona_str, scheme)
            self.json_service.write_persona_to_json(persona)
        except Exception as e:
            raise e