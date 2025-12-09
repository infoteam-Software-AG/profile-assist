// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.entity;

import de.infoteam.profile_assist.domain.entity.OptimizationResult;

/** Wrapper class for providing additional information about the llm optimization */
public record OptimizationResultImpl<T>(T result) implements OptimizationResult<T> {}
