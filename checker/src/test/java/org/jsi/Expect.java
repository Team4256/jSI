package org.jsi;

/**
 * Markers for fixture files. They do nothing when run: {@link FixtureTest} reads them
 * out of the source to learn which mistakes the checker should report. The checker
 * reports them as warnings; the name predates that.
 */
public final class Expect {
  private Expect() {}

  /**
   * The next statement must fail with exactly this checker error. Repeat the call to
   * expect more than one error from the same statement.
   */
  public static void assertError(String message) {}
}
