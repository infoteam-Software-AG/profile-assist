package de.infoteam.profile_assist;

import static org.assertj.core.api.Assertions.assertThat;

import de.infoteam.profile_assist.TestArchitectureRules.IntegrationTest;
import org.junit.jupiter.api.Test;

@IntegrationTest
public class NamingIT {

  @Test
  public void classNameTest() {
    assertThat(this.getClass().getSimpleName()).endsWith("IT");
  }
}
