package de.infoteam.profile_assist.integration.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.entity.Persona;
import java.io.InputStream;

public class PersonaParser {

  public static Persona readPersonaJson() {
    try {
      InputStream in =
          Thread.currentThread().getContextClassLoader().getResourceAsStream("persona.json");
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(in, Persona.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
