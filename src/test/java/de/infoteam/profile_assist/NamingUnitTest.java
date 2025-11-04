package de.infoteam.profile_assist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NamingUnitTest {

  @Test
  @DisplayName("When getSimpleName of class ends with \"Test\", assert True")
  void classNameTest() {
    assertThat(this.getClass().getSimpleName()).endsWith("Test");
  }
}
