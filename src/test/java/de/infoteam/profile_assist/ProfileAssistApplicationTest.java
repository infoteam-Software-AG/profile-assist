package de.infoteam.profile_assist;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class ProfileAssistApplicationTest {
  @Test
  void mainTest() {

    var test = mock(ProfileAssistApplication.class);

    verify(test, times(1)).main(new String[] {});
  }
}
