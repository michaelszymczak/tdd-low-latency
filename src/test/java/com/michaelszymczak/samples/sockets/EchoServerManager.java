package com.michaelszymczak.samples.sockets;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created 17/06/18.
 */
public class EchoServerManager {

  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  private EchoServer server;
  private final Processor serverSideProcessor;

  public EchoServerManager() {
    this(new Processor.IdentityProcessor());
  }

  public EchoServerManager(Processor serverSideProcessor) {
    this.serverSideProcessor = Objects.requireNonNull(serverSideProcessor);
  }

  public Port startAndListen() throws IOException, InterruptedException {
    server = new EchoServer(serverSideProcessor);
    final Port serverPort = server.start();
    executor.submit(server::listen);
    Thread.sleep(100);

    return serverPort;
  }

  public void stop() throws ExecutionException, InterruptedException {
    executor.submit(server::stop).get();
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.SECONDS);
    executor.shutdownNow();
  }

}
