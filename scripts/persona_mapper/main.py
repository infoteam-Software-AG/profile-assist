import json

from controller.persona_controller import PersonaController

def main():
    with open("resources/config.json", "r") as config_file:
        config = json.load(config_file)
        config_file.close()

    persona_controller = PersonaController(config)

    persona_controller.generate_persona_from_scheme(
        path_to_persona = config["input_path"]
    )

if __name__ == "__main__":
    main()