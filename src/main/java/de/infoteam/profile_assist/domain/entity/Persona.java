// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Persona(
    @NonNull UUID id,
    @NonNull String jobTitle,
    @NonNull String name,
    @NonNull List<Education> educations,
    @NonNull Integer yearsOfExperience,
    @NonNull List<Job> jobHistory,
    @NonNull List<String> coreCompetencies,
    @NonNull List<String> certificates,
    @NonNull Skills skills,
    @NonNull List<Project> projectHistory,
    @NonNull LocalDate startingDate,
    @NonNull LocalDate lastUpdate)
    implements BuilderSupport<Persona> {

  public static class PersonaBuilder implements BuilderSupport.Builder<Persona> {}
}
