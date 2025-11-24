package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EducationTest {

  static final String FROM = "from";
  static final String TO = "to";
  static final String NAME = "name";
  static final String LOCATION = "location";

  @DisplayName("When values are valid, then record should be created")
  @Test
  void constructorAcceptsValues() {
    var result = new Education(FROM, TO, NAME, LOCATION);

    assertThat(result.from()).isEqualTo(FROM);
    assertThat(result.to()).isEqualTo(TO);
    assertThat(result.name()).isEqualTo(NAME);
    assertThat(result.location()).isEqualTo(LOCATION);
  }

  @DisplayName("When from is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfFromIsNull() {
    assertThatThrownBy(() -> new Education(null, TO, NAME, LOCATION))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When to is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfToIsNull() {
    assertThatThrownBy(() -> new Education(FROM, null, NAME, LOCATION))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When name is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfNameIsNull() {
    assertThatThrownBy(() -> new Education(FROM, TO, null, LOCATION))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When location is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfLocationIsNull() {
    assertThatThrownBy(() -> new Education(FROM, TO, NAME, null))
        .isInstanceOf(NullPointerException.class);
  }
}
