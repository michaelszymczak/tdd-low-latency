package com.michaelszymczak.samples.sockets;

/**
 * Created 17/06/18.
 */
public class Port {
  private final int port;

  public Port(int port) {
    this.port = port;
  }

  public int asInt() {
    return port;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Port port1 = (Port) o;

    return port == port1.port;

  }

  @Override
  public int hashCode() {
    return port;
  }

  @Override
  public String toString() {
    return "Port{" +
            "port=" + port +
            '}';
  }
}
