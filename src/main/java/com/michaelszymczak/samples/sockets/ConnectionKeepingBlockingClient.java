package com.michaelszymczak.samples.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static java.net.InetAddress.getLoopbackAddress;
import static java.util.Objects.requireNonNull;

/**
 * Created 17/06/18.
 */
public class ConnectionKeepingBlockingClient implements Client {

  private final Port serverPort;
  private final InetAddress inetAddress;

  private Socket client;
  private DataOutputStream out;
  private DataInputStream in;

  public static ConnectionKeepingBlockingClient connectedTo(InetAddress inetAddress, Port serverPort) throws IOException {
    ConnectionKeepingBlockingClient client = new ConnectionKeepingBlockingClient(inetAddress, serverPort);
    client.connect();

    return client;
  }

  public ConnectionKeepingBlockingClient(Port serverPort) {
    this(getLoopbackAddress(), serverPort);
  }

  public ConnectionKeepingBlockingClient(InetAddress inetAddress, Port serverPort) {
    this.inetAddress = requireNonNull(inetAddress);
    this.serverPort = serverPort;
  }

  @Override
  public void connect() throws IOException {
    if (client == null) {
      client = new Socket(inetAddress, serverPort.asInt());
      out = new DataOutputStream(client.getOutputStream());
      in = new DataInputStream(client.getInputStream());
    }
  }

  @Override
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
