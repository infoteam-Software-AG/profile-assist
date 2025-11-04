package de.infoteam.profile_assist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NamingIT {

  @Test
  void classNameTest() {
    assertThat(this.getClass().getSimpleName()).endsWith("IT");
  }
}
