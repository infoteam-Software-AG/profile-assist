// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.entity.CallForBids;
import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.integration.JacksonConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JsonReader {

  private final ObjectMapper objectMapper = new JacksonConfig().objectMapper();

  public Persona readPersonaJson(String personaName) throws IOException {
    try (InputStream in =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(
                "personas" + File.separator + personaName + File.separator + "persona.json")) {
      return objectMapper.readValue(in, Persona.class);
    }
  }

  public CallForBids readBidJson(String bidName) throws IOException {
    try (InputStream in =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("bids" + File.separator + bidName + File.separator + "bid.json")) {
      return objectMapper.readValue(in, CallForBids.class);
    }
  }
}
