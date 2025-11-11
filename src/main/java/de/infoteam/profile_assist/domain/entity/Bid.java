package de.infoteam.profile_assist.domain.entity;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Bid {
  private UUID id;
  private String description;
  private List<String> mandatorySkills;
  private List<String> optionalSkills;
}
