package de.infoteam.profile_assist.port.llm.control;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DateTimeTools {

  @Tool(description = "Get the current date and time in the user's timezone")
  String getCurrentDateTime() {
    return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).truncatedTo(ChronoUnit.MINUTES).toString();
  }
}
