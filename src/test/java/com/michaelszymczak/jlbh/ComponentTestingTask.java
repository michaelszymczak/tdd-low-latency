package com.michaelszymczak.jlbh;

import com.michaelszymczak.chroniclequeuetailer.jlbh.Valuation;
import net.openhft.chronicle.core.jlbh.JLBH;
import net.openhft.chronicle.core.jlbh.JLBHTask;

import static java.util.Objects.requireNonNull;

/**
 * Created 07/04/18.
 */
public class ComponentTestingTask implements JLBHTask {

  private final Valuation componentUnderTest;
  private final ComputationEnforcer computationEnforcer = new ComputationEnforcer();

  private JLBH jlbh;


  public ComponentTestingTask(Valuation componentUnderTest) {
    this.componentUnderTest = requireNonNull(componentUnderTest);
  }

  @Override
  public void init(JLBH jlbh) {
    this.jlbh = jlbh;
  }

  @Override
  public void run(long startTimeNS) {
    double result = componentUnderTest.priceOf(startTimeNS);
    jlbh.sampleNanos(System.nanoTime() - startTimeNS);
    computationEnforcer.process(result);
  }

  @Override
  public void complete(){
    System.out.println(computationEnforcer.useResult());
  }


}
