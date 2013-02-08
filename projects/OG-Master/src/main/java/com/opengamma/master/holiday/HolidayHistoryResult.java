/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.holiday;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.master.AbstractHistoryResult;
import com.opengamma.util.PublicSPI;

/**
 * Result providing the history of a holiday.
 * <p>
 * The returned documents may be a mixture of versions and corrections.
 * The document instant fields are used to identify which are which.
 * See {@link HolidayHistoryRequest} for more details.
 */
@PublicSPI
@BeanDefinition
public class HolidayHistoryResult extends AbstractHistoryResult<HolidayDocument> {

  /**
   * Creates an instance.
   */
  public HolidayHistoryResult() {
  }

  /**
   * Creates an instance from a collection of documents.
   * 
   * @param coll  the collection of documents to add, not null
   */
  public HolidayHistoryResult(Collection<HolidayDocument> coll) {
    super(coll);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the returned holidays from within the documents.
   * 
   * @return the holidays, not null
   */
  public List<ManageableHoliday> getHolidays() {
    List<ManageableHoliday> result = new ArrayList<ManageableHoliday>();
    if (getDocuments() != null) {
      for (HolidayDocument doc : getDocuments()) {
        result.add(doc.getHoliday());
      }
    }
    return result;
  }

  /**
   * Gets the first holiday, or null if no documents.
   * 
   * @return the first holiday, null if none
   */
  public ManageableHoliday getFirstHoliday() {
    return getDocuments().size() > 0 ? getDocuments().get(0).getHoliday() : null;
  }

  /**
   * Gets the single result expected from a query.
   * <p>
   * This throws an exception if more than 1 result is actually available.
   * Thus, this method implies an assumption about uniqueness of the queried holiday.
   * 
   * @return the matching holiday, not null
   * @throws IllegalStateException if no holiday was found
   */
  public ManageableHoliday getSingleHoliday() {
    if (getDocuments().size() != 1) {
      throw new OpenGammaRuntimeException("Expecting zero or single resulting match, and was " + getDocuments().size());
    } else {
      return getDocuments().get(0).getHoliday();
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HolidayHistoryResult}.
   * @return the meta-bean, not null
   */
  public static HolidayHistoryResult.Meta meta() {
    return HolidayHistoryResult.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(HolidayHistoryResult.Meta.INSTANCE);
  }

  @Override
  public HolidayHistoryResult.Meta metaBean() {
    return HolidayHistoryResult.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    super.propertySet(propertyName, newValue, quiet);
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
   * The meta-bean for {@code HolidayHistoryResult}.
   */
  public static class Meta extends AbstractHistoryResult.Meta<HolidayDocument> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends HolidayHistoryResult> builder() {
      return new DirectBeanBuilder<HolidayHistoryResult>(new HolidayHistoryResult());
    }

    @Override
    public Class<? extends HolidayHistoryResult> beanType() {
      return HolidayHistoryResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
