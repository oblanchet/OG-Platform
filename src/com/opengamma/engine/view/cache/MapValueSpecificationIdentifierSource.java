/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.util.ArgumentChecker;

/**
 * An implementation of {@link ValueSpecificationIdentifierSource} which is backed by an in-memory
 * {@link ConcurrentMap}. This has no facilities for acting as a cache, or for persistence.
 * It should only be used for development and debugging purposes.
 */
public class MapValueSpecificationIdentifierSource implements ValueSpecificationIdentifierSource {
  private final AtomicLong _nextIdentifier = new AtomicLong(1L);
  private final ConcurrentMap<ValueSpecification, Long> _identifiers =
    new ConcurrentHashMap<ValueSpecification, Long>();

  @Override
  public long getIdentifier(ValueSpecification spec) {
    ArgumentChecker.notNull(spec, "Value specification");
    Long result = _identifiers.get(spec);
    if (result != null) {
      return result;
    }
    Long freshIdentifier = _nextIdentifier.getAndIncrement();
    result = _identifiers.putIfAbsent(spec, freshIdentifier);
    if (result == null) {
      result = freshIdentifier;
    }
    return result;
  }

}
