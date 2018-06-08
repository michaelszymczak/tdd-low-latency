package com.michaelszymczak.jlbh;

import com.michaelszymczak.chroniclequeuetailer.jlbh.Valuation;
import net.openhft.chronicle.core.jlbh.JLBH;
import net.openhft.chronicle.core.jlbh.JLBHOptions;
import net.openhft.chronicle.core.jlbh.JLBHResult;
import net.openhft.chronicle.core.jlbh.JLBHResultConsumer;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.PrintStream;
import java.time.Duration;
import java.util.Arrays;

import static com.michaelszymczak.chroniclequeuetailer.jlbh.Memory.asManyBytesAs;
import static com.michaelszymczak.chroniclequeuetailer.jlbh.Memory.usedMemoryInBytes;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofNanos;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created 07/04/18.
 */
public class ValuationTest {
  @Test
  public void testNothing() throws Exception {
  }

  @Test // UNIT TEST
  public void shouldBeCorrectWhenCalculatingThePrice() throws Exception {
    Valuation valuation = new Valuation(15.5);

    double result = valuation.priceOf(1500);

    assertEquals(749265.5, result, 0.0001);
  }

  @Test // NOT A UNIT TEST
  public void shouldAlwaysQuicklyPerformTheTask() throws Exception {
    // given
    final Valuation valuation = createMyComponent();
    final JLBHResultConsumer results = results();
    final JLBH jlbh = new JLBH(parametersWhenTesting(valuation), printStream(), results);

    // when
    jlbh.start();

    // then

    JLBHResult.RunResult latency = results.get().endToEnd().summaryOfLastRun();
    assertThat(String.format("Worst end to end latency was %d microseconds", latency.getWorst().toNanos() / 1000),
            latency.getWorst(), lessThan(ms(1)));
    assertThat(String.format("99.9th percentile latency was %d microseconds", latency.getWorst().toNanos() / 1000),
            latency.get999thPercentile(), lessThan(us(50)));
  }

  @Test // NOT A UNIT TEST
  public void shouldNotRequireGC() throws Exception {
    // given
    final int iterations = 100_000;
    long memoryFootprintBefore, memoryFootprint = 0;
    final Valuation valuation = createMyComponent();
    final double[] results = new double[iterations];


    // when
    for (int i = 0; i < iterations; i++) {
      memoryFootprintBefore = usedMemoryInBytes();
      results[i] = valuation.priceOf(i);
      memoryFootprint+= usedMemoryInBytes() - memoryFootprintBefore;
    }


    // then
    final long target = asManyBytesAs(2 * iterations);

    // TODO: Explicitly check that GC didn't happen (it shouldn't as no garbage generated)
    //       However, this is only a supporting test - the one above is the one that matters
    assertTrue(Arrays.stream(results).allMatch(value -> value >= 0));
    assertThat(memoryFootprint, lessThan(target));
    assertThat(memoryFootprint, is(not(lessThan(0L))));
  }

  @NotNull
  private Valuation createMyComponent() {
    return new Valuation(15.5);
  }

  @NotNull
  private Valuation componentUnderTest() {
    return new Valuation(12.3);
  }

  @NotNull
  private JLBHResultConsumer results() {
    return JLBHResultConsumer.newThreadSafeInstance();
  }

  @NotNull
  private JLBHOptions parametersWhenTesting(Valuation valuation) {
    return new JLBHOptions()
            .warmUpIterations(50_000)
            .iterations(50_000)
            .throughput(10_000)
            .runs(2)
            .recordOSJitter(true)
            .accountForCoordinatedOmmission(true)
            .jlbhTask(new ComponentTestingTask(valuation));
  }

  @NotNull
  private PrintStream printStream() {
//    return new NoOpPrintStream();
    return System.out;
  }

  private Duration us(int us) {
    return ofNanos(us * 1000);
  }

  private Duration ms(int ms) {
    return ofMillis(ms);
  }
}