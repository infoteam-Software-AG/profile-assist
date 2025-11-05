package de.infoteam.profile_assist.Domain;

import lombok.Data;

import java.util.LinkedList;

public @Data class Project {
    private String name;
    private String description;
    private LinkedList<String> technologies;

}
