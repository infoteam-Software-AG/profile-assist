package de.infoteam.profile_assist.domain.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Persona(
    UUID id,
    String jobTitle,
    List<String> skills,
    List<String> certificates,
    List<Project> projectHistory,
    LocalDate startingDate,
    LocalDate lastUpdate) {}
