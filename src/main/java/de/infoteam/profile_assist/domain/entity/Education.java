package de.infoteam.profile_assist.domain.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Education(@NonNull String timePeriod, @NonNull String name, @NonNull String location)
    implements BuilderSupport<Education> {

  public static class EducationBuilder implements BuilderSupport.Builder<Education> {}
}
