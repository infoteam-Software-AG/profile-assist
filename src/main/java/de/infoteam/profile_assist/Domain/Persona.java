package de.infoteam.profile_assist.Domain;

import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public @Data class Persona {

    private UUID id;
    private String jobTitle;
    private LinkedList<String> skills;
    private LinkedList<String> certificates;
    private LinkedList<Project> projectHistory;
    private Date startingDate;
    private Date lastUpdate;



}
