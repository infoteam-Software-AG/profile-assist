package de.infoteam.profile_assist.TestArchitectureRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SuppressWarnings("removal")
@AnalyzeClasses(
    packages = "de.infoteam.profile_assist",
    importOptions = {ImportOption.OnlyIncludeTests.class})
public class NamingRulesTest {

  @ArchTest
  static final ArchRule unitTestsHaveTestSuffix =
      classes()
          .that()
          .areNotAnnotatedWith(ExtendWith.class)
          .and()
          .areNotAnnotatedWith(SpringBootTest.class)
          .and()
          .areNotAnnotatedWith(MockitoBean.class)
          .and()
          .areNotAnnotatedWith(MockBean.class)
          .and()
          .areNotAnnotatedWith(Autowired.class)
          .should()
          .haveSimpleNameEndingWith("Test");

  @ArchTest
  static final ArchRule integrationTestsHaveITSuffix =
      classes()
          .that()
          .areAnnotatedWith(ExtendWith.class)
          .or()
          .areAnnotatedWith(SpringBootTest.class)
          .or()
          .areAnnotatedWith(MockitoBean.class)
          .or()
          .areAnnotatedWith(MockBean.class)
          .or()
          .areAnnotatedWith(Autowired.class)
          .should()
          .haveSimpleNameEndingWith("IT");
}
