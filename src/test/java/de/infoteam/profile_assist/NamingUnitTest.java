package de.infoteam.profile_assist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NamingUnitTest {

  @Test
  void classNameTest() {
    assertThat(this.getClass().getSimpleName()).endsWith("Test");
  }
}
