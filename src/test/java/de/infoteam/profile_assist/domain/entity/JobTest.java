// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JobTest {

  static final String TIME_PERIOD = "from - to";
  static final String NAME = "jobName";

  @DisplayName("When values are valid, then record should be created")
  @Test
  void constructorAcceptsValues() {
    var result = new Job(TIME_PERIOD, NAME);

    assertThat(result.timePeriod()).isEqualTo(TIME_PERIOD);
    assertThat(result.name()).isEqualTo(NAME);
  }

  @DisplayName("When timePeriod is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfTimePeriodIsNull() {
    assertThatThrownBy(() -> new Job(null, NAME)).isInstanceOf(NullPointerException.class);
  }

  @DisplayName("When name is null, then the constructor shall raise an exception")
  @Test
  void constructorThrowsExceptionIfNameIsNull() {
    assertThatThrownBy(() -> new Job(TIME_PERIOD, null)).isInstanceOf(NullPointerException.class);
  }
}
