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

import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/**
 * an implementation of {@link RpBase} for {@link String}.
 */
public final class RpString extends RpBase<RpString, String> {

  /**
   * ctor.
   *
   * @param value the value.
   */
  private RpString(@NotNull final String value) {
    super(value);
  }

  /**
   * creates a replaceable string instance.
   *
   * @param builder the builder to create.
   *
   * @return a newly created replaceable string.
   */
  @NotNull
  public static RpString from(@NotNull final StringBuilder builder) {
    return RpString.from(builder.toString());
  }

  /**
   * creates a replaceable string instance.
   *
   * @param text the text to create.
   *
   * @return a newly created replaceable string.
   */
  @NotNull
  public static RpString from(@NotNull final String text) {
    return new RpString(text);
  }

  /**
   * creates a replaceable string instance.
   *
   * @param object the object to create.
   *
   * @return a newly created replaceable string.
   */
  @NotNull
  public static RpString fromObject(@NotNull final Object object) {
    return RpString.from(Objects.toString(object));
  }

  @NotNull
  @Override
  public Supplier<RpString> newSelf(@NotNull final String value) {
    return () -> RpString.from(value);
  }

  @NotNull
  @Override
  public String replace(@NotNull final String value, @NotNull final CharSequence regex,
                        @NotNull final CharSequence replace) {
    return value.replace(regex, replace);
  }

  @NotNull
  @Override
  public RpString self() {
    return this;
  }
}
