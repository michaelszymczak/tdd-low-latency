package com.michaelszymczak.samples.sockets;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created 17/06/18.
 */
public class EchoServerTest {

  @Test(timeout = 2000)
  public void shouldRespondWithTheSameValue() throws Exception {
    EchoServerManager echoServerManager = new EchoServerManager();
    Port serverPort = echoServerManager.startAndListen();
    OneOffBlockingClient client = new OneOffBlockingClient(serverPort);

    // when
    long result1 = client.send(12345L);


    // then
    assertEquals(12345L, result1);


    // cleanup
    echoServerManager.stop();
  }

  @Test
  public void shouldHandleMultipleRequests() throws Exception {
    EchoServerManager echoServerManager = new EchoServerManager();
    Port serverPort = echoServerManager.startAndListen();
    ConnectionKeepingBlockingClient client = new ConnectionKeepingBlockingClient(serverPort);
    client.connect();
    client.send(1);
    client.send(2);
    client.send(3);

    // when
    long result1 = client.send(4L);


    // then
    assertEquals(4L, result1);


    // cleanup
    client.close();
    echoServerManager.stop();
  }


}