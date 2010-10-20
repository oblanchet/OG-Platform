/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.math.surface;

import com.opengamma.util.tuple.Pair;

/**
 * 
 */
public class ConstantDoublesSurface extends Surface<Double, Double, Double> {

  public static ConstantDoublesSurface from(final double z) {
    return new ConstantDoublesSurface(z);
  }

  public static ConstantDoublesSurface from(final double z, final String name) {
    return new ConstantDoublesSurface(z, name);
  }

  private final double _z;
  private Double[] _zArray;

  public ConstantDoublesSurface(final double z) {
    super();
    _z = z;
  }

  public ConstantDoublesSurface(final double z, final String name) {
    super(name);
    _z = z;
  }

  @Override
  public Double[] getXData() {
    throw new UnsupportedOperationException("Cannot get x data for constant surface");
  }

  @Override
  public Double[] getYData() {
    throw new UnsupportedOperationException("Cannot get y data for constant surface");
  }

  @Override
  public Double[] getZData() {
    if (_zArray != null) {
      return _zArray;
    }
    _zArray = new Double[] {_z};
    return _zArray;
  }

  @Override
  public int size() {
    return 1;
  }

  @Override
  public Double getZValue(final Double x, final Double y) {
    return _z;
  }

  @Override
  public Double getZValue(final Pair<Double, Double> xy) {
    return _z;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    long temp;
    temp = Double.doubleToLongBits(_z);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ConstantDoublesSurface other = (ConstantDoublesSurface) obj;
    return Double.doubleToLongBits(_z) == Double.doubleToLongBits(other._z);
  }
}
