package com.michaelszymczak.samples.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.util.Objects.requireNonNull;

/**
 * Created 17/06/18.
 */
public class EchoServer {

  private final Processor processor;
  private ServerSocket serverSocket;
  private Socket clientSocket;

  public EchoServer(Processor processor) {
    this.processor = requireNonNull(processor);
  }

  public Port start() throws IOException {
    this.serverSocket = new ServerSocket(0);
    return new Port(serverSocket.getLocalPort());
  }

  public void listen() {
    try {
      this.clientSocket = serverSocket.accept();
      try (
              DataInputStream in = new DataInputStream(clientSocket.getInputStream());
              DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
      ) {
        while (!Thread.currentThread().isInterrupted()) {
          out.writeLong(processor.processed(in.readLong()));
        }
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
