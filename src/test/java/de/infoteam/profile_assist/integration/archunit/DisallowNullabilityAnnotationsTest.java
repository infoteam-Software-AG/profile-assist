// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.integration.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "de.infoteam.profile_assist",
    importOptions = {ImportOption.DoNotIncludeTests.class})
public class DisallowNullabilityAnnotationsTest {
  @ArchTest
  static final ArchRule noNullabilityAnnotationsOtherThanJSpecify =
      noClasses()
          .should()
          .dependOnClassesThat()
          .haveFullyQualifiedName("org.jetbrains.annotations.NotNull")
          .orShould()
          .dependOnClassesThat()
          .haveFullyQualifiedName("org.jetbrains.annotations.Nullable")
          .orShould()
          .dependOnClassesThat()
          .haveFullyQualifiedName("javax.annotation.Nonnull")
          .orShould()
          .dependOnClassesThat()
          .haveFullyQualifiedName("javax.annotation.Nullable")
          .orShould()
          .dependOnClassesThat()
          .haveFullyQualifiedName("jakarta.annotation.Nonnull")
          .orShould()
          .dependOnClassesThat()
          .haveFullyQualifiedName("jakarta.annotation.Nullable")
          .orShould()
          .dependOnClassesThat()
          .haveFullyQualifiedName("org.springframework.lang.NonNull")
          .orShould()
          .dependOnClassesThat()
          .haveFullyQualifiedName("org.springframework.lang.Nullable")
          .because("Only JSpecify (@NullMarked / @Nullable) allowed");
}
