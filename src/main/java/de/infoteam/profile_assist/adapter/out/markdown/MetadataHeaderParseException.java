package de.infoteam.profile_assist.adapter.out.markdown;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid YAML front matter")
public class MetadataHeaderParseException extends RuntimeException {
  public MetadataHeaderParseException(String message, Throwable error) {
    super(message, error);
  }
}
