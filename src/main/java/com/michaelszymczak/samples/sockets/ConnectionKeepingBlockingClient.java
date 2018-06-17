package com.michaelszymczak.samples.sockets;

import net.openhft.chronicle.core.io.Closeable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.net.InetAddress.getLocalHost;

/**
 * Created 17/06/18.
 */
public class ConnectionKeepingBlockingClient implements Closeable {

  private final Port serverPort;
  private Socket client;
  private DataOutputStream out;
  private DataInputStream in;

  public ConnectionKeepingBlockingClient(Port serverPort) {
    this.serverPort = serverPort;
  }

  public void connect() throws IOException {
    if (client == null) {
      client = new Socket(getLocalHost(), serverPort.asInt());
      out = new DataOutputStream(client.getOutputStream());
      in = new DataInputStream(client.getInputStream());
    }
  }

  public long send(long value) throws IOException {
      out.writeLong(value);
      return in.readLong();
  }

  @Override
  public void close() {
    if (out != null) {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (in != null) {
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (client != null) {
      try {
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
