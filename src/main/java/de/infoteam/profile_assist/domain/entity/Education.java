// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Education(@NonNull String timePeriod, @NonNull String name, @NonNull String location)
    implements BuilderSupport<Education> {

  public static class EducationBuilder implements BuilderSupport.Builder<Education> {}
}
