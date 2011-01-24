/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaProperty;

import com.opengamma.master.AbstractHistoryResult;

/**
 * Result providing the history of a portfolio tree.
 * <p>
 * The returned documents may be a mixture of versions and corrections.
 * The document instant fields are used to identify which are which.
 * See {@link PortfolioHistoryRequest} for more details.
 */
@BeanDefinition
public class PortfolioHistoryResult extends AbstractHistoryResult<PortfolioDocument> {

  /**
   * Creates an instance.
   */
  public PortfolioHistoryResult() {
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the returned portfolios from within the documents.
   * 
   * @return the portfolios, not null
   */
  public List<ManageablePortfolio> getPortfolios() {
    List<ManageablePortfolio> result = new ArrayList<ManageablePortfolio>();
    if (getDocuments() != null) {
      for (PortfolioDocument doc : getDocuments()) {
        result.add(doc.getPortfolio());
      }
    }
    return result;
  }

  /**
   * Gets the first portfolio, or null if no documents.
   * 
   * @return the first portfolio, null if none
   */
  public ManageablePortfolio getFirstPortfolio() {
    return getDocuments().size() > 0 ? getDocuments().get(0).getPortfolio() : null;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code PortfolioHistoryResult}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static PortfolioHistoryResult.Meta meta() {
    return PortfolioHistoryResult.Meta.INSTANCE;
  }

  @Override
  public PortfolioHistoryResult.Meta metaBean() {
    return PortfolioHistoryResult.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    switch (propertyName.hashCode()) {
    }
    return super.propertyGet(propertyName);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue) {
    switch (propertyName.hashCode()) {
    }
    super.propertySet(propertyName, newValue);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code PortfolioHistoryResult}.
   */
  public static class Meta extends AbstractHistoryResult.Meta<PortfolioDocument> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map;

    @SuppressWarnings({"unchecked", "rawtypes" })
    protected Meta() {
      LinkedHashMap temp = new LinkedHashMap(super.metaPropertyMap());
      _map = Collections.unmodifiableMap(temp);
    }

    @Override
    public PortfolioHistoryResult createBean() {
      return new PortfolioHistoryResult();
    }

    @Override
    public Class<? extends PortfolioHistoryResult> beanType() {
      return PortfolioHistoryResult.class;
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
