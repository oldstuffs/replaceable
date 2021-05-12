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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract class of replaceable objects.
 *
 * @param <S> type of the implementation itself.
 * @param <X> type of the value.
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class RpBase<S extends RpBase<S, X>, X> {

  /**
   * the maps.
   */
  @NotNull
  @Getter
  private final Collection<UnaryOperator<X>> maps = new ArrayList<>();

  /**
   * the regex.
   */
  @NotNull
  @Getter
  private final Collection<String> regex = new ArrayList<>();

  /**
   * the replaces.
   */
  @NotNull
  @Getter
  private final Map<String, Supplier<String>> replaces = new HashMap<>();

  /**
   * the value.
   */
  @NotNull
  @Getter
  private final X value;

  /**
   * builds the replaceable object with the given entries.
   *
   * @param entries the entry to build.
   *
   * @return built value.
   */
  @SafeVarargs
  @NotNull
  public final X build(@NotNull final Map.Entry<String, Supplier<String>>... entries) {
    return this.build(Arrays.asList(entries));
  }

  /**
   * builds the replaceable object with the given entries.
   *
   * @param entries the entry to build.
   *
   * @return built value.
   */
  @NotNull
  public final X build(@NotNull final Collection<Map.Entry<String, Supplier<String>>> entries) {
    return this.build(entries.stream()
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
  }

  /**
   * builds the replaceable object with the given entry.
   *
   * @param entry the entry to build.
   *
   * @return built value.
   */
  @NotNull
  public final X build(@NotNull final Map.Entry<String, Supplier<String>> entry) {
    return this.build(Collections.singletonList(entry));
  }

  /**
   * builds the replaceable object with the given regex and replace.
   *
   * @param regex the regex to build.
   * @param replace the replace to build.
   *
   * @return built value.
   */
  @NotNull
  public final X build(@NotNull final String regex, @NotNull final Supplier<String> replace) {
    return this.build(Map.entry(regex, replace));
  }

  /**
   * builds the replaceable object with the given replaces.
   *
   * @param replaces the replaces to build.
   *
   * @return built value.
   */
  @NotNull
  public final X build(@NotNull final Map<String, Supplier<String>> replaces) {
    final var value = new AtomicReference<>(this.value);
    this.maps.stream()
      .map(operator -> operator.apply(value.get()))
      .forEach(value::set);
    this.replaces.forEach((regex, supplier) ->
      value.set(this.replace(value.get(), regex, supplier.get())));
    this.regex.stream()
      .filter(replaces::containsKey)
      .forEach(regex ->
        value.set(this.replace(value.get(), regex, replaces.get(regex).get())));
    return value.get();
  }

  /**
   * builds the replacement object with the given function and replaces.
   *
   * @param function the function to build.
   * @param replaces the replaces to build.
   * @param <Y> type of the value.
   *
   * @return built value.
   */
  @NotNull
  public final <Y> Y buildMap(@NotNull final Function<X, Y> function,
                              @NotNull final Map<String, Supplier<String>> replaces) {
    return function.apply(this.build(replaces));
  }

  /**
   * builds the replacement object with the given function and replaces.
   *
   * @param function the function to build.
   * @param replaces the replaces to build.
   * @param <Y> type of the value.
   *
   * @return built value.
   */
  @SafeVarargs
  @NotNull
  public final <Y> Y buildMap(@NotNull final Function<X, Y> function,
                              @NotNull final Map.Entry<String, Supplier<String>>... replaces) {
    return function.apply(this.build(replaces));
  }

  /**
   * builds the replaceable object with the given function.
   *
   * @param function the function to build.
   * @param <Y> type of the value.
   *
   * @return built value.
   */
  @NotNull
  public final <Y> Y buildMap(@NotNull final Function<X, Y> function) {
    return function.apply(this.build());
  }

  /**
   * adds the given map to {@link #maps}.
   *
   * @param map the map to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final S map(@NotNull final Collection<UnaryOperator<X>> map) {
    this.maps.addAll(map);
    return this.self();
  }

  /**
   * adds the given maps to {@link #maps}.
   *
   * @param maps the maps to add.
   *
   * @return {@code this} for builder chain.
   */
  @SafeVarargs
  @NotNull
  public final S map(@NotNull final UnaryOperator<X>... maps) {
    return this.map(List.of(maps));
  }

  /**
   * adds the given regex to {@link #regex}.
   *
   * @param regex the regex to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final S regex(@NotNull final Collection<String> regex) {
    this.regex.addAll(regex);
    return this.self();
  }

  /**
   * adds the given regex to {@link #regex}.
   *
   * @param regex the regex to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final S regex(@NotNull final String... regex) {
    return this.regex(Arrays.asList(regex));
  }

  /**
   * adds the given replaces to {@link #replaces}.
   *
   * @param replaces the replaces to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final S replace(@NotNull final Map<String, Supplier<String>> replaces) {
    this.replaces.putAll(replaces);
    return this.self();
  }

  /**
   * adds the given replaces to {@link #replaces}.
   *
   * @param replaces the replaces to add.
   *
   * @return {@code this} for builder chain.
   */
  @SafeVarargs
  @NotNull
  public final S replace(@NotNull final Map.Entry<String, Supplier<String>>... replaces) {
    Arrays.stream(replaces).forEach(entry ->
      this.replaces.put(entry.getKey(), entry.getValue()));
    return this.self();
  }

  /**
   * adds the given replaces to {@link #replaces}.
   *
   * @param regex the regex to add.
   * @param replace the replace to add.
   *
   * @return {@code this} for builder chain.
   */
  @NotNull
  public final S replace(@NotNull final String regex, @NotNull final Supplier<String> replace) {
    return this.replace(Collections.singletonMap(regex, replace));
  }

  /**
   * creates a new instance of {@link S}.
   *
   * @param value the value to create.
   *
   * @return a newly created instance of {@link S} with {@link #regex}, {@link #replaces}, {@link #maps}.
   */
  @NotNull
  public final S value(@NotNull final X value) {
    return this.newSelf(value).get()
      .regex(this.getRegex())
      .replace(this.getReplaces())
      .map(this.getMaps());
  }

  /**
   * creates a new implementation of {@link S}.
   *
   * @param value the value to create.
   *
   * @return a newly created implementation.
   */
  @NotNull
  protected abstract Supplier<S> newSelf(@NotNull X value);

  /**
   * replaces the given values.
   *
   * @param value the value to replace.
   * @param regex the regex to replace.
   * @param replace the replace to replace.
   *
   * @return replaced value.
   */
  @NotNull
  protected abstract X replace(@NotNull X value, @NotNull CharSequence regex, @NotNull CharSequence replace);

  /**
   * obtains the implementation of {@link S}.
   *
   * @return {@code this}.
   */
  protected abstract S self();
}
