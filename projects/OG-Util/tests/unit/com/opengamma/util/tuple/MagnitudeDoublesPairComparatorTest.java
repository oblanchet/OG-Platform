/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.util.tuple;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

/**
 * Test MagnitudeDoublesPairComparator.
 */
public class MagnitudeDoublesPairComparatorTest {

  @Test
  public void testCompare_differentQuadrants() {
    final DoublesPair first = Pair.of(2.0, 3.0);
    final DoublesPair second = Pair.of(3.0, 2.0);
    final DoublesPair third = Pair.of(4.0, 4.0);
    
    final Comparator<DoublesPair> test = MagnitudeDoublesPairComparator.INSTANCE;
    
    assertTrue(test.compare(first, first) == 0);
    assertTrue(test.compare(first, second) < 0);
    assertTrue(test.compare(first, third) < 0);
    
    assertTrue(test.compare(second, first) > 0);
    assertTrue(test.compare(second, second) == 0);
    assertTrue(test.compare(second, third) < 0);
    
    assertTrue(test.compare(third, first) > 0);
    assertTrue(test.compare(third, second) > 0);
    assertTrue(test.compare(third, third) == 0);
  }

}
