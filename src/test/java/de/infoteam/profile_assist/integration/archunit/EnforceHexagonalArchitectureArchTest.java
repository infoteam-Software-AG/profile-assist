package de.infoteam.profile_assist.integration.archunit;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(
    packages = "de.infoteam.profile_assist",
    importOptions = {ImportOption.DoNotIncludeTests.class})
public class EnforceHexagonalArchitectureArchTest {

  @ArchTest
  Architectures.OnionArchitecture architecture =
      onionArchitecture()
          .withOptionalLayers(true)
          .domainModels("de.infoteam.profile_assist.domain.entity..")
          .domainServices("de.infoteam.profile_assist.domain.control..")
          .applicationServices("de.infoteam.profile_assist.integration..")
          .adapter("llm", "de.infoteam.profile_assist.port.llm..")
          .adapter("rest", "de.infoteam.profile_assist.port.rest..");
}
