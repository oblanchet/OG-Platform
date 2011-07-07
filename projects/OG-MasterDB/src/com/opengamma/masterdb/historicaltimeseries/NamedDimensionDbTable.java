/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb.historicaltimeseries;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.db.DbHelper;
import com.opengamma.util.db.DbMapSqlParameterSource;
import com.opengamma.util.db.DbSource;

/**
 * A dimension table within a star schema.
 * <p>
 * This class aims to simplify working with a simple dimension table.
 * This kind of table consists of simple deduplicated data, keyed by id.
 * The id is used to reference the data on the main "fact" table.
 * <p>
 * This class uses SQL via JDBC. The SQL may be changed by subclassing the relevant methods.
 */
public class NamedDimensionDbTable {

  /** Logger. */
  private static final Logger s_logger = LoggerFactory.getLogger(NamedDimensionDbTable.class);

  /**
   * The database source.
   */
  private final DbSource _dbSource;
  /**
   * The variable name.
   */
  private final String _variableName;
  /**
   * The table name.
   */
  private final String _tableName;
  /**
   * The sequence used to generate the id.
   */
  private final String _sequenceName;

  /**
   * Creates an instance.
   * 
   * @param dbSource  the database source combining all configuration, not null
   * @param variableName  the variable name, used as a placeholder in SQL, not null
   * @param tableName  the table name, not null
   * @param sequenceName  the sequence used to generate the id, may be null
   */
  public NamedDimensionDbTable(final DbSource dbSource, final String variableName, final String tableName, final String sequenceName) {
    ArgumentChecker.notNull(dbSource, "dbSource");
    ArgumentChecker.notNull(variableName, "variableName");
    ArgumentChecker.notNull(tableName, "tableName");
    _dbSource = dbSource;
    _variableName = variableName;
    _tableName = tableName;
    _sequenceName = sequenceName;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the database source.
   * 
   * @return the database source, not null
   */
  protected DbSource getDbSource() {
    return _dbSource;
  }

  /**
   * Gets the variable name.
   * 
   * @return the variable name, not null
   */
  protected String getVariableName() {
    return _variableName;
  }

  /**
   * Gets the table name.
   * 
   * @return the table name, not null
   */
  protected String getTableName() {
    return _tableName;
  }

  /**
   * Gets the sequence name.
   * 
   * @return the sequence name, may be null
   */
  protected String getSequenceName() {
    return _sequenceName;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the dialect helper.
   * 
   * @return the dialect helper, not null
   */
  protected DbHelper getDbDialect() {
    return _dbSource.getDialect();
  }

  /**
   * Gets the next database id.
   * 
   * @return the next database id
   */
  protected long nextId() {
    return _dbSource.getJdbcTemplate().queryForLong(getDbDialect().sqlNextSequenceValueSelect(_sequenceName));
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the id for the name matching exactly.
   * 
   * @param name  the name to lookup, not null
   * @return the id, null if not stored
   */
  public Long get(final String name) {
    String select = sqlSelectGet();
    DbMapSqlParameterSource args = new DbMapSqlParameterSource()
      .addValue(getVariableName(), name);
    List<Map<String, Object>> result = _dbSource.getJdbcTemplate().queryForList(select, args);
    if (result.size() == 1) {
      return (Long) result.get(0).get("dim_id");
    }
    return null;
  }

  /**
   * Gets an SQL select statement suitable for finding the name.
   * <p>
   * The SQL requires a parameter of name {@link #getVariableName()}.
   * The statement returns a single column of the id.
   * 
   * @return the SQL, not null
   */
  public String sqlSelectGet() {
    return
      "SELECT dim.id AS dim_id " +
      "FROM " + getTableName() + " dim " +
      "WHERE dim.name = :" + getVariableName() + " ";
  }

  //-------------------------------------------------------------------------
  /**
   * Searches for the id for the name matching any case and using wildcards.
   * 
   * @param name  the name to lookup, not null
   * @return the id, null if not stored
   */
  public Long search(final String name) {
    String select = sqlSelectSearch(name);
    DbMapSqlParameterSource args = new DbMapSqlParameterSource()
      .addValue(getVariableName(), getDbDialect().sqlWildcardAdjustValue(name));
    List<Map<String, Object>> result = _dbSource.getJdbcTemplate().queryForList(select, args);
    if (result.isEmpty()) {
      return null;
    }
    return (Long) result.get(0).get("dim_id");
  }

  /**
   * Gets an SQL select statement suitable for finding the name.
   * <p>
   * The SQL requires a parameter of name {@link #getVariableName()}.
   * The statement returns a single column of the id.
   * 
   * @param name  the name to lookup, not null
   * @return the SQL, not null
   */
  public String sqlSelectSearch(final String name) {
    return
      "SELECT dim.id AS dim_id " +
      "FROM " + getTableName() + " dim " +
      "WHERE " + getDbDialect().sqlWildcardQuery("UPPER(dim.name) ", "UPPER(:" + getVariableName() + ")", name);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the id adding it if necessary.
   * 
   * @param name  the name to ensure is present, not null
   * @return the id, null if not stored
   */
  public long ensure(final String name) {
    String select = sqlSelectGet();
    DbMapSqlParameterSource args = new DbMapSqlParameterSource()
      .addValue(getVariableName(), name);
    List<Map<String, Object>> result = _dbSource.getJdbcTemplate().queryForList(select, args);
    if (result.size() == 1) {
      return (Long) result.get(0).get("dim_id");
    }
    final long id = nextId();
    args.addValue("dim_id", id);
    _dbSource.getJdbcTemplate().update(sqlInsert(), args);
    s_logger.debug("Inserted new value into {} : {} = {}", new Object[] {getTableName(), id, name});
    return id;
  }

  /**
   * Gets an SQL insert statement suitable for finding the name.
   * <p>
   * The SQL requires a parameter of name {@link #getVariableName()}.
   * 
   * @return the SQL, not null
   */
  public String sqlInsert() {
    return
      "INSERT INTO " + getTableName() + " (id, name) " +
      "VALUES (:dim_id, :" + getVariableName() + ")";
  }

  //-------------------------------------------------------------------------
  @Override
  public String toString() {
    return "Dimension[" + getTableName() + "]";
  }

}
