/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.replaceable;

import io.github.portlek.mapentry.MapEntry;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

final class RpBaseTest {

  @Test
  void build() {
    new Assertion<>(
      "Couldn't build correctly.",
      RpString.from("test").build(),
      new IsEqual<>("test")
    ).affirm();
    new Assertion<>(
      "Couldn't build correctly.",
      RpString.from("test %test%")
        .replaces("%test%")
        .build(MapEntry.from("%test%", () -> "1")),
      new IsEqual<>("test 1")
    ).affirm();
  }

  @Test
  void buildMap() {
  }

  @Test
  void getMaps() {
  }

  @Test
  void getRegex() {
  }

  @Test
  void getReplaces() {
  }

  @Test
  void getValue() {
  }

  @Test
  void map() {
  }

  @Test
  void newSelf() {
  }

  @Test
  void replace() {
  }

  @Test
  void replaces() {
  }

  @Test
  void self() {
  }

  @Test
  void testBuild() {
  }

  @Test
  void testBuild1() {
  }

  @Test
  void testBuild2() {
  }

  @Test
  void testBuild3() {
  }

  @Test
  void testBuildMap() {
  }

  @Test
  void testMap() {
  }

  @Test
  void testReplace() {
  }

  @Test
  void testReplace1() {
  }

  @Test
  void testReplace2() {
  }

  @Test
  void testReplaces() {
  }

  @Test
  void value() {
  }
}
