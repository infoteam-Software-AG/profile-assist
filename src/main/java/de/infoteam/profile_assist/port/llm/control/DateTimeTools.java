package de.infoteam.profile_assist.port.llm.control;

import java.time.LocalDate;
import org.springframework.ai.tool.annotation.Tool;

public class DateTimeTools {

  @Tool(description = "Get the current date in the user's timezone")
  String getCurrentDateTime() {
    return LocalDate.now().toString();
  }
}
