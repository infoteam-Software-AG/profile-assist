/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SkillsTest {

  static final List<String> BUSINESS_SECTOR = List.of("Industry", "Medicine");
  static final List<String> LANGUAGES = List.of("German", "English");
  static final List<String> PROGRAMMING_LANGUAGES = List.of("Java", "Python");
  static final List<String> METHODICAL_EXPERTISE = List.of("Scrum", "Kanban");
  static final List<String> TOOLS = List.of("Intellij", "Eclipse");
  static final List<String> OPERATING_SYSTEMS = List.of("Windows", "Linux");
  static final List<String> DATABASES = List.of("Postgres", "Oracle");

  @DisplayName("When values are valid, then record should be created")
  @Test
  void constructorAcceptsValues() {
    var result =
        new Skills(
            BUSINESS_SECTOR,
            LANGUAGES,
            PROGRAMMING_LANGUAGES,
            METHODICAL_EXPERTISE,
            TOOLS,
            OPERATING_SYSTEMS,
            DATABASES);

    assertThat(result.businessSectors()).isEqualTo(BUSINESS_SECTOR);
    assertThat(result.languages()).isEqualTo(LANGUAGES);
    assertThat(result.programmingLanguages()).isEqualTo(PROGRAMMING_LANGUAGES);
    assertThat(result.methodicalExpertise()).isEqualTo(METHODICAL_EXPERTISE);
    assertThat(result.tools()).isEqualTo(TOOLS);
    assertThat(result.operatingSystems()).isEqualTo(OPERATING_SYSTEMS);
    assertThat(result.databases()).isEqualTo(DATABASES);
  }

  @DisplayName("When businessSectors is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfBusinessSectorsIsNull() {
    assertThatThrownBy(
            () ->
                new Skills(
                    null,
                    LANGUAGES,
                    PROGRAMMING_LANGUAGES,
                    METHODICAL_EXPERTISE,
                    TOOLS,
                    OPERATING_SYSTEMS,
                    DATABASES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When languages is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfLanguagesIsNull() {
    assertThatThrownBy(
            () ->
                new Skills(
                    BUSINESS_SECTOR,
                    null,
                    PROGRAMMING_LANGUAGES,
                    METHODICAL_EXPERTISE,
                    TOOLS,
                    OPERATING_SYSTEMS,
                    DATABASES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When programmingLanguages is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfProgrammingLanguagesIsNull() {
    assertThatThrownBy(
            () ->
                new Skills(
                    BUSINESS_SECTOR,
                    LANGUAGES,
                    null,
                    METHODICAL_EXPERTISE,
                    TOOLS,
                    OPERATING_SYSTEMS,
                    DATABASES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When methodicalExpertise is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfMethodicalExpertiseIsNull() {
    assertThatThrownBy(
            () ->
                new Skills(
                    BUSINESS_SECTOR,
                    LANGUAGES,
                    PROGRAMMING_LANGUAGES,
                    null,
                    TOOLS,
                    OPERATING_SYSTEMS,
                    DATABASES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When tools is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfToolsIsNull() {
    assertThatThrownBy(
            () ->
                new Skills(
                    BUSINESS_SECTOR,
                    LANGUAGES,
                    PROGRAMMING_LANGUAGES,
                    METHODICAL_EXPERTISE,
                    null,
                    OPERATING_SYSTEMS,
                    DATABASES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When operatingSystems is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfOperatingSystemsIsNull() {
    assertThatThrownBy(
            () ->
                new Skills(
                    BUSINESS_SECTOR,
                    LANGUAGES,
                    PROGRAMMING_LANGUAGES,
                    METHODICAL_EXPERTISE,
                    TOOLS,
                    null,
                    DATABASES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When databases is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfDatabasesIsNull() {
    assertThatThrownBy(
            () ->
                new Skills(
                    BUSINESS_SECTOR,
                    LANGUAGES,
                    PROGRAMMING_LANGUAGES,
                    METHODICAL_EXPERTISE,
                    TOOLS,
                    OPERATING_SYSTEMS,
                    null))
        .isInstanceOf(NullPointerException.class);
  }
}
