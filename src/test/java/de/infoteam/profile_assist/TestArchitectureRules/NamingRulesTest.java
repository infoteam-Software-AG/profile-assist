package de.infoteam.profile_assist.TestArchitectureRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "de.infoteam.profile_assist",
    importOptions = {ImportOption.OnlyIncludeTests.class})
public class NamingRulesTest {

  @ArchTest
  static final ArchRule unitTestsHaveTestSuffix =
      classes()
          .that()
          .areNotAnnotatedWith(IntegrationTest.class)
          .should()
          .haveSimpleNameEndingWith("Test");

  @ArchTest
  static final ArchRule integrationTestsHaveITSuffix =
      classes()
          .that()
          .areAnnotatedWith(IntegrationTest.class)
          .should()
          .haveSimpleNameEndingWith("IT");
}
