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
  int output(PrintStream out);

  void printNEmptyLines(int n, PrintStream out);

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
    var words = line.split(" ");
    StringBuilder builder = new StringBuilder();
    boolean reasoningLock = false; //used so boldening syntax for ai reasoning doesn't break on newline
    for (String word : words) {
      if(word.contains("(")){
        reasoningLock = true;
      }
      if (reasoningLock || word.length() + builder.length() < 80) {
        builder.append(word.concat(" "));
      } else {
        builder.append(System.lineSeparator());
        lines.add(builder.toString());
        builder.setLength(0);
        builder.append(word.concat(" "));
      }
      if(word.contains(")")){ //reasoning is over
        reasoningLock = false;
      }
    }
    if (builder.length() > 0) {
      lines.add(builder.toString());
    }
    return lines.stream();
  }
}
