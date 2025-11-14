package de.infoteam.profile_assist.port.llm.control;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateTimeToolsTest {
  private final DateTimeTools dateTimeTools = new DateTimeTools();

  @Test
  void getCurrentDateTimeTest() {
    // pre-action
    String localTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
    // action
    String toolOutputTime = dateTimeTools.getCurrentDateTime();
    // assertion
    Assertions.assertEquals(localTime, toolOutputTime);
  }
}
