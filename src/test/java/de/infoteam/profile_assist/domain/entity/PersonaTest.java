/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PersonaTest {

  static final UUID ID = UUID.randomUUID();
  static final String JOB_TITLE = "JobTitle";
  static final String NAME = "name";
  static final List<Education> EDUCATIONS = Collections.emptyList();
  static final int YEARS_OF_EXPERIENCE = 1;
  static final List<Job> JOB_HISTORY = Collections.emptyList();
  static final List<String> CORE_COMPETENCIES = Collections.emptyList();
  static final List<String> CERTIFICATES = Collections.emptyList();
  static final Skills SKILLS =
      Skills.builder()
          .businessSectors(Collections.emptyList())
          .languages(Collections.emptyList())
          .programmingLanguages(Collections.emptyList())
          .methodicalExpertise(Collections.emptyList())
          .tools(Collections.emptyList())
          .operatingSystems(Collections.emptyList())
          .databases(Collections.emptyList())
          .build();
  static final List<Project> PROJECT_HISTORY = Collections.emptyList();
  static final LocalDate STARTING_DATE = LocalDate.now();
  static final LocalDate LAST_UPDATE = LocalDate.now();

  @DisplayName("When values are valid, then record should be created")
  @Test
  void constructorAcceptsValues() {
    var result =
        new Persona(
            ID,
            JOB_TITLE,
            NAME,
            EDUCATIONS,
            YEARS_OF_EXPERIENCE,
            JOB_HISTORY,
            CORE_COMPETENCIES,
            CERTIFICATES,
            SKILLS,
            PROJECT_HISTORY,
            STARTING_DATE,
            LAST_UPDATE);

    assertThat(result.id()).isEqualTo(ID);
    assertThat(result.jobTitle()).isEqualTo(JOB_TITLE);
    assertThat(result.name()).isEqualTo(NAME);
    assertThat(result.educations()).isEqualTo(EDUCATIONS);
    assertThat(result.yearsOfExperience()).isEqualTo(YEARS_OF_EXPERIENCE);
    assertThat(result.jobHistory()).isEqualTo(JOB_HISTORY);
    assertThat(result.coreCompetencies()).isEqualTo(CORE_COMPETENCIES);
    assertThat(result.certificates()).isEqualTo(CERTIFICATES);
    assertThat(result.skills()).isEqualTo(SKILLS);
    assertThat(result.projectHistory()).isEqualTo(PROJECT_HISTORY);
    assertThat(result.startingDate()).isEqualTo(STARTING_DATE);
    assertThat(result.lastUpdate()).isEqualTo(LAST_UPDATE);
  }

  @DisplayName("When id is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfIDIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    null,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When jobTitle is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfJobTitleIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    null,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When name is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfNameIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    null,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When educations is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfEducationsIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    null,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When yearsOfExperience is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfYearsOfExperienceIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    null,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When jobHistory is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfJobHistoryIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    null,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When coreCompetencies is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfCoreCompetenciesIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    null,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When certificates is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfCertificatesIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    null,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When skills is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfSkillsIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    null,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When projectHistory is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfProjectHistoryIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    null,
                    STARTING_DATE,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When startingDate is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfStatingDateIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    null,
                    LAST_UPDATE))
        .isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When lastUpdate is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfLastUpdateIsNull() {
    assertThatThrownBy(
            () ->
                new Persona(
                    ID,
                    JOB_TITLE,
                    NAME,
                    EDUCATIONS,
                    YEARS_OF_EXPERIENCE,
                    JOB_HISTORY,
                    CORE_COMPETENCIES,
                    CERTIFICATES,
                    SKILLS,
                    PROJECT_HISTORY,
                    STARTING_DATE,
                    null))
        .isInstanceOf(NullPointerException.class);
  }
}
