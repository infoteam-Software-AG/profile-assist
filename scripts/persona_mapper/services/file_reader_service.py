class FileReaderService:

    def __init__(self):
        pass

    def read_file_as_string(self, path_to_file: str) -> str:
        try:
            with open(path_to_file, 'r') as file:
                return file.read()
        except Exception as e:
            raise e