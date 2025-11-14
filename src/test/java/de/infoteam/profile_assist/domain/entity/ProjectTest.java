package de.infoteam.profile_assist.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectTest {

  public static final String NAME = "projectName";
  public static final String DESCRIPTION = "projectDescription";
  public static final List<String> TECHNOLOGIES = List.of("Java", "Spring Boot");

  @DisplayName("When values are valid, then record should be created")
  @Test
  void constructorAcceptsValues() {
    var result = new Project(NAME, DESCRIPTION, TECHNOLOGIES);

    assertThat(result.name()).isEqualTo(NAME);
    assertThat(result.description()).isEqualTo(DESCRIPTION);
    assertThat(result.technologies()).isEqualTo(TECHNOLOGIES);
  }

  @DisplayName("When name is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfNameIsNull() {
    assertThatThrownBy(() -> new Project(null, DESCRIPTION, TECHNOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When description is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfDescriptionIsNull() {
    assertThatThrownBy(() -> new Project(NAME, null, TECHNOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When technologies is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfTechnologiesIsNull() {
    assertThatThrownBy(() -> new Project(NAME, DESCRIPTION, null))
        .isInstanceOf(NullPointerException.class);
  }
}