package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EducationTest {

  static final String TIME_PERIOD = "from - to";
  static final String NAME = "name";
  static final String LOCATION = "location";

  @DisplayName("When values are valid, then record should be created")
  @Test
  void constructorAcceptsValues() {
    var result = new Education(TIME_PERIOD, NAME, LOCATION);

    assertThat(result.timePeriod()).isEqualTo(TIME_PERIOD);
    assertThat(result.name()).isEqualTo(NAME);
    assertThat(result.location()).isEqualTo(LOCATION);
  }

  @DisplayName("When timePeriod is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfTimePeriodIsNull() {
    assertThatThrownBy(() -> new Education(null, NAME, LOCATION))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When name is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfNameIsNull() {
    assertThatThrownBy(() -> new Education(TIME_PERIOD, null, LOCATION))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When location is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfLocationIsNull() {
    assertThatThrownBy(() -> new Education(TIME_PERIOD, NAME, null))
        .isInstanceOf(NullPointerException.class);
  }
}
