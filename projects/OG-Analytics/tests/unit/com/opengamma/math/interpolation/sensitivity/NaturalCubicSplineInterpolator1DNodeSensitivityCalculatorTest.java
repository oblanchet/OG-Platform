/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.math.interpolation.sensitivity;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import cern.jet.random.engine.MersenneTwister64;
import cern.jet.random.engine.RandomEngine;

import com.opengamma.math.function.Function1D;
import com.opengamma.math.interpolation.Interpolator1D;
import com.opengamma.math.interpolation.NaturalCubicSplineInterpolator1D;
import com.opengamma.math.interpolation.data.Interpolator1DCubicSplineDataBundle;
import com.opengamma.math.interpolation.data.Interpolator1DDataBundle;

/**
 * 
 */
public class NaturalCubicSplineInterpolator1DNodeSensitivityCalculatorTest {
  private static final RandomEngine RANDOM = new MersenneTwister64(MersenneTwister64.DEFAULT_SEED);
  private static final Interpolator1D<Interpolator1DCubicSplineDataBundle> INTERPOLATOR = new NaturalCubicSplineInterpolator1D();
  private static final NaturalCubicSplineInterpolator1DNodeSensitivityCalculator CALCULATOR = new NaturalCubicSplineInterpolator1DNodeSensitivityCalculator();
  private static final FiniteDifferenceInterpolator1DNodeSensitivityCalculator<Interpolator1DCubicSplineDataBundle> FD_CALCULATOR = new FiniteDifferenceInterpolator1DNodeSensitivityCalculator<Interpolator1DCubicSplineDataBundle>(
      INTERPOLATOR);
  private static final Interpolator1DCubicSplineDataBundle DATA1;
  private static final double EPS = 1e-7;
  private static final Function1D<Double, Double> FUNCTION = new Function1D<Double, Double>() {

    private static final double a = -0.045;
    private static final double b = 0.03;
    private static final double c = 0.3;
    private static final double d = 0.05;

    @Override
    public Double evaluate(final Double x) {
      return (a + b * x) * Math.exp(-c * x) + d;
    }

  };

  static {
    final double[] t = new double[] {0.0, 0.5, 1.0, 2.0, 3.0, 5.0, 7.0, 10.0, 15.0, 17.5, 20.0, 25.0, 30.0};
    final int n = t.length;
    final double[] r = new double[n];
    for (int i = 0; i < n; i++) {
      r[i] = FUNCTION.evaluate(t[i]);
    }
    DATA1 = INTERPOLATOR.getDataBundleFromSortedArrays(t, r);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInputMap() {
    INTERPOLATOR.interpolate(null, 3.);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInterpolateValue() {
    INTERPOLATOR.interpolate(DATA1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHighValue() {
    INTERPOLATOR.interpolate(DATA1, 31.);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowValue() {
    INTERPOLATOR.interpolate(DATA1, -1.);
  }

  @Test
  public void testSensitivities() {
    final double tmax = DATA1.lastKey();
    double[] sensitivity;
    for (int i = 0; i < 100; i++) {
      final double t = tmax * RANDOM.nextDouble();
      sensitivity = CALCULATOR.calculate(DATA1, t);
      for (int j = 0; j < sensitivity.length; j++) {
        assertEquals(getSensitivity(DATA1, INTERPOLATOR, t, j), sensitivity[j], EPS);
      }
    }
  }

  @Test
  public void testYieldCurve() {
    final double[] fwdTimes = new double[] {0.0, 1.0, 2.0, 5.0, 10.0, 20.0, 31.0};
    final int n = fwdTimes.length;
    final double[] rates = new double[n];
    for (int i = 0; i < n; i++) {
      rates[i] = FUNCTION.evaluate(fwdTimes[i]);
    }
    final Interpolator1DCubicSplineDataBundle data = new Interpolator1DCubicSplineDataBundle(INTERPOLATOR.getDataBundleFromSortedArrays(fwdTimes, rates));
    final double[] sensitivity1 = CALCULATOR.calculate(data, 0.25);
    final double[] sensitivity2 = FD_CALCULATOR.calculate(data, 0.25);
    for (int j = 0; j < sensitivity1.length; j++) {
      assertEquals(sensitivity1[j], sensitivity2[j], EPS);
    }
  }

  private <T extends Interpolator1DDataBundle> double getSensitivity(final T model, final Interpolator1D<T> interpolator, final double t, final int node) {
    final double[] x = model.getKeys();
    final double[] y = model.getValues();
    final int n = y.length;
    double[] yUp = new double[n];
    double[] yDown = new double[n];
    yUp = Arrays.copyOf(y, n);
    yDown = Arrays.copyOf(y, n);
    yUp[node] += EPS;
    yDown[node] -= EPS;
    final T modelUp = interpolator.getDataBundleFromSortedArrays(x, yUp);
    final T modelDown = interpolator.getDataBundleFromSortedArrays(x, yDown);
    final double up = interpolator.interpolate(modelUp, t);
    final double down = interpolator.interpolate(modelDown, t);
    return (up - down) / 2.0 / EPS;
  }
}
