package de.infoteam.profile_assist.domain.entity;

import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record Project(String name, String description, List<String> technologies)
    implements BuilderSupport<Project> {

  public static class ProjectBuilder implements BuilderSupport.Builder<Project> {}
}
