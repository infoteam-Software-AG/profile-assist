package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Builder;
import org.junit.jupiter.api.Test;

class BuilderSupportTest {

  @Test
  void verifyBuildersAreIndependentWhenUsedWithBuilderAnnotation() {
    var builder = TestRecord.builder().testValue("something");
    var testDouble = builder.build();

    var otherBuilder = testDouble.toBuilder();
    var otherTestDouble = otherBuilder.testValue("something else").build();

    assertThat(otherBuilder).isNotSameAs(builder);
    assertThat(testDouble.testValue).isNotEqualTo(otherTestDouble.testValue);
  }

  @Test
  void verifyBuilderImplementsInterface() {
    var builder = TestRecord.builder();
    assertThat(builder).isInstanceOf(BuilderSupport.Builder.class);
  }

  /**
   * A simple test double to be used for testing.
   *
   * @param testValue some value
   */
  @Builder(toBuilder = true)
  private record TestRecord(String testValue) implements BuilderSupport<TestRecord> {

    // this is required to add the interface to the builder
    // other overrides and additional methods may be added here as well
    @SuppressWarnings("unused")
    public static class TestRecordBuilder implements BuilderSupport.Builder<TestRecord> {}
  }
}
