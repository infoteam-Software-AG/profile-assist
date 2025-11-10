package de.infoteam.profile_assist.domain.entity;

import java.util.List;
import lombok.Data;

@Data
public class Project {
  private String name;
  private String description;
  private List<String> technologies;
}
