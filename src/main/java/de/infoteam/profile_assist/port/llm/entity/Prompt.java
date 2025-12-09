// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.entity;

import static java.util.Objects.isNull;

import de.infoteam.profile_assist.port.llm.integration.PromptParsingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.PromptTemplate;

@RequiredArgsConstructor
public class Prompt extends Lazy<String> {
  public static final String RESOURCE_NOT_FOUND_MESSAGE = "Cannot find resource %s";
  public static final String IO_ERROR_READING_PROMPT_S = "I/O error reading prompt %s";
  private final String resource;

  @Override
  protected String computeLazyValue() {
    try (var inputStream = getClass().getClassLoader().getResourceAsStream(resource)) {
      if (isNull(inputStream)) {
        throw new PromptParsingException(String.format(RESOURCE_NOT_FOUND_MESSAGE, resource));
      }
      return Stream.of(inputStream)
          .map(is -> new InputStreamReader(is, StandardCharsets.UTF_8))
          .map(BufferedReader::new)
          .flatMap(BufferedReader::lines)
          .collect(Collectors.joining(System.lineSeparator()));
    } catch (IOException exception) {
      throw new PromptParsingException(
          String.format(IO_ERROR_READING_PROMPT_S, resource), exception);
    }
  }

  public String withVariables(Supplier<Map<String, Object>> variableSupplier) {
    return PromptTemplate.builder().template(get()).build().render(variableSupplier.get());
  }
}
