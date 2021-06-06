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

import java.util.List;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

final class RpListTest {

  @Test
  void from() {
    new Assertion<>(
      "Couldn't created replaceable string correctly.",
      RpList.from(new StringBuilder()
        .append("test")
        .append('\n')
        .append("test")),
      new IsEqual<>(RpList.from("test\ntest"))
    ).affirm();
  }

  @Test
  void fromObjects() {
    new Assertion<>(
      "Couldn't created replaceable string correctly.",
      RpList.fromObjects(new StringBuilder("test\ntest")),
      new IsEqual<>(RpList.from("test\ntest"))
    ).affirm();
  }

  @Test
  void newSelf() {
    final var original = RpList.from("test");
    new Assertion<>(
      "Couldn't create a new replaceable object correctly.",
      original,
      new IsEqual<>(original.newSelf(List.of("test")).get())
    ).affirm();
  }

  @Test
  void replace() {
    new Assertion<>(
      "Couldn't replace the object.",
      RpList.from("").replace(List.of("test %test%"), "%test%", "1"),
      new IsEqual<>(List.of("test 1"))
    ).affirm();
  }

  @Test
  void self() {
    final var original = RpList.from("");
    new Assertion<>(
      "Couldn't get the original object correctly.",
      original,
      new IsEqual<>(original.self())
    );
  }

  @Test
  void testFromObjects() {
    new Assertion<>(
      "Couldn't created replaceable string correctly.",
      RpList.fromObjects(List.of(new StringBuilder("test\ntest"))),
      new IsEqual<>(RpList.from("test\ntest"))
    ).affirm();
  }
}
