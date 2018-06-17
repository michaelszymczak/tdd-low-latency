package com.michaelszymczak.samples.sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created 17/06/18.
 */
public class EchoServer {

  private final int port;
  private ServerSocket serverSocket;
  private Socket clientSocket;

  public EchoServer(int port) {
    if (port < 1024) {
      throw new IllegalArgumentException("Port below 1024 not allowed");
    }
    this.port = port;
  }

  public EchoServer() {
    this.port = 0;
  }

  public int start() throws IOException {
    this.serverSocket = new ServerSocket(port);
    return serverSocket.getLocalPort();
  }

  public void listen() {
    try {
      this.clientSocket = serverSocket.accept();
      try (DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
        out.writeLong(12345L);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void stop() {
    Exception e1 = null;
    try {
      if (this.serverSocket != null) { this.serverSocket.close(); }
    } catch (Exception e) {
      e1 = e;
    }
    Exception e2 = null;
    try {
      if (this.clientSocket != null) { this.clientSocket.close(); }
    } catch (Exception e) {
      e2 = e;
    }

    if (e1 != null ) {
      throw new RuntimeException(e1);
    }

    if (e2 != null ) {
      throw new RuntimeException(e2);
    }
  }
}
