package com.michaelszymczak.samples.sockets;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

/**
 * Created 17/06/18.
 */
public class RoundTrip {

  private final Client client;

  public RoundTrip(Client client) {
    this.client = requireNonNull(client);
  }

  public long send(long content) {
    try {
      return client.send(content);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
