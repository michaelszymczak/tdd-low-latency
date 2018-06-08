package com.michaelszymczak.jlbh;

/**
 * Created 12/04/18.
 */
public class ComputationEnforcer {

  private long totalRuns = 0;
  private double forcingComputation = 0.0d;

  void process(double result) {
    forcingComputation = (forcingComputation + result) % 1000;
    totalRuns++;
  }


  String useResult() {
    return String.format("Total runs %d, totalSum %f", totalRuns, forcingComputation);
  }
}
