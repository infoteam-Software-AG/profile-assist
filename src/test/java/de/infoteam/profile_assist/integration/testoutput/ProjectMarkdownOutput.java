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
  public void printNEmptyLines(int n, PrintStream out){
    for(int i = 0; i < n; i++){
      out.println();
    }
  }

  @Override
  public int output(PrintStream out) {
    out.println(HEADLINE_TEMPLATE.formatted(project.name()));
    out.println();
    var printableProjectDescription = MarkdownOutput.ensureLineLength(project.description());
    out.println(printableProjectDescription);
    out.println();
    var projectTechnologies = MarkdownOutput.ensureLineLength(convertTechnologiesToString(project.technologies()));
    out.println(projectTechnologies);
    out.println();
    return printableProjectDescription.split(System.lineSeparator()).length + projectTechnologies.split(System.lineSeparator()).length;
  }

  private static String convertTechnologiesToString(List<String> technologies){
    StringBuilder builder = new StringBuilder();
    for(var tech : technologies){
      builder.append(tech);
      if(!technologies.getLast().equals(tech)) {
        builder.append(", ");
      }
    }
    return builder.toString();
  }
}
