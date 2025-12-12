// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.entity.CallForBids;
import de.infoteam.profile_assist.domain.entity.Persona;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.infoteam.profile_assist.integration.JacksonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonReader {

  @Autowired private JacksonConfig jacksonConfig;

  public Persona readPersonaJson(String personaName) throws IOException {
    try (InputStream in =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(
                "personas" + File.separator + personaName + File.separator + "persona.json")) {
      return jacksonConfig.objectMapper().readValue(in, Persona.class);
    }
  }

  public CallForBids readBidJson(String bidName) throws IOException {
    try (InputStream in =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("bids" + File.separator + bidName + File.separator + "bid.json")) {
      return jacksonConfig.objectMapper().readValue(in, CallForBids.class);
    }
  }
}
