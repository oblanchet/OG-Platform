/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.timeseries.exchange;

/**
 * Allows an exchange to be accessed.
 */
public interface ExchangeDataProvider {

  /**
   * Gets an exchange by ISO MIC code.
   * 
   * @param micCode  the MIC code, not null
   * @return the exchange, null if not found
   */
  Exchange getExchange(String micCode);

}
