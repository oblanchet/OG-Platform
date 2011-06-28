// Automatically created - do not modify
///CLOVER:OFF
// CSOFF: Generated File
package com.opengamma.engine.marketdata.spec;
public class UserMarketDataSnapshotSpecification extends com.opengamma.engine.marketdata.spec.MarketDataSnapshotSpecification implements java.io.Serializable {
  private static final long serialVersionUID = -12166521530l;
  private com.opengamma.id.UniqueIdentifier _userSnapshotId;
  public static final String USER_SNAPSHOT_ID_KEY = "userSnapshotId";
  public UserMarketDataSnapshotSpecification (com.opengamma.id.UniqueIdentifier userSnapshotId) {
    if (userSnapshotId == null) throw new NullPointerException ("'userSnapshotId' cannot be null");
    else {
      _userSnapshotId = userSnapshotId;
    }
  }
  protected UserMarketDataSnapshotSpecification (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeMsg fudgeMsg) {
    super (fudgeContext, fudgeMsg);
    org.fudgemsg.FudgeField fudgeField;
    fudgeField = fudgeMsg.getByName (USER_SNAPSHOT_ID_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a UserMarketDataSnapshotSpecification - field 'userSnapshotId' is not present");
    try {
      _userSnapshotId = com.opengamma.id.UniqueIdentifier.fromFudgeMsg (fudgeContext, fudgeMsg.getFieldValue (org.fudgemsg.FudgeMsg.class, fudgeField));
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a UserMarketDataSnapshotSpecification - field 'userSnapshotId' is not UniqueIdentifier message", e);
    }
  }
  protected UserMarketDataSnapshotSpecification (final UserMarketDataSnapshotSpecification source) {
    super (source);
    if (source == null) throw new NullPointerException ("'source' must not be null");
    if (source._userSnapshotId == null) _userSnapshotId = null;
    else {
      _userSnapshotId = source._userSnapshotId;
    }
  }
  public UserMarketDataSnapshotSpecification clone () {
    return new UserMarketDataSnapshotSpecification (this);
  }
  public org.fudgemsg.FudgeMsg toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext) {
    if (fudgeContext == null) throw new NullPointerException ("fudgeContext must not be null");
    final org.fudgemsg.MutableFudgeMsg msg = fudgeContext.newMessage ();
    toFudgeMsg (fudgeContext, msg);
    return msg;
  }
  public void toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext, final org.fudgemsg.MutableFudgeMsg msg) {
    super.toFudgeMsg (fudgeContext, msg);
    if (_userSnapshotId != null)  {
      final org.fudgemsg.MutableFudgeMsg fudge1 = org.fudgemsg.mapping.FudgeSerializationContext.addClassHeader (fudgeContext.newMessage (), _userSnapshotId.getClass (), com.opengamma.id.UniqueIdentifier.class);
      _userSnapshotId.toFudgeMsg (fudgeContext, fudge1);
      msg.add (USER_SNAPSHOT_ID_KEY, null, fudge1);
    }
  }
  public static UserMarketDataSnapshotSpecification fromFudgeMsg (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeMsg fudgeMsg) {
    final java.util.List<org.fudgemsg.FudgeField> types = fudgeMsg.getAllByOrdinal (0);
    for (org.fudgemsg.FudgeField field : types) {
      final String className = (String)field.getValue ();
      if ("com.opengamma.engine.marketdata.spec.UserMarketDataSnapshotSpecification".equals (className)) break;
      try {
        return (com.opengamma.engine.marketdata.spec.UserMarketDataSnapshotSpecification)Class.forName (className).getDeclaredMethod ("fromFudgeMsg", org.fudgemsg.mapping.FudgeDeserializationContext.class, org.fudgemsg.FudgeMsg.class).invoke (null, fudgeContext, fudgeMsg);
      }
      catch (Throwable t) {
        // no-action
      }
    }
    return new UserMarketDataSnapshotSpecification (fudgeContext, fudgeMsg);
  }
  public com.opengamma.id.UniqueIdentifier getUserSnapshotId () {
    return _userSnapshotId;
  }
  public void setUserSnapshotId (com.opengamma.id.UniqueIdentifier userSnapshotId) {
    if (userSnapshotId == null) throw new NullPointerException ("'userSnapshotId' cannot be null");
    else {
      _userSnapshotId = userSnapshotId;
    }
  }
  public String toString () {
    return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this, org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
///CLOVER:ON
// CSON: Generated File
