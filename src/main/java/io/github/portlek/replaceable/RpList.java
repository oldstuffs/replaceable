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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * an implementation of {@link RpBase} for {@link List} {@link String}.
 */
public final class RpList extends RpBase<RpList, List<String>> {

  /**
   * ctor.
   *
   * @param value the value.
   */
  private RpList(@NotNull final List<String> value) {
    super(value);
  }

  /**
   * creates a replaceable list instance.
   *
   * @param objects the objects to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final Object... objects) {
    return RpList.from(Arrays.stream(objects).map(Objects::toString).collect(Collectors.toList()));
  }

  /**
   * creates a replaceable list instance.
   *
   * @param list the list to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final List<Object> list) {
    return from(list.stream().map(Objects::toString).collect(Collectors.toList()));
  }

  /**
   * creates a replaceable list instance.
   *
   * @param texts the texts to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final String... texts) {
    return RpList.from(Arrays.asList(texts));
  }

  /**
   * creates a replaceable list instance.
   *
   * @param list the list to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final List<String> list) {
    return new RpList(list);
  }

  /**
   * creates a replaceable list instance.
   *
   * @param builders the builders to create.
   *
   * @return a newly created replaceable list.
   */
  @NotNull
  public static RpList from(@NotNull final StringBuilder... builders) {
    return RpList.from(Arrays.stream(builders)
      .map(StringBuilder::toString)
      .collect(Collectors.toList()));
  }

  @NotNull
  @Override
  public Supplier<RpList> newSelf(@NotNull final List<String> value) {
    return () -> new RpList(value);
  }

  @NotNull
  @Override
  public List<String> replace(@NotNull final List<String> value, @NotNull final CharSequence regex,
                              @NotNull final CharSequence replace) {
    return value.stream()
      .map(s -> s.replace(regex, replace))
      .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public RpList self() {
    return this;
  }
}
