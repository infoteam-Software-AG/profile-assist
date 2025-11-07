package de.infoteam.profile_assist.application.domain;

import java.util.List;
import lombok.Data;

public @Data class Project {
  private String name;
  private String description;
  private List<String> technologies;
}
