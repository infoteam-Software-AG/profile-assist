/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.port.llm.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LazyTest {

  public static final String THE_LAZY_STRING = "the lazy string";

  @DisplayName("When get() is called for the first time, the value is computed")
  @Test
  void firstCallToGet_computesTheValue() {
    var lazy = spy(new TestDouble());
    var result = lazy.get();

    assertThat(result).isEqualTo(THE_LAZY_STRING);
    verify(lazy, times(1)).computeLazyValue();
  }

  @DisplayName("When get() is called multiple times, the value is computed only once")
  @Test
  void mulipleCallsToGet_computeTheValueOnlyOnce() {
    var lazy = spy(new TestDouble());
    lazy.get();
    var result = lazy.get();

    assertThat(result).isEqualTo(THE_LAZY_STRING);
    verify(lazy, times(1)).computeLazyValue();
  }

  @DisplayName("When computeLazyValue() returns null, an IllegalStateException is raised")
  @Test
  void computeLazyValue_returnsNull_causesIllegalStateException() {
    var lazy = spy(new TestDouble());
    when(lazy.computeLazyValue()).thenReturn(null);

    assertThatThrownBy(lazy::get).isInstanceOf(IllegalStateException.class);
  }

  private static final class TestDouble extends Lazy<String> {

    @Override
    protected String computeLazyValue() {
      return THE_LAZY_STRING;
    }
  }
}
