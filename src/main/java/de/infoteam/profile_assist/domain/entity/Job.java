/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.domain.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Job(@NonNull String timePeriod, @NonNull String name) implements BuilderSupport<Job> {
  public static class JobBuilder implements BuilderSupport.Builder<Job> {}
}
