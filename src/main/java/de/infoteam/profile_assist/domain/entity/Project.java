// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Project(
    @NonNull String name,
    @NonNull String description,
    @NonNull List<String> technologies,
    @NonNull String timePeriod,
    @NonNull String businessSector,
    int teamSize,
    @NonNull String role,
    @NonNull String specializedFocus,
    @NonNull List<String> personalContributions,
    @NonNull List<String> methodologies)
    implements BuilderSupport<Project> {

  public static class ProjectBuilder implements BuilderSupport.Builder<Project> {}
}
