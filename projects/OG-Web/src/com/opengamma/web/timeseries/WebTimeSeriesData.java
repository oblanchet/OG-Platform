/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.web.timeseries;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.BasicMetaBean;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectMetaProperty;

import com.opengamma.id.UniqueIdentifier;
import com.opengamma.master.timeseries.TimeSeriesDocument;
import com.opengamma.master.timeseries.TimeSeriesLoader;
import com.opengamma.master.timeseries.TimeSeriesMaster;

/**
 * Data class for web-based time series.
 */
@BeanDefinition
public class WebTimeSeriesData extends DirectBean {
  /**
   * The time series master.
   */
  @PropertyDefinition
  private TimeSeriesMaster<?> _timeSeriesMaster;
  /**
   * The timeseries loader
   */
  @PropertyDefinition
  private TimeSeriesLoader _timeSeriesLoader;
  /**
   * The JSR-311 URI information.
   */
  @PropertyDefinition
  private UriInfo _uriInfo;
  /**
   * The time series id from the input URI.
   */
  @PropertyDefinition
  private String _uriTimeSeriesId;
  /**
   * The time series.
   */
  @PropertyDefinition
  private TimeSeriesDocument<?> _timeSeries;
  
  /**
   * Gets the best available security id.
   * @param overrideId  the override id, null derives the result from the data
   * @return the id, may be null
   */
  public String getBestTimeSeriesUriId(final UniqueIdentifier overrideId) {
    if (overrideId != null) {
      return overrideId.toLatest().toString();
    }
    return getTimeSeries() != null ? getTimeSeries().getUniqueIdentifier().toLatest().toString() : getUriTimeSeriesId();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code WebTimeSeriesData}.
   * @return the meta-bean, not null
   */
  public static WebTimeSeriesData.Meta meta() {
    return WebTimeSeriesData.Meta.INSTANCE;
  }

  @Override
  public WebTimeSeriesData.Meta metaBean() {
    return WebTimeSeriesData.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    switch (propertyName.hashCode()) {
      case 1946549030:  // timeSeriesMaster
        return getTimeSeriesMaster();
      case 1930297559:  // timeSeriesLoader
        return getTimeSeriesLoader();
      case -173275078:  // uriInfo
        return getUriInfo();
      case -377735317:  // uriTimeSeriesId
        return getUriTimeSeriesId();
      case 779431844:  // timeSeries
        return getTimeSeries();
    }
    return super.propertyGet(propertyName);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue) {
    switch (propertyName.hashCode()) {
      case 1946549030:  // timeSeriesMaster
        setTimeSeriesMaster((TimeSeriesMaster<?>) newValue);
        return;
      case 1930297559:  // timeSeriesLoader
        setTimeSeriesLoader((TimeSeriesLoader) newValue);
        return;
      case -173275078:  // uriInfo
        setUriInfo((UriInfo) newValue);
        return;
      case -377735317:  // uriTimeSeriesId
        setUriTimeSeriesId((String) newValue);
        return;
      case 779431844:  // timeSeries
        setTimeSeries((TimeSeriesDocument<?>) newValue);
        return;
    }
    super.propertySet(propertyName, newValue);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the time series master.
   * @return the value of the property
   */
  public TimeSeriesMaster<?> getTimeSeriesMaster() {
    return _timeSeriesMaster;
  }

  /**
   * Sets the time series master.
   * @param timeSeriesMaster  the new value of the property
   */
  public void setTimeSeriesMaster(TimeSeriesMaster<?> timeSeriesMaster) {
    this._timeSeriesMaster = timeSeriesMaster;
  }

  /**
   * Gets the the {@code timeSeriesMaster} property.
   * @return the property, not null
   */
  public final Property<TimeSeriesMaster<?>> timeSeriesMaster() {
    return metaBean().timeSeriesMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the timeseries loader
   * @return the value of the property
   */
  public TimeSeriesLoader getTimeSeriesLoader() {
    return _timeSeriesLoader;
  }

  /**
   * Sets the timeseries loader
   * @param timeSeriesLoader  the new value of the property
   */
  public void setTimeSeriesLoader(TimeSeriesLoader timeSeriesLoader) {
    this._timeSeriesLoader = timeSeriesLoader;
  }

  /**
   * Gets the the {@code timeSeriesLoader} property.
   * @return the property, not null
   */
  public final Property<TimeSeriesLoader> timeSeriesLoader() {
    return metaBean().timeSeriesLoader().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the JSR-311 URI information.
   * @return the value of the property
   */
  public UriInfo getUriInfo() {
    return _uriInfo;
  }

  /**
   * Sets the JSR-311 URI information.
   * @param uriInfo  the new value of the property
   */
  public void setUriInfo(UriInfo uriInfo) {
    this._uriInfo = uriInfo;
  }

  /**
   * Gets the the {@code uriInfo} property.
   * @return the property, not null
   */
  public final Property<UriInfo> uriInfo() {
    return metaBean().uriInfo().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the time series id from the input URI.
   * @return the value of the property
   */
  public String getUriTimeSeriesId() {
    return _uriTimeSeriesId;
  }

  /**
   * Sets the time series id from the input URI.
   * @param uriTimeSeriesId  the new value of the property
   */
  public void setUriTimeSeriesId(String uriTimeSeriesId) {
    this._uriTimeSeriesId = uriTimeSeriesId;
  }

  /**
   * Gets the the {@code uriTimeSeriesId} property.
   * @return the property, not null
   */
  public final Property<String> uriTimeSeriesId() {
    return metaBean().uriTimeSeriesId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the time series.
   * @return the value of the property
   */
  public TimeSeriesDocument<?> getTimeSeries() {
    return _timeSeries;
  }

  /**
   * Sets the time series.
   * @param timeSeries  the new value of the property
   */
  public void setTimeSeries(TimeSeriesDocument<?> timeSeries) {
    this._timeSeries = timeSeries;
  }

  /**
   * Gets the the {@code timeSeries} property.
   * @return the property, not null
   */
  public final Property<TimeSeriesDocument<?>> timeSeries() {
    return metaBean().timeSeries().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code WebTimeSeriesData}.
   */
  public static class Meta extends BasicMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code timeSeriesMaster} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<TimeSeriesMaster<?>> _timeSeriesMaster = DirectMetaProperty.ofReadWrite(this, "timeSeriesMaster", (Class) TimeSeriesMaster.class);
    /**
     * The meta-property for the {@code timeSeriesLoader} property.
     */
    private final MetaProperty<TimeSeriesLoader> _timeSeriesLoader = DirectMetaProperty.ofReadWrite(this, "timeSeriesLoader", TimeSeriesLoader.class);
    /**
     * The meta-property for the {@code uriInfo} property.
     */
    private final MetaProperty<UriInfo> _uriInfo = DirectMetaProperty.ofReadWrite(this, "uriInfo", UriInfo.class);
    /**
     * The meta-property for the {@code uriTimeSeriesId} property.
     */
    private final MetaProperty<String> _uriTimeSeriesId = DirectMetaProperty.ofReadWrite(this, "uriTimeSeriesId", String.class);
    /**
     * The meta-property for the {@code timeSeries} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<TimeSeriesDocument<?>> _timeSeries = DirectMetaProperty.ofReadWrite(this, "timeSeries", (Class) TimeSeriesDocument.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map;

    @SuppressWarnings({"unchecked", "rawtypes" })
    protected Meta() {
      LinkedHashMap temp = new LinkedHashMap();
      temp.put("timeSeriesMaster", _timeSeriesMaster);
      temp.put("timeSeriesLoader", _timeSeriesLoader);
      temp.put("uriInfo", _uriInfo);
      temp.put("uriTimeSeriesId", _uriTimeSeriesId);
      temp.put("timeSeries", _timeSeries);
      _map = Collections.unmodifiableMap(temp);
    }

    @Override
    public WebTimeSeriesData createBean() {
      return new WebTimeSeriesData();
    }

    @Override
    public Class<? extends WebTimeSeriesData> beanType() {
      return WebTimeSeriesData.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code timeSeriesMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<TimeSeriesMaster<?>> timeSeriesMaster() {
      return _timeSeriesMaster;
    }

    /**
     * The meta-property for the {@code timeSeriesLoader} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<TimeSeriesLoader> timeSeriesLoader() {
      return _timeSeriesLoader;
    }

    /**
     * The meta-property for the {@code uriInfo} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UriInfo> uriInfo() {
      return _uriInfo;
    }

    /**
     * The meta-property for the {@code uriTimeSeriesId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriTimeSeriesId() {
      return _uriTimeSeriesId;
    }

    /**
     * The meta-property for the {@code timeSeries} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<TimeSeriesDocument<?>> timeSeries() {
      return _timeSeries;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
