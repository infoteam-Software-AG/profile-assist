package de.infoteam.profile_assist.domain.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Job(@NonNull String from, @NonNull String to, @NonNull String name)
    implements BuilderSupport<Job> {
  public static class JobBuilder implements BuilderSupport.Builder<Job> {}
}
