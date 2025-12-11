// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.OptimizationResult;
import de.infoteam.profile_assist.domain.entity.Project;

public interface OptimizeProjectDescriptionUseCase {
  OptimizationResult<Project> optimizeProjectDescription(Project project, String requiredProjectDescription);
}
