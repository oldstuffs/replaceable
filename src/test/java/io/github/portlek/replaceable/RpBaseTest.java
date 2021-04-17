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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.hamcrest.core.IsEqual;
import org.hamcrest.object.HasEqualValues;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValues;

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
    new Assertion<>(
      "Couldn't build correctly.",
      RpString.from("test").buildMap(s -> s + "-1"),
      new IsEqual<>("test-1")
    ).affirm();
    new Assertion<>(
      "Couldn't build correctly.",
      RpString.from("test %test%")
        .replaces("%test%")
        .buildMap(s -> s + "-1", Map.of("%test%", () -> "1")),
      new IsEqual<>("test 1-1")
    ).affirm();
  }

  @Test
  void getMaps() {
    final var map = (UnaryOperator<String>) s -> s + 1;
    final var original = RpString.from("")
      .map(map);
    new Assertion<>(
      "Couldn't get the map.",
      original.getMaps(),
      new HasValues<>(map)
    ).affirm();
  }

  @Test
  void getRegex() {
    final var regex = "%regex%";
    final var original = RpString.from("")
      .replaces("%regex%");
    new Assertion<>(
      "Couldn't get the regex.",
      original.getRegex(),
      new HasValues<>(regex)
    ).affirm();
  }

  @Test
  void getReplaces() {
    final var replaces = new HashMap<String, Supplier<String>>();
    replaces.put("%regex%", () -> "test");
    final var original = RpString.from("")
      .replace(replaces);
    new Assertion<>(
      "Couldn't get the replaces.",
      original.getReplaces(),
      new HasEqualValues<>(replaces)
    ).affirm();
  }

  @Test
  void getValue() {
    final var original = RpString.from("test");
    new Assertion<>(
      "Couldn't get the value.",
      original.getValue(),
      new IsEqual<>("test")
    );
  }

  @Test
  void map() {
    final var map = (UnaryOperator<String>) s -> s + 1;
    final var original = RpString.from("")
      .map(map);
    new Assertion<>(
      "Couldn't add the map.",
      original.getMaps(),
      new HasSize(1)
    ).affirm();
  }

  @Test
  void replace() {
    final var original = RpString.from("")
      .replace("test-1", () -> "test-1")
      .replace(MapEntry.from("test-2", () -> "test-2"))
      .replace(Map.of("test-3", () -> "test-3"));
    new Assertion<>(
      "Couldn't add the replace.",
      original.getReplaces().values(),
      new HasSize(3)
    ).affirm();
  }

  @Test
  void replaces() {
  }

  @Test
  void value() {
    final var map = (UnaryOperator<String>) s -> s;
    final var replace = (Supplier<String>) () -> "test";
    final var original = RpString.from("test")
      .replaces("%test%")
      .map(map)
      .replace("%text%", replace);
    final var expected = RpString.from("test-2")
      .replaces("%test%")
      .map(map)
      .replace("%text%", replace);
    new Assertion<>(
      "Couldn't change the value.",
      original.value("test-2"),
      new IsEqual<>(expected)
    );
  }
}
