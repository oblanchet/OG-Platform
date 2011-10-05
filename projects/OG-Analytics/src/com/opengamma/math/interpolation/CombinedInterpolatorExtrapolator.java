/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.math.interpolation;

import org.apache.commons.lang.Validate;

import com.opengamma.math.interpolation.data.Interpolator1DDataBundle;

/**
 * 
 */
public class CombinedInterpolatorExtrapolator extends Interpolator1D {
  private static final long serialVersionUID = 1L;
  private final Interpolator1D _interpolator;
  private final Interpolator1D _leftExtrapolator;
  private final Interpolator1D _rightExtrapolator;

  public CombinedInterpolatorExtrapolator(final Interpolator1D interpolator) {
    Validate.notNull(interpolator, "interpolator");
    _interpolator = interpolator;
    _leftExtrapolator = null;
    _rightExtrapolator = null;
  }

  public CombinedInterpolatorExtrapolator(final Interpolator1D interpolator, final Interpolator1D extrapolator) {
    Validate.notNull(interpolator, "interpolator");
    Validate.notNull(extrapolator, "extrapolator");
    _interpolator = interpolator;
    _leftExtrapolator = extrapolator;
    _rightExtrapolator = extrapolator;
  }

  public CombinedInterpolatorExtrapolator(final Interpolator1D interpolator, final Interpolator1D leftExtrapolator, final Interpolator1D rightExtrapolator) {
    Validate.notNull(interpolator, "interpolator");
    Validate.notNull(leftExtrapolator, "left extrapolator");
    Validate.notNull(rightExtrapolator, "right extrapolator");
    _interpolator = interpolator;
    _leftExtrapolator = leftExtrapolator;
    _rightExtrapolator = rightExtrapolator;
  }

  @Override
  public Interpolator1DDataBundle getDataBundle(final double[] x, final double[] y) {
    return _interpolator.getDataBundle(x, y);
  }

  @Override
  public Interpolator1DDataBundle getDataBundleFromSortedArrays(final double[] x, final double[] y) {
    return _interpolator.getDataBundleFromSortedArrays(x, y);
  }

  public Interpolator1D getInterpolator() {
    return _interpolator;
  }

  public Interpolator1D getLeftExtrapolator() {
    return _leftExtrapolator;
  }

  public Interpolator1D getRightExtrapolator() {
    return _rightExtrapolator;
  }

  //TODO  fail earlier if there's no extrapolators?
  @Override
  public Double interpolate(final Interpolator1DDataBundle data, final Double value) {
    Validate.notNull(data, "data");
    Validate.notNull(value, "value");
    if (value < data.firstKey()) {
      if (_leftExtrapolator != null) {
        return _leftExtrapolator.interpolate(data, value);
      }
    } else if (value > data.lastKey()) {
      if (_rightExtrapolator != null) {
        return _rightExtrapolator.interpolate(data, value);
      }
    }
    return _interpolator.interpolate(data, value);
  }

}
