package de.infoteam.profile_assist.port.llm.control;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DateTimeToolsTest {
  private final DateTimeTools dateTimeTools = new DateTimeTools();

  @Test
  void getCurrentDateTimeTest() {
    // pre-action
    String localTime = LocalDate.now().toString();
    // action
    String toolOutputTime = dateTimeTools.getCurrentDateTime();
    // assertion
    Assertions.assertThat(localTime).isEqualTo(toolOutputTime);
  }
}
