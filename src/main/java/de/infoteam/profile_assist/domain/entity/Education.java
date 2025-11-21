package de.infoteam.profile_assist.domain.entity;

import lombok.Builder;

@Builder(toBuilder = true)
public record Education(String from, String to, String name, String location)
    implements BuilderSupport<Education> {

  public static class EducationBuilder implements BuilderSupport.Builder<Education> {}
}
