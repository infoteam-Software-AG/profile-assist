package de.infoteam.profile_assist.application.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

public @Data class Persona {

  private UUID id;
  private String jobTitle;
  private List<String> skills;
  private List<String> certificates;
  private List<Project> projectHistory;
  private Date startingDate;
  private Date lastUpdate;
}
