// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Job(@NonNull String timePeriod, @NonNull String name) implements BuilderSupport<Job> {
  public static class JobBuilder implements BuilderSupport.Builder<Job> {}
}
