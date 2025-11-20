package de.infoteam.profile_assist.domain.entity;

public interface BuilderSupport<T> {

  Builder<T> toBuilder();

  interface Builder<T> {
    T build();
  }
}
