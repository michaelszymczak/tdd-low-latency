package com.michaelszymczak.samples.sockets;

import org.junit.Test;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;
import static org.junit.Assert.*;

/**
 * Created 17/06/18.
 */
public class RoundTripTest {

  private final RoundTrip roundTrip = new RoundTrip();

  @Test
  public void shouldReturnSentNumber() throws Exception {
    assertEquals(MIN_VALUE, roundTrip.send(MIN_VALUE));
    assertEquals(0, roundTrip.send(0));
    assertEquals(1, roundTrip.send(1));
    assertEquals(MAX_VALUE, roundTrip.send(MAX_VALUE));
  }
}