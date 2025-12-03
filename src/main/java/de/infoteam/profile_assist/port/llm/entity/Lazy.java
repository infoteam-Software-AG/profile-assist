/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.port.llm.entity;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

public abstract class Lazy<T> {

  @Nullable
  // see: Bloch, Joshua - Effective Java 3rd Edition, p. 334
  // and the corresponding errata at
  // https://docs.google.com/document/d/1mAeEgQu4H4ADxa03k7YaVDjIP5vJBvjVIjg3DIvoc8E/edit?tab=t.0
  // (published on X https://x.com/joshbloch/status/950509809219940352)
  @SuppressWarnings("java:S3077")
  private volatile T field;

  @SuppressWarnings("")
  public final T get() {
    T result = field;
    // First check (no locking)
    if (result != null) {
      return result;
    }
    // Second check (with locking)
    synchronized (this) {
      if (field == null) {
        field = computeLazyValue();
      }
      return noneNullOrThrow(field);
    }
  }

  // false positive in nullability checks
  @SuppressWarnings("java:S2637")
  private static <T> T noneNullOrThrow(@Nullable T field) {
    return Optional.ofNullable(field)
        .orElseThrow(() -> new IllegalStateException("Lazy initialization failed"));
  }

  protected abstract T computeLazyValue();
}
