package de.infoteam.profile_assist;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class ProfileAssistApplicationTest {
  @Test
  @DisplayName("Main method should call SpringApplication run")
  void mainTest() {
    try (MockedStatic<SpringApplication> springAppMock = mockStatic(SpringApplication.class)) {

      springAppMock
          .when(() -> SpringApplication.run(ProfileAssistApplication.class, new String[] {}))
          .thenReturn(null);

      ProfileAssistApplication.main(new String[] {});

      springAppMock.verify(
          () -> SpringApplication.run(ProfileAssistApplication.class, new String[] {}), times(1));
    }
  }
}
