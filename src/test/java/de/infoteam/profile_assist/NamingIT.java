package de.infoteam.profile_assist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NamingIT {

  @Test
  @DisplayName("When getSimpleName of class ends with \"IT\", assert True")
  void classNameTest() {
    assertThat(this.getClass().getSimpleName()).endsWith("IT");
  }
}
