package com.michaelszymczak.samples.sockets;

/**
 * Created 17/06/18.
 */
public interface Processor {

  long processed(long input);

  class IdentityProcessor implements Processor {

    @Override
    public long processed(long input) {
      return input;
    }
  }
}
