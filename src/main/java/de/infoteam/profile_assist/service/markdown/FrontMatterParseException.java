package de.infoteam.profile_assist.service.markdown;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid YAML front matter")
public class FrontMatterParseException extends RuntimeException {
  public FrontMatterParseException(String message, Throwable error) {
    super(message, error);
  }
}
