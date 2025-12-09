// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CallForBids(
    UUID id, String description, List<String> mandatorySkills, List<String> optionalSkills) {
  public CallForBids {
    if (id == null) {
      id = UUID.randomUUID();
    }
    if (description == null) {
      description = "";
    }
    if (mandatorySkills == null) {
      mandatorySkills = new ArrayList<>();
    }
    if (optionalSkills == null) {
      optionalSkills = new ArrayList<>();
    }
  }
}
