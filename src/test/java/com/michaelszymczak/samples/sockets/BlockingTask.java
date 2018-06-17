package com.michaelszymczak.samples.sockets;

import net.openhft.chronicle.core.jlbh.JLBH;
import net.openhft.chronicle.core.jlbh.JLBHTask;

import static java.util.Objects.requireNonNull;

/**
 * Created 17/06/18.
 */
public class BlockingTask implements JLBHTask {

  private final RoundTrip roundTrip;
  private JLBH jlbh;

  public BlockingTask(RoundTrip roundTrip) {
    this.roundTrip = requireNonNull(roundTrip);
  }

  @Override
  public void init(JLBH jlbh) {
    this.jlbh = jlbh;
  }


  @Override
  public void run(long startTime) {
    long timeWhenShouldBeSent = roundTrip.send(startTime);
    jlbh.sampleNanos(System.nanoTime() - timeWhenShouldBeSent);
  }
}
