/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.timeseries.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.core.config.ConfigSource;
import com.opengamma.id.IdentifierBundle;
import com.opengamma.master.timeseries.TimeSeriesDocument;
import com.opengamma.master.timeseries.TimeSeriesMaster;
import com.opengamma.master.timeseries.TimeSeriesInfo;
import com.opengamma.master.timeseries.TimeSeriesInfoResolver;
import com.opengamma.master.timeseries.TimeSeriesSearchRequest;
import com.opengamma.master.timeseries.TimeSeriesSearchResult;
import com.opengamma.util.ArgumentChecker;

/**
 * Simple time-series resolver, returns the best match from the time-series info in the data store.
 * <p>
 * This resolver relies on configuration in the config database.
 * 
 * @param <T> the type of the time-series, such as LocalDate/LocalDateTime
 */
public class DefaultTimeSeriesInfoResolver<T> implements TimeSeriesInfoResolver {

  /** Logger. */
  private static final Logger s_logger = LoggerFactory.getLogger(DefaultTimeSeriesInfoResolver.class);

  /**
   * The time-series master.
   */
  private final TimeSeriesMaster<T> _tsMaster;
  /**
   * The source of configuration.
   */
  private final ConfigSource _configSource;

  /**
   * Creates an instance from a master and config source.
   * 
   * @param timeSeriesMaster  the time-series master, not null
   * @param configSource  the config source, not null
   */
  public DefaultTimeSeriesInfoResolver(TimeSeriesMaster<T> timeSeriesMaster, ConfigSource configSource) {
    ArgumentChecker.notNull(timeSeriesMaster, "timeseries master");
    ArgumentChecker.notNull(configSource, "configSource");
    _configSource = configSource;
    _tsMaster = timeSeriesMaster;
  }

  //-------------------------------------------------------------------------
  @Override
  public TimeSeriesInfo getInfo(IdentifierBundle securityBundle, String configName) {
    ArgumentChecker.notNull(securityBundle, "securityBundle");
    ArgumentChecker.notNull(configName, "configName");
    
    TimeSeriesSearchRequest<T> searchRequest = new TimeSeriesSearchRequest<T>(securityBundle);
    searchRequest.setLoadTimeSeries(false);
    TimeSeriesSearchResult<T> searchResult = _tsMaster.search(searchRequest);
    
    // load rules from config datastore
    TimeSeriesInfoConfiguration ruleSet = _configSource.getLatestByName(TimeSeriesInfoConfiguration.class, configName);
    if (ruleSet != null) {
      List<TimeSeriesInfo> infos = extractTimeSeriesInfo(searchResult);
      return bestMatch(infos, ruleSet);
    } else {
      s_logger.warn("can not resolve time-series info because rules set with name {} can not be loaded from config database", configName);
      return null;
    }
  }

  private List<TimeSeriesInfo> extractTimeSeriesInfo(TimeSeriesSearchResult<T> searchResult) {
    List<TimeSeriesDocument<T>> documents = searchResult.getDocuments();
    List<TimeSeriesInfo> infoList = new ArrayList<TimeSeriesInfo>(documents.size());
    for (TimeSeriesDocument<T> document : documents) {
      if (document.getDataField().equals(DEFAULT_DATA_FIELD)) {
        infoList.add(document.toInfo());
      }
    }
    return infoList;
  }

  private TimeSeriesInfo bestMatch(List<TimeSeriesInfo> infoList, TimeSeriesInfoRateProvider ruleSet) {
    TreeMap<Integer, TimeSeriesInfo> scores = new TreeMap<Integer, TimeSeriesInfo>();
    for (TimeSeriesInfo info : infoList) {
      int score = ruleSet.rate(info);
      s_logger.debug("score: {} for info: {} using rules: {} ", new Object[]{score, info, ruleSet});
      scores.put(score, info);
    }
    if (!scores.isEmpty()) {
      Integer max = scores.lastKey();
      return scores.get(max);
    }
    return null;
  }

}
