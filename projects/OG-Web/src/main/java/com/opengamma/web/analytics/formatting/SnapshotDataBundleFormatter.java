/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.analytics.formatting;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opengamma.core.marketdatasnapshot.SnapshotDataBundle;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.id.ExternalId;
import com.opengamma.util.ArgumentChecker;

/* package */ class SnapshotDataBundleFormatter extends AbstractFormatter<SnapshotDataBundle> {

  private static final String DATA = "data";
  private static final String LABELS = "labels";
  private static final String ID = "ID";
  private static final String VALUE = "Value";

  private final DoubleFormatter _doubleFormatter;

  /* package */ SnapshotDataBundleFormatter(final DoubleFormatter doubleFormatter) {
    super(SnapshotDataBundle.class);
    ArgumentChecker.notNull(doubleFormatter, "doubleFormatter");
    _doubleFormatter = doubleFormatter;
    addFormatter(new Formatter<SnapshotDataBundle>(Format.EXPANDED) {
      @Override
      Map<String, Object> format(final SnapshotDataBundle value, final ValueSpecification valueSpec) {
        return formatExpanded(value, valueSpec);
      }
    });
  }

  @Override
  public String formatCell(final SnapshotDataBundle bundle, final ValueSpecification valueSpec) {
    return "Data Bundle (" + bundle.getDataPoints().size() + " points)";
  }

  private Map<String, Object> formatExpanded(final SnapshotDataBundle bundle, final ValueSpecification valueSpec) {
    final Map<ExternalId, Double> dataPoints = bundle.getDataPoints();
    final List<List<String>> results = Lists.newArrayListWithCapacity(dataPoints.size());
    final Map<String, Object> resultsMap = Maps.newHashMap();
    for (final Map.Entry<ExternalId, Double> entry : dataPoints.entrySet()) {
      final String idStr = entry.getKey().toString();
      final String formattedValue = _doubleFormatter.formatCell(entry.getValue(), valueSpec);
      results.add(ImmutableList.of(idStr, formattedValue));
    }
    resultsMap.put(DATA, results);
    resultsMap.put(LABELS, ImmutableList.of(ID, VALUE));
    return resultsMap;
  }

  @Override
  public DataType getDataType() {
    return DataType.LABELLED_MATRIX_1D;
  }
}
