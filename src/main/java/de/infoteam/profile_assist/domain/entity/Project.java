package de.infoteam.profile_assist.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Project(String name, String description, List<String> technologies) {
  public Project {
    technologies = Objects.requireNonNullElseGet(technologies, ArrayList::new);
  }
}
