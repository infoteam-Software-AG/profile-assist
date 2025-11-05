package de.infoteam.profile_assist.integration.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SuppressWarnings("removal")
@AnalyzeClasses(
    packages = "de.infoteam.profile_assist",
    importOptions = {ImportOption.OnlyIncludeTests.class})
public class EnforceDisableAnnotationsUnitTestsArchTest {

  @ArchTest
  static final ArchRule unitAndComponentTestsDoNotUseAnnotations =
      classes()
          .that()
          .haveSimpleNameEndingWith("Test")
          .should()
          .notBeAnnotatedWith(SpringBootTest.class)
          .andShould()
          .notBeAnnotatedWith(MockitoBean.class)
          .andShould()
          .notBeAnnotatedWith(MockBean.class)
          .andShould()
          .notBeAnnotatedWith(MockitoSpyBean.class)
          .andShould()
          .notBeAnnotatedWith(Autowired.class)
          .andShould()
          .notBeAnnotatedWith(DirtiesContext.class)
          .andShould()
          .notBeAnnotatedWith(WebMvcTest.class);

  /* currently no Configuration for DataSource, invoking the class breaks test*/
  // .andShould()
  // .notBeAnnotatedWith(DataJpaTest.class);

  @ArchTest
  static final ArchRule unitAndComponentTestsDoNotUseExtendWithAnnotation =
      classes()
          .that()
          .areAnnotatedWith(ExtendWith.class)
          .and()
          .haveSimpleNameEndingWith("Test")
          .should()
          .beAnnotatedWith(
              new DescribedPredicate<JavaAnnotation<?>>("value SpringExtension.class removed") {
                @Override
                public boolean test(JavaAnnotation<?> javaAnnotation) {
                  if (javaAnnotation.getRawType().getSimpleName().equals("ExtendWith")) {
                    JavaClass[] values = (JavaClass[]) javaAnnotation.get("value").get();
                    for (JavaClass value : values) {
                      if (value.getSimpleName().equals("SpringExtension")) {
                        return false;
                      }
                    }
                  }
                  return true;
                }
              })
          .allowEmptyShould(true);
}
