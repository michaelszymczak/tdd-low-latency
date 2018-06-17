package com.michaelszymczak.samples.sockets;

import net.openhft.chronicle.core.jlbh.JLBH;
import net.openhft.chronicle.core.jlbh.JLBHOptions;
import net.openhft.chronicle.core.jlbh.JLBHResult;
import net.openhft.chronicle.core.jlbh.JLBHResultConsumer;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.time.Duration;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofNanos;
import static net.openhft.chronicle.core.jlbh.JLBHResultConsumer.newThreadSafeInstance;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created 17/06/18.
 */
public class RoundTripTest {

  @Test
  public void shouldReturnSentNumber() throws Exception {
    final RoundTrip roundTrip = new RoundTrip();

    assertEquals(MIN_VALUE, roundTrip.send(MIN_VALUE));
    assertEquals(0, roundTrip.send(0));
    assertEquals(1, roundTrip.send(1));
    assertEquals(MAX_VALUE, roundTrip.send(MAX_VALUE));
  }

  @Test
  public void shouldHaveAcceptableLatency() throws Exception {
    final RoundTrip roundTrip = new RoundTrip();
    final JLBHResultConsumer results = newThreadSafeInstance();
    final JLBH jlbh = new JLBH(parametersWhenTesting(roundTrip), System.out, results);

    // when
    jlbh.start();

    // then
    JLBHResult.RunResult latency = results.get().endToEnd().summaryOfLastRun();
    assertThat(String.format("Worst end to end latency was %d microseconds", latency.getWorst().toNanos() / 1000),
            latency.getWorst(), lessThan(ms(1)));
    assertThat(String.format("99.9th percentile latency was %d microseconds", latency.getWorst().toNanos() / 1000),
            latency.get999thPercentile(), lessThan(us(50)));
  }

  @NotNull
  private JLBHOptions parametersWhenTesting(RoundTrip roundTrip) {
    return new JLBHOptions()
            .warmUpIterations(5_000)
            .iterations(5_000)
            .throughput(1000)
            .runs(2)
            .recordOSJitter(true)
            .accountForCoordinatedOmmission(true)
            .jlbhTask(new BlockingTask(roundTrip));
  }

  private Duration us(int us) {
    return ofNanos(us * 1000);
  }

  private Duration ms(int ms) {
    return ofMillis(ms);
  }
}