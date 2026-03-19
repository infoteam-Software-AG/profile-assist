// SPDX-FileCopyrightText: 2026 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.integration.testoutput;

import de.infoteam.profile_assist.domain.entity.Project;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProjectMarkdownOutputTest {

  @Test
  void bla() {
    var project =
        Project.builder()
            .name("Example Project")
            .description(
"""
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce suscipit in justo ac maximus. Pellentesque sit amet tempus metus. Nulla fermentum auctor ipsum in vulputate. Praesent gravida scelerisque nisi non dapibus. Ut blandit dui eget est tempus semper. Proin mattis lectus nec porta condimentum. Donec ultricies vulputate ligula, nec rutrum purus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque ac ligula at ipsum blandit dictum. Vivamus condimentum pretium massa, sit amet bibendum neque consequat vel.

Aenean massa dui, semper ac velit ac, vulputate sagittis tortor. Nam pellentesque arcu mauris, eget pellentesque nisi auctor nec. Proin finibus sapien eget libero finibus, eu aliquet ante tristique. Integer quis velit orci. Vestibulum nisi odio, placerat quis eros at, hendrerit varius urna. Etiam in convallis orci. Integer tincidunt faucibus dictum. Praesent mi urna, suscipit sit amet nibh ac, luctus porttitor libero. Maecenas eget nibh id risus convallis tempus. Sed nec enim volutpat felis varius accumsan posuere nec justo. Vestibulum eget leo magna. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
""")
            .technologies(List.of())
            .timePeriod("")
            .businessSector("")
            .role("")
            .specializedFocus("")
            .personalContributions(List.of())
            .methodologies(List.of())
            .build();
    var projectMarkdownOutput = new ProjectMarkdownOutput(project);

    projectMarkdownOutput.output(System.out);
  }

  @ParameterizedTest
  @ValueSource(strings = {"Alle meine zuhausis lieben Strings formattieren mit ganzem Herzen. Es ist so super toll das zu debuggen. Yay! "})
  void testEnsureLineLength(String testStr){
    int beforeAlphCount = getNonWhitespaceCharCount(testStr);
    var after = MarkdownOutput.ensureLineLength(testStr);
    Assert.assertEquals(beforeAlphCount, getNonWhitespaceCharCount(after));
  }

  private int getNonWhitespaceCharCount(String input){
    var nonWhiteSpaceCount = 0;
    for(char c : input.toCharArray()){
      if(!Character.isWhitespace(c)) nonWhiteSpaceCount++;
    }
    return nonWhiteSpaceCount;
  }
}
