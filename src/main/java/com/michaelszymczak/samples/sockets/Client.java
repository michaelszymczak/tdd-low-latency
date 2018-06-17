package com.michaelszymczak.samples.sockets;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created 17/06/18.
 */
public interface Client  extends Closeable {

  void connect() throws IOException;
  long send(long value) throws IOException;
}
