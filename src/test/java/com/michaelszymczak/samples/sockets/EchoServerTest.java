package com.michaelszymczak.samples.sockets;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.net.InetAddress.getLocalHost;
import static org.junit.Assert.assertEquals;

/**
 * Created 17/06/18.
 */
public class EchoServerTest {

  ExecutorService executor = Executors.newSingleThreadExecutor();

  @Test(timeout = 2000)
  public void shouldStartAndStop() throws Exception {
    EchoServer server = new EchoServer();
    int serverPort = server.start();
    Future<?> listened = executor.submit(server::listen);
    Thread.sleep(10);
    long result = 0;
    Socket client = new Socket(getLocalHost(), serverPort);
    try (
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream())
    ) {
      out.writeLong(12345L);
      result = in.readLong();
    }

    // then
    assertEquals(12345L, result);


    // cleanup
    listened.get(1, TimeUnit.SECONDS);
    executor.submit(server::stop).get();
    shutdownExecutor();
  }

  private void shutdownExecutor() throws InterruptedException {
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.SECONDS);
    executor.shutdownNow();
  }


}