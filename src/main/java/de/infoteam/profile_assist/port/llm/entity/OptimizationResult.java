package de.infoteam.profile_assist.port.llm.entity;

/** Wrapper class for providing additional information about the llm optimization */
public record OptimizationResult<T>(T result) {}
