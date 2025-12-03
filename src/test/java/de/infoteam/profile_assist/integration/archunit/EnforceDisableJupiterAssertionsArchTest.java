/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.integration.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "de.infoteam.profile_assist",
    importOptions = {ImportOption.OnlyIncludeTests.class})
public class EnforceDisableJupiterAssertionsArchTest {

  @ArchTest
  static final ArchRule disableJupiterAssertions =
      noClasses()
          .should()
          .dependOnClassesThat()
          .haveNameMatching("org\\.junit\\.jupiter\\.api\\.Assertions(\\$.*)?");
}
