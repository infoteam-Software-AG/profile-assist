// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProjectTest {

  static final String NAME = "projectName";
  static final String DESCRIPTION = "projectDescription";
  static final List<String> TECHNOLOGIES = List.of("Java", "Spring Boot");
  static final String TIME_PERIOD = "timePeriod";
  static final String BUSINESS_SECTOR = "businessSector";
  static final int TEAM_SIZE = 42;
  static final String ROLE = "Role";
  static final String SPECIALIZED_FOCUS = "specializedFocus";
  static final List<String> PERSONAL_CONTRIBUTIONS =
      List.of("MyPersonalContribution", "AnotherPersonalContribution");
  static final List<String> METHODOLOGIES = List.of("ISO", "IEC");

  @DisplayName("When values are valid, then record should be created")
  @Test
  void constructorAcceptsValues() {
    var result =
        new Project(
            NAME,
            DESCRIPTION,
            TECHNOLOGIES,
            TIME_PERIOD,
            BUSINESS_SECTOR,
            TEAM_SIZE,
            ROLE,
            SPECIALIZED_FOCUS,
            PERSONAL_CONTRIBUTIONS,
            METHODOLOGIES);

    assertThat(result.name()).isEqualTo(NAME);
    assertThat(result.description()).isEqualTo(DESCRIPTION);
    assertThat(result.technologies()).isEqualTo(TECHNOLOGIES);
    assertThat(result.timePeriod()).isEqualTo(TIME_PERIOD);
    assertThat(result.businessSector()).isEqualTo(BUSINESS_SECTOR);
    assertThat(result.teamSize()).isEqualTo(TEAM_SIZE);
    assertThat(result.role()).isEqualTo(ROLE);
    assertThat(result.specializedFocus()).isEqualTo(SPECIALIZED_FOCUS);
    assertThat(result.personalContributions()).isEqualTo(PERSONAL_CONTRIBUTIONS);
    assertThat(result.methodologies()).isEqualTo(METHODOLOGIES);
  }

  @DisplayName("When name is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfNameIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    null,
                    DESCRIPTION,
                    TECHNOLOGIES,
                    TIME_PERIOD,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    ROLE,
                    SPECIALIZED_FOCUS,
                    PERSONAL_CONTRIBUTIONS,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When description is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfDescriptionIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    null,
                    TECHNOLOGIES,
                    TIME_PERIOD,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    ROLE,
                    SPECIALIZED_FOCUS,
                    PERSONAL_CONTRIBUTIONS,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When technologies is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfTechnologiesIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    DESCRIPTION,
                    null,
                    TIME_PERIOD,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    ROLE,
                    SPECIALIZED_FOCUS,
                    PERSONAL_CONTRIBUTIONS,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When timePeriod is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfTimePeriodIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    DESCRIPTION,
                    TECHNOLOGIES,
                    null,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    ROLE,
                    SPECIALIZED_FOCUS,
                    PERSONAL_CONTRIBUTIONS,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When businessSector is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfBusinessSectorIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    DESCRIPTION,
                    TECHNOLOGIES,
                    TIME_PERIOD,
                    null,
                    TEAM_SIZE,
                    ROLE,
                    SPECIALIZED_FOCUS,
                    PERSONAL_CONTRIBUTIONS,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When role is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfRoleIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    DESCRIPTION,
                    TECHNOLOGIES,
                    TIME_PERIOD,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    null,
                    SPECIALIZED_FOCUS,
                    PERSONAL_CONTRIBUTIONS,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When specializedFocus is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfSpecializedFocusIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    DESCRIPTION,
                    TECHNOLOGIES,
                    TIME_PERIOD,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    ROLE,
                    null,
                    PERSONAL_CONTRIBUTIONS,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When specializedFocus is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfPersonalContributionsIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    DESCRIPTION,
                    TECHNOLOGIES,
                    TIME_PERIOD,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    ROLE,
                    SPECIALIZED_FOCUS,
                    null,
                    METHODOLOGIES))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When methodologies is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfMethodologiesIsNull() {
    assertThatThrownBy(
            () ->
                new Project(
                    NAME,
                    DESCRIPTION,
                    TECHNOLOGIES,
                    TIME_PERIOD,
                    BUSINESS_SECTOR,
                    TEAM_SIZE,
                    ROLE,
                    SPECIALIZED_FOCUS,
                    PERSONAL_CONTRIBUTIONS,
                    null))
        .isInstanceOf(NullPointerException.class);
  }
}
