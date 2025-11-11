package de.infoteam.profile_assist.domain.entity;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CallForBids(
    UUID id, String description, List<String> mandatorySkills, List<String> optionalSkills) {}
