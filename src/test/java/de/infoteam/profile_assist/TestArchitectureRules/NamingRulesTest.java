package de.infoteam.profile_assist.TestArchitectureRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.boot.test.context.SpringBootTest;

@AnalyzeClasses(
    packages = "de.infoteam.profile_assist",
    importOptions = {ImportOption.OnlyIncludeTests.class})
public class NamingRulesTest {

  @ArchTest
  static final ArchRule unitTestsHaveTestSuffix =
      classes()
          .that()
          .areNotAnnotatedWith(IntegrationTest.class)
          .and()
          .areNotAnnotatedWith(SpringBootTest.class)
          .should()
          .haveSimpleNameEndingWith("Test");

  @ArchTest
  static final ArchRule integrationTestsHaveITSuffix =
      classes()
          .that()
          .areAnnotatedWith(IntegrationTest.class)
          .or()
          .areAnnotatedWith(SpringBootTest.class)
          .should()
          .haveSimpleNameEndingWith("IT");
}
