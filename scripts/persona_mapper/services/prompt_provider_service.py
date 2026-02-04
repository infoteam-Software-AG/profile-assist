import json

mappings = {
    "name": "name",
    "educations": "Studium / Ausbildung",
    "yearsOfExperience": "yearsOfExperience",
    "jobHistory":  "Beruflicher Werdegang",
    "coreCompetencies": "Kernkompetenzen", 
    "certificates": "Zertifikate", 
    "skills": "Allgemeine Erfahrungen",
    "projectHistory": "Projekte",
    "startingDate": "Verfügbar ab",
    "lastUpdate": "Stand des Profils"
}

skills_mappings = {
    "buissnesSectors": "Branchen",
    "languages": "Sprachen",
    "programmingLanguages": "Programmiersprachen", 
    "methodicalExpertise": "Methodenkenntnisse", 
    "tools": "Tools",
    "operatignSystems": "Betriebssysteme",
    "databases": "Datenbanken"
}

project_mappings = {
    "name": "name",
    "description": "Projektbeschreibung",
    "technologies": "Technologien / Tools",
    "timePeriod": "Zeitraum",
    "buisnessSector": "Branche",
    "teamSize": "Teamgröße",
    "role": "Projektrolle",
    "specializedFocus": "specializedFocus",
    "personalContributions": "personalContributians", 
    "methodologies": "Methoden",
}

class PromptProviderService:

    def __init__(self, config):
        self.prompt_template_path = config["prompt_template_path"]

    def __get_prompt_template(self) -> str:
        try:
            with open(self.prompt_template_path, 'r') as file:
                return file.read()
        except Exception as e:
            raise e
    
    def provide_prompt(self, raw_persona_str: str, persona_scheme: dict) -> str:
        try:
            prompt_template = self.__get_prompt_template()
            prompt = prompt_template.replace("{profile}", raw_persona_str)
            prompt = prompt.replace("{mappings}", json.dumps(mappings, indent=4))
            prompt = prompt.replace("{project_mappings}", json.dumps(project_mappings, indent=4))
            prompt = prompt.replace("{skills_mappings}", json.dumps(skills_mappings, indent=4))
            prompt = prompt.replace("{persona_scheme}", json.dumps(persona_scheme, indent=4))
            return prompt
        except Exception as e:
            raise e


