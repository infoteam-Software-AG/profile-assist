// SPDX-FileCopyrightText: 2026 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record SkillMatrix(@NonNull String name, int level)
    implements BuilderSupport<SkillMatrix> {
  public static class SkillMatrixBuilder implements BuilderSupport.Builder<SkillMatrix> {}
}
