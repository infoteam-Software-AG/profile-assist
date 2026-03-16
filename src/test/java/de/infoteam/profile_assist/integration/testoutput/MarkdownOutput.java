// SPDX-FileCopyrightText: 2026 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.integration.testoutput;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MarkdownOutput {
  void output(PrintStream out);

  static String ensureLineLength(String textblock) {
    return Arrays.stream(textblock.split(System.lineSeparator()))
        .flatMap(MarkdownOutput::handleLine)
        .collect(Collectors.joining(System.lineSeparator()));
  }

  private static Stream<String> handleLine(String line) {
    if (line.length() <= 80) {
      return Stream.of(line);
    }
    var lines = new ArrayList<String>();
    while (line.length() > 80) {
      var charAtEnd = line.charAt(80);
      if (Character.isWhitespace(charAtEnd)) {
        lines.add(line.substring(0, 80).trim());
        line = line.substring(80).trim();
      } else {
        var nearestWhitespace = findNearestWhitespaceIndex(line, 80);
        lines.add(line.substring(0, nearestWhitespace).trim());
        line = line.substring(nearestWhitespace).trim();
      }
    }

    return lines.stream();
  }

  static int findNearestWhitespaceIndex(String line, final int index) {
    var currentIndex = index;
    while (currentIndex > 0) {
      var charAtEnd = line.charAt(currentIndex);
      if (Character.isWhitespace(charAtEnd)) {
        return currentIndex;
      }
      currentIndex--;
    }
    return index;
  }
}
