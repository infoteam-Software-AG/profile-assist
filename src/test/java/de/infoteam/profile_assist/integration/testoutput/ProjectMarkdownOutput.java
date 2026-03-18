// SPDX-FileCopyrightText: 2026 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.integration.testoutput;

import de.infoteam.profile_assist.domain.entity.Project;
import java.io.PrintStream;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectMarkdownOutput implements MarkdownOutput {

  public static final String HEADLINE_TEMPLATE = "# %s";
  private final Project project;

  @Override
  public void output(PrintStream out) {
    out.println(HEADLINE_TEMPLATE.formatted(project.name()));
    out.println();
    out.println(MarkdownOutput.ensureLineLength(project.description()));
    out.println();
    out.println(MarkdownOutput.ensureLineLength(convertTechnologiesToString(project.technologies())));
    out.println();
  }

  private static String convertTechnologiesToString(List<String> technologies){
    StringBuilder builder = new StringBuilder();
    for(var tech : technologies){
      builder.append(tech);
      builder.append(", ");
    }
    return builder.toString();
  }
}
