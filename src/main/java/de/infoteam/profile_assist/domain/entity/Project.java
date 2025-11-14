package de.infoteam.profile_assist.domain.entity;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Project(@NonNull String name, @NonNull String description, @NonNull List<String> technologies)
    implements BuilderSupport<Project> {

  public static class ProjectBuilder implements BuilderSupport.Builder<Project> {}
}
