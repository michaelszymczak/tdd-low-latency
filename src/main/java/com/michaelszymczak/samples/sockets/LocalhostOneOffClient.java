package com.michaelszymczak.samples.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.net.InetAddress.getLocalHost;

/**
 * Created 17/06/18.
 */
public class LocalhostOneOffClient implements Client {

  private final Port serverPort;

  public LocalhostOneOffClient(Port serverPort) {
    this.serverPort = serverPort;
  }

  @Override
  public void connect() throws IOException {

  }

  @Override
  public long send(long value) throws IOException {
    try (
            Socket client = new Socket(getLocalHost(), serverPort.asInt());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream())
    ) {
      out.writeLong(value);
      return in.readLong();
    }
  }

  @Override
  public void close() throws IOException {

  }
}
