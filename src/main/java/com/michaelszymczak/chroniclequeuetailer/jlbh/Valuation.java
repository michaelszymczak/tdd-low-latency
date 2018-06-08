package com.michaelszymczak.chroniclequeuetailer.jlbh;

import java.util.Arrays;

/**
 * Created 07/04/18.
 */
public class Valuation {

  private final double[] data = new double[1000];
  private final double someInitialState;

  private double sum = 0.0d;

  public Valuation(double someInitialState) {
    this.someInitialState = someInitialState;
  }

  public double priceOf(long input) {
//    return 0.0;
    //final double data[] = new double[1000];
    for (int i = 0; i < data.length; i++) {
      data[i] = (double) input * i + someInitialState;
    }

    return averageOf(data);
  }


  private double averageOf(double[] input) {
    for (int  i = 0; i < input.length; i++) {
      sum += input[i];
    }
    return sum / input.length;
  }

  private double gaverageOf(double[] input) {
    return Arrays.stream(input).summaryStatistics().getAverage();
  }




}
