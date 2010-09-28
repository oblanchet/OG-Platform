/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.timeseries;

import static org.junit.Assert.assertEquals;

import javax.time.calendar.LocalDate;

import org.junit.Test;

/**
 * 
 */
public class EndOfMonthScheduleCalculatorTest {
  private static final Schedule CALCULATOR = new EndOfMonthScheduleCalculator();

  @Test(expected = IllegalArgumentException.class)
  public void testNullStart() {
    CALCULATOR.getSchedule(null, LocalDate.of(2000, 1, 1), true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullEnd() {
    CALCULATOR.getSchedule(LocalDate.of(2000, 1, 1), null, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartAfterEnd() {
    CALCULATOR.getSchedule(LocalDate.of(2001, 1, 1), LocalDate.of(2000, 1, 1), true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartAndEndSameButInvalid() {
    CALCULATOR.getSchedule(LocalDate.of(2001, 2, 3), LocalDate.of(2001, 2, 3), false);
  }

  @Test
  public void testSameDates() {
    final LocalDate date = LocalDate.of(2001, 1, 31);
    final LocalDate[] dates = CALCULATOR.getSchedule(date, date, true);
    assertEquals(dates.length, 1);
    assertEquals(dates[0], date);
  }

  @Test
  public void testMonthlyOnLast() {
    LocalDate startDate = LocalDate.of(2000, 1, 1);
    LocalDate endDate = LocalDate.of(2000, 1, 30);
    LocalDate[] forward = CALCULATOR.getSchedule(startDate, endDate, false);
    LocalDate[] backward = CALCULATOR.getSchedule(startDate, endDate, true);
    assertEquals(forward.length, 0);
    assertEquals(backward.length, 0);
    startDate = LocalDate.of(2002, 1, 31);
    endDate = LocalDate.of(2002, 2, 9);
    forward = CALCULATOR.getSchedule(startDate, endDate, false);
    backward = CALCULATOR.getSchedule(startDate, endDate, true);
    assertEquals(forward.length, 1);
    assertEquals(backward.length, 1);
    assertEquals(forward[0], startDate);
    assertEquals(backward[0], startDate);
    startDate = LocalDate.of(2000, 1, 1);
    endDate = LocalDate.of(2002, 2, 9);
    final int months = 25;
    forward = CALCULATOR.getSchedule(startDate, endDate, false);
    backward = CALCULATOR.getSchedule(startDate, endDate, true);
    assertEquals(forward.length, months);
    assertEquals(backward.length, months);
    final LocalDate firstDate = LocalDate.of(2000, 1, 31);
    assertEquals(forward[0], firstDate);
    assertEquals(backward[0], firstDate);
    final LocalDate lastDate = LocalDate.of(2002, 1, 31);
    assertEquals(forward[months - 1], lastDate);
    assertEquals(backward[months - 1], lastDate);
    LocalDate d1, d2;
    for (int i = 1; i < months; i++) {
      d1 = forward[i];
      d2 = backward[i];
      if (d1.getYear() == forward[i - 1].getYear()) {
        assertEquals(d1.getMonthOfYear().getValue() - forward[i - 1].getMonthOfYear().getValue(), 1);
        assertEquals(d2.getMonthOfYear().getValue() - backward[i - 1].getMonthOfYear().getValue(), 1);
      } else {
        assertEquals(d1.getMonthOfYear().getValue() - forward[i - 1].getMonthOfYear().getValue(), -11);
        assertEquals(d2.getMonthOfYear().getValue() - backward[i - 1].getMonthOfYear().getValue(), -11);
      }
      assertEquals(d1.getDayOfMonth(), d1.getMonthOfYear().lengthInDays(d1.isLeapYear()));
      assertEquals(d2.getDayOfMonth(), forward[i].getMonthOfYear().lengthInDays(d2.isLeapYear()));
    }
  }
}
