// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Skills(
    @NonNull List<String> businessSectors,
    @NonNull List<String> languages,
    @NonNull List<String> programmingLanguages,
    @NonNull List<String> methodicalExpertise,
    @NonNull List<String> tools,
    @NonNull List<String> operatingSystems,
    @NonNull List<String> databases)
    implements BuilderSupport<Skills> {

  public static class SkillsBuilder implements BuilderSupport.Builder<Skills> {}
}
