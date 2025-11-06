package de.infoteam.profile_assist;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProfileAssistApplicationTest {
  @Test
  @DisplayName("When Mock successfully starts Method main, assert true")
  void mainTest() {

    var test = mock(ProfileAssistApplication.class);

    verify(test, times(1)).main(new String[] {});
  }
}
