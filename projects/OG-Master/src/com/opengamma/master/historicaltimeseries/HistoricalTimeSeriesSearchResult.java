/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.historicaltimeseries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.master.AbstractSearchResult;
import com.opengamma.util.PublicSPI;

/**
 * Result from searching for historical time-series.
 * <p>
 * The returned documents will match the search criteria.
 * See {@link HistoricalTimeSeriesSearchRequest} for more details.
 * <p>
 * This class is mutable and not thread-safe.
 */
@PublicSPI
@BeanDefinition
public class HistoricalTimeSeriesSearchResult extends AbstractSearchResult<HistoricalTimeSeriesDocument> {

  /**
   * Creates an instance.
   */
  public HistoricalTimeSeriesSearchResult() {
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the returned series from within the documents.
   * 
   * @return the series, not null
   */
  public List<ManageableHistoricalTimeSeries> getSeriesList() {
    List<ManageableHistoricalTimeSeries> result = new ArrayList<ManageableHistoricalTimeSeries>();
    if (getDocuments() != null) {
      for (HistoricalTimeSeriesDocument doc : getDocuments()) {
        result.add(doc.getSeries());
      }
    }
    return result;
  }

  /**
   * Gets the first series, or null if no documents.
   * 
   * @return the first series, null if none
   */
  public ManageableHistoricalTimeSeries getFirstSeries() {
    return getDocuments().size() > 0 ? getDocuments().get(0).getSeries() : null;
  }

  /**
   * Gets the single result expected from a query.
   * <p>
   * This throws an exception if more than 1 result is actually available.
   * Thus, this method implies an assumption about uniqueness of the queried series.
   * 
   * @return the matching exchange, not null
   * @throws IllegalStateException if no series was found
   */
  public ManageableHistoricalTimeSeries getSingleSeries() {
    if (getDocuments().size() != 1) {
      throw new OpenGammaRuntimeException("Expecting zero or single resulting match, and was " + getDocuments().size());
    } else {
      return getDocuments().get(0).getSeries();
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HistoricalTimeSeriesSearchResult}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static HistoricalTimeSeriesSearchResult.Meta meta() {
    return HistoricalTimeSeriesSearchResult.Meta.INSTANCE;
  }

  @Override
  public HistoricalTimeSeriesSearchResult.Meta metaBean() {
    return HistoricalTimeSeriesSearchResult.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    return super.propertyGet(propertyName);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue) {
    super.propertySet(propertyName, newValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code HistoricalTimeSeriesSearchResult}.
   */
  public static class Meta extends AbstractSearchResult.Meta<HistoricalTimeSeriesDocument> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends HistoricalTimeSeriesSearchResult> builder() {
      return new DirectBeanBuilder<HistoricalTimeSeriesSearchResult>(new HistoricalTimeSeriesSearchResult());
    }

    @Override
    public Class<? extends HistoricalTimeSeriesSearchResult> beanType() {
      return HistoricalTimeSeriesSearchResult.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
