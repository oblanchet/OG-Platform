/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.opengamma.master.region.RegionDocument;
import com.opengamma.master.region.RegionMaster;

/**
 * Data class for web-based regions.
 */
@BeanDefinition
public class WebRegionData extends DirectBean {

  /**
   * The region master.
   */
  @PropertyDefinition
  private RegionMaster _regionMaster;
  /**
   * The JSR-311 URI information.
   */
  @PropertyDefinition
  private UriInfo _uriInfo;
  /**
   * The region id from the input URI.
   */
  @PropertyDefinition
  private String _uriRegionId;
  /**
   * The version id from the URI.
   */
  @PropertyDefinition
  private String _uriVersionId;
  /**
   * The region.
   */
  @PropertyDefinition
  private RegionDocument _region;
  /**
   * The parents of the region.
   */
  @PropertyDefinition
  private List<RegionDocument> _regionParents = new ArrayList<RegionDocument>();
  /**
   * The children of the region.
   */
  @PropertyDefinition
  private List<RegionDocument> _regionChildren = new ArrayList<RegionDocument>();
  /**
   * The versioned region.
   */
  @PropertyDefinition
  private RegionDocument _versioned;

  /**
   * Creates an instance.
   */
  public WebRegionData() {
  }

  /**
   * Creates an instance.
   * @param uriInfo  the URI information
   */
  public WebRegionData(final UriInfo uriInfo) {
    setUriInfo(uriInfo);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the best available region id.
   * @param overrideId  the override id, null derives the result from the data
   * @return the id, may be null
   */
  public String getBestRegionUriId(final UniqueIdentifier overrideId) {
    if (overrideId != null) {
      return overrideId.toLatest().toString();
    }
    return getRegion() != null ? getRegion().getUniqueId().toLatest().toString() : getUriRegionId();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code WebRegionData}.
   * @return the meta-bean, not null
   */
  public static WebRegionData.Meta meta() {
    return WebRegionData.Meta.INSTANCE;
  }

  @Override
  public WebRegionData.Meta metaBean() {
    return WebRegionData.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    switch (propertyName.hashCode()) {
      case -1820969354:  // regionMaster
        return getRegionMaster();
      case -173275078:  // uriInfo
        return getUriInfo();
      case -2147467077:  // uriRegionId
        return getUriRegionId();
      case 666567687:  // uriVersionId
        return getUriVersionId();
      case -934795532:  // region
        return getRegion();
      case 2045674357:  // regionParents
        return getRegionParents();
      case -524241645:  // regionChildren
        return getRegionChildren();
      case -1407102089:  // versioned
        return getVersioned();
    }
    return super.propertyGet(propertyName);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue) {
    switch (propertyName.hashCode()) {
      case -1820969354:  // regionMaster
        setRegionMaster((RegionMaster) newValue);
        return;
      case -173275078:  // uriInfo
        setUriInfo((UriInfo) newValue);
        return;
      case -2147467077:  // uriRegionId
        setUriRegionId((String) newValue);
        return;
      case 666567687:  // uriVersionId
        setUriVersionId((String) newValue);
        return;
      case -934795532:  // region
        setRegion((RegionDocument) newValue);
        return;
      case 2045674357:  // regionParents
        setRegionParents((List<RegionDocument>) newValue);
        return;
      case -524241645:  // regionChildren
        setRegionChildren((List<RegionDocument>) newValue);
        return;
      case -1407102089:  // versioned
        setVersioned((RegionDocument) newValue);
        return;
    }
    super.propertySet(propertyName, newValue);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the region master.
   * @return the value of the property
   */
  public RegionMaster getRegionMaster() {
    return _regionMaster;
  }

  /**
   * Sets the region master.
   * @param regionMaster  the new value of the property
   */
  public void setRegionMaster(RegionMaster regionMaster) {
    this._regionMaster = regionMaster;
  }

  /**
   * Gets the the {@code regionMaster} property.
   * @return the property, not null
   */
  public final Property<RegionMaster> regionMaster() {
    return metaBean().regionMaster().createProperty(this);
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
   * Gets the region id from the input URI.
   * @return the value of the property
   */
  public String getUriRegionId() {
    return _uriRegionId;
  }

  /**
   * Sets the region id from the input URI.
   * @param uriRegionId  the new value of the property
   */
  public void setUriRegionId(String uriRegionId) {
    this._uriRegionId = uriRegionId;
  }

  /**
   * Gets the the {@code uriRegionId} property.
   * @return the property, not null
   */
  public final Property<String> uriRegionId() {
    return metaBean().uriRegionId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the version id from the URI.
   * @return the value of the property
   */
  public String getUriVersionId() {
    return _uriVersionId;
  }

  /**
   * Sets the version id from the URI.
   * @param uriVersionId  the new value of the property
   */
  public void setUriVersionId(String uriVersionId) {
    this._uriVersionId = uriVersionId;
  }

  /**
   * Gets the the {@code uriVersionId} property.
   * @return the property, not null
   */
  public final Property<String> uriVersionId() {
    return metaBean().uriVersionId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the region.
   * @return the value of the property
   */
  public RegionDocument getRegion() {
    return _region;
  }

  /**
   * Sets the region.
   * @param region  the new value of the property
   */
  public void setRegion(RegionDocument region) {
    this._region = region;
  }

  /**
   * Gets the the {@code region} property.
   * @return the property, not null
   */
  public final Property<RegionDocument> region() {
    return metaBean().region().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the parents of the region.
   * @return the value of the property
   */
  public List<RegionDocument> getRegionParents() {
    return _regionParents;
  }

  /**
   * Sets the parents of the region.
   * @param regionParents  the new value of the property
   */
  public void setRegionParents(List<RegionDocument> regionParents) {
    this._regionParents = regionParents;
  }

  /**
   * Gets the the {@code regionParents} property.
   * @return the property, not null
   */
  public final Property<List<RegionDocument>> regionParents() {
    return metaBean().regionParents().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the children of the region.
   * @return the value of the property
   */
  public List<RegionDocument> getRegionChildren() {
    return _regionChildren;
  }

  /**
   * Sets the children of the region.
   * @param regionChildren  the new value of the property
   */
  public void setRegionChildren(List<RegionDocument> regionChildren) {
    this._regionChildren = regionChildren;
  }

  /**
   * Gets the the {@code regionChildren} property.
   * @return the property, not null
   */
  public final Property<List<RegionDocument>> regionChildren() {
    return metaBean().regionChildren().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the versioned region.
   * @return the value of the property
   */
  public RegionDocument getVersioned() {
    return _versioned;
  }

  /**
   * Sets the versioned region.
   * @param versioned  the new value of the property
   */
  public void setVersioned(RegionDocument versioned) {
    this._versioned = versioned;
  }

  /**
   * Gets the the {@code versioned} property.
   * @return the property, not null
   */
  public final Property<RegionDocument> versioned() {
    return metaBean().versioned().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code WebRegionData}.
   */
  public static class Meta extends BasicMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code regionMaster} property.
     */
    private final MetaProperty<RegionMaster> _regionMaster = DirectMetaProperty.ofReadWrite(this, "regionMaster", RegionMaster.class);
    /**
     * The meta-property for the {@code uriInfo} property.
     */
    private final MetaProperty<UriInfo> _uriInfo = DirectMetaProperty.ofReadWrite(this, "uriInfo", UriInfo.class);
    /**
     * The meta-property for the {@code uriRegionId} property.
     */
    private final MetaProperty<String> _uriRegionId = DirectMetaProperty.ofReadWrite(this, "uriRegionId", String.class);
    /**
     * The meta-property for the {@code uriVersionId} property.
     */
    private final MetaProperty<String> _uriVersionId = DirectMetaProperty.ofReadWrite(this, "uriVersionId", String.class);
    /**
     * The meta-property for the {@code region} property.
     */
    private final MetaProperty<RegionDocument> _region = DirectMetaProperty.ofReadWrite(this, "region", RegionDocument.class);
    /**
     * The meta-property for the {@code regionParents} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<RegionDocument>> _regionParents = DirectMetaProperty.ofReadWrite(this, "regionParents", (Class) List.class);
    /**
     * The meta-property for the {@code regionChildren} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<RegionDocument>> _regionChildren = DirectMetaProperty.ofReadWrite(this, "regionChildren", (Class) List.class);
    /**
     * The meta-property for the {@code versioned} property.
     */
    private final MetaProperty<RegionDocument> _versioned = DirectMetaProperty.ofReadWrite(this, "versioned", RegionDocument.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map;

    @SuppressWarnings({"unchecked", "rawtypes" })
    protected Meta() {
      LinkedHashMap temp = new LinkedHashMap();
      temp.put("regionMaster", _regionMaster);
      temp.put("uriInfo", _uriInfo);
      temp.put("uriRegionId", _uriRegionId);
      temp.put("uriVersionId", _uriVersionId);
      temp.put("region", _region);
      temp.put("regionParents", _regionParents);
      temp.put("regionChildren", _regionChildren);
      temp.put("versioned", _versioned);
      _map = Collections.unmodifiableMap(temp);
    }

    @Override
    public WebRegionData createBean() {
      return new WebRegionData();
    }

    @Override
    public Class<? extends WebRegionData> beanType() {
      return WebRegionData.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code regionMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<RegionMaster> regionMaster() {
      return _regionMaster;
    }

    /**
     * The meta-property for the {@code uriInfo} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UriInfo> uriInfo() {
      return _uriInfo;
    }

    /**
     * The meta-property for the {@code uriRegionId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriRegionId() {
      return _uriRegionId;
    }

    /**
     * The meta-property for the {@code uriVersionId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriVersionId() {
      return _uriVersionId;
    }

    /**
     * The meta-property for the {@code region} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<RegionDocument> region() {
      return _region;
    }

    /**
     * The meta-property for the {@code regionParents} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<RegionDocument>> regionParents() {
      return _regionParents;
    }

    /**
     * The meta-property for the {@code regionChildren} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<RegionDocument>> regionChildren() {
      return _regionChildren;
    }

    /**
     * The meta-property for the {@code versioned} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<RegionDocument> versioned() {
      return _versioned;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
