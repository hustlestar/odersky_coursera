package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   *
   * @Ignore annotation.
   */
  @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersect contains only equal part of each set`: Unit = {
    new TestSets {
      val u = union(s1, s2)
      val inter = intersect(u, s1)
      assert(contains(inter, 1), "Intersect 1")
      assert(!contains(inter, 2), "Intersect 2")
      assert(!contains(inter, 3), "Intersect 3")
    }
  }

  @Test def `diff contains difference`: Unit = {
    new TestSets {
      val d1 = (i: Int) => 1 < i && i < 10
      val d2 = (i: Int) => 3 < i && i < 12
      val delta = diff(d1, d2)
      assert(contains(delta, 2), "Diff 2")
      assert(contains(delta, 3), "Diff 3")
      assert(!contains(delta, 4), "Diff 4")
      assert(contains(delta, 11), "Diff 11")
    }
  }

  @Test def `filter works fine`: Unit = {
    new TestSets {
      val d1 = (i: Int) => 1 < i && i < 10
      val pred = (i: Int) => i > 8
      val filtered = filter(d1, pred)
      assert(contains(filtered, 9), "Filter 9")
      assert(!contains(filtered, 4), "Filter 4")
      assert(!contains(filtered, 8), "Filter 8")
    }
  }

  @Test def `forall works fine`: Unit = {
    new TestSets {
      val d1 = (i: Int) => 1 < i && i < 10
      val pred1 = (i: Int) => i > 1
      val pred2 = (i: Int) => i > 3
      val checked1 = forall(d1, pred1)
      val checked2 = forall(d1, pred2)
      assert(checked1, "Forall in 1 < i < 10 works i > 1")
      assert(!checked2, "Forall in 1 < i < 10 not works i > 3")
    }
  }

  @Test def `exists works fine`: Unit = {
    new TestSets {
      val d1 = (i: Int) => 1 < i && i < 10
      val pred1 = (i: Int) => i > 1
      val pred2 = (i: Int) => i > 3
      val checked1 = exists(d1, pred1)
      val checked2 = exists(d1, pred2)
      assert(checked1, "Exists in 1 < i < 10 -> i > 1")
      assert(checked2, "Exists in 1 < i < 10 -> i > 3")
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
