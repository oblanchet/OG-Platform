/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.fudgemsg;

import org.fudgemsg.FudgeMsg;
import org.fudgemsg.MutableFudgeMsg;
import org.fudgemsg.mapping.FudgeBuilder;
import org.fudgemsg.mapping.FudgeBuilderFor;
import org.fudgemsg.mapping.FudgeDeserializer;
import org.fudgemsg.mapping.FudgeSerializer;

import com.opengamma.master.security.RawSecurity;
import com.opengamma.util.fudgemsg.AbstractFudgeBuilder;

/**
 * A Fudge builder for {@code RawSecurity}.
 */
@FudgeBuilderFor(RawSecurity.class)
public class RawSecurityBuilder extends AbstractFudgeBuilder implements FudgeBuilder<RawSecurity> {

  /** Field name. */
  public static final String RAW_DATA_KEY = "rawData";

  @Override
  public MutableFudgeMsg buildMessage(FudgeSerializer serializer, RawSecurity object) {
    final MutableFudgeMsg msg = serializer.newMessage();
    RawSecurityBuilder.toFudgeMsg(serializer, object, msg);
    return msg;
  }

  public static void toFudgeMsg(FudgeSerializer serializer, RawSecurity object, final MutableFudgeMsg msg) {
    ManageableSecurityBuilder.toFudgeMsg(serializer, object, msg);
    addToMessage(msg, RAW_DATA_KEY, object.getRawData());
  }

  @Override
  public RawSecurity buildObject(FudgeDeserializer deserializer, FudgeMsg msg) {
    RawSecurity object = new RawSecurity("", new byte[0]);
    RawSecurityBuilder.fromFudgeMsg(deserializer, msg, object);
    return object;
  }

  public static void fromFudgeMsg(FudgeDeserializer deserializer, FudgeMsg msg, RawSecurity object) {
    ManageableSecurityBuilder.fromFudgeMsg(deserializer, msg, object);
    object.setRawData(msg.getValue(byte[].class, RAW_DATA_KEY));
  }

}
