# Persona Mapper

Maps a plain-text persona profile into a structured JSON document using an AI model.

## What it does
- Reads a raw persona profile from resources/inputs/input.txt (or a custom path).
- Loads the target JSON schema from resources/inputs/scheme.json.
- Builds a prompt from resources/prompts/prompt_template.txt.
- Calls the OpenAI API to map the profile to the schema.
- Writes the resulting JSON to out/generated_persona.json.

## Requirements
- Python 3.10+
- An OpenAI API key in the environment

## Setup
1. Create a virtual environment (optional but recommended).
2. Install dependencies:
   - openai

## Configuration
Update resources/config.json as needed:
- persona_scheme_path: Path to the JSON schema.
- input_path: Path to the raw persona text.
- output.output_path and output.output_filename: Output location.
- prompt_template_path: Prompt template file.

## Environment variables
Set the OpenAI key before running:
- OPENAI_API_KEY

## Run
From the persona_mapper folder:
- python main.py

## Output
- The generated JSON file is written to out/generated_persona.json (by default).

## Notes
- The prompt enforces strict schema output and special character normalization.
- If a field cannot be found in the input, it is set to null.
