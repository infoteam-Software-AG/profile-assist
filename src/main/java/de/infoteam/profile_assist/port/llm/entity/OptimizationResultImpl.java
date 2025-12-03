/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.port.llm.entity;

import de.infoteam.profile_assist.domain.entity.OptimizationResult;

/** Wrapper class for providing additional information about the llm optimization */
public record OptimizationResultImpl<T>(T result) implements OptimizationResult<T> {}
