package de.infoteam.profile_assist.Domain;

import java.util.LinkedList;
import lombok.Data;

public @Data class Project {
  private String name;
  private String description;
  private LinkedList<String> technologies;
}
