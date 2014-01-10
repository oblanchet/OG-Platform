/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.core.config.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.fudgemsg.FudgeContext;
import org.fudgemsg.FudgeField;
import org.fudgemsg.FudgeMsg;
import org.fudgemsg.MutableFudgeMsg;
import org.fudgemsg.mapping.FudgeDeserializer;
import org.fudgemsg.mapping.FudgeSerializer;
import org.fudgemsg.wire.FudgeMsgReader;
import org.fudgemsg.wire.FudgeMsgWriter;
import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.id.ObjectId;
import com.opengamma.id.ObjectIdentifiable;
import com.opengamma.id.UniqueId;
import com.opengamma.id.UniqueIdentifiable;
import com.opengamma.util.ClassUtils;
import com.opengamma.util.fudgemsg.OpenGammaFudgeContext;

/**
 * An item stored in the config master.
 * 
 * @param <T> the type of the underlying item
 */
@BeanDefinition
public class ConfigItem<T> extends DirectBean implements UniqueIdentifiable, ObjectIdentifiable, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The underlying value.
   */
  @PropertyDefinition(validate = "notNull")
  private T _value;
  /**
   * The unique identifier.
   */
  @PropertyDefinition
  private UniqueId _uniqueId;
  /**
   * The name of the item.
   */
  @PropertyDefinition(validate = "notNull")
  private String _name;
  /**
   * The type of the configuration item.
   */
  @PropertyDefinition(get = "manual")
  private Class<?> _type;

  /**
   * Obtains an item that wraps the underlying object.
   * <p>
   * The name will be extracted if the target object has a {@code getName} method.
   * 
   * @param <T> the type of the item
   * @param object the underlying object, not null
   * @return the item, not null
   */
  public static <T> ConfigItem<T> of(final T object) {
    final ConfigItem<T> item = new ConfigItem<T>(object);
    if (object instanceof Bean) {
      final Bean bean = (Bean) object;
      if (bean.metaBean().metaPropertyExists("name")) {
        item.setName(ObjectUtils.toString(bean.property("name").get(), null));
      }
    } else if (object != null) {
      try {
        item.setName((String) object.getClass().getMethod("getName").invoke(object));
      } catch (final Exception ex) {
        // ignore
      }
    }
    return item;
  }

  /**
   * Obtains an item that wraps the underlying object.
   * 
   * @param <T> the type of the item
   * @param object the underlying object, not null
   * @param name the name of the item, not null
   * @return the item, not null
   */
  public static <T> ConfigItem<T> of(final T object, final String name) {
    final ConfigItem<T> configItem = new ConfigItem<T>(object);
    configItem.setName(name);
    return configItem;
  }

  /**
   * Obtains an item that wraps the underlying object.
   * 
   * @param <T> the type of the item
   * @param object the underlying object, not null
   * @param name the name of the item, not null
   * @param type the type of the item, not null
   * @return the item, not null
   */
  public static <T> ConfigItem<T> of(final T object, final String name, final Class<?> type) {
    final ConfigItem<T> configItem = new ConfigItem<T>(object);
    configItem.setName(name);
    configItem.setType(type);
    return configItem;
  }

  //-------------------------------------------------------------------------
  /**
   * Creates an empty item. This constructor is here for automated bean construction. This item is invalid until the item class gets set.
   */
  protected ConfigItem() {
    _type = null;
  }

  /**
   * Creates an instance.
   * 
   * @param value the underlying value of the configuration item
   */
  protected ConfigItem(final T value) {
    _value = value;
  }

  //-------------------------------------------------------------------------
  @Override
  public ObjectId getObjectId() {
    return _uniqueId.getObjectId();
  }

  /**
   * Gets the type of the config item.
   * 
   * @return the type, null if no value
   */
  public Class<?> getType() {
    return _type == null ? (_value == null ? null : _value.getClass()) : _type;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ConfigItem}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("rawtypes")
  public static ConfigItem.Meta meta() {
    return ConfigItem.Meta.INSTANCE;
  }

  /**
   * The meta-bean for {@code ConfigItem}.
   * @param <R>  the bean's generic type
   * @param cls  the bean's generic type
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static <R> ConfigItem.Meta<R> metaConfigItem(Class<R> cls) {
    return ConfigItem.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ConfigItem.Meta.INSTANCE);
  }

  @SuppressWarnings("unchecked")
  @Override
  public ConfigItem.Meta<T> metaBean() {
    return ConfigItem.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying value.
   * @return the value of the property, not null
   */
  public T getValue() {
    return _value;
  }

  /**
   * Sets the underlying value.
   * @param value  the new value of the property, not null
   */
  public void setValue(T value) {
    JodaBeanUtils.notNull(value, "value");
    this._value = value;
  }

  /**
   * Gets the the {@code value} property.
   * @return the property, not null
   */
  public final Property<T> value() {
    return metaBean().value().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the unique identifier.
   * @return the value of the property
   */
  public UniqueId getUniqueId() {
    return _uniqueId;
  }

  /**
   * Sets the unique identifier.
   * @param uniqueId  the new value of the property
   */
  public void setUniqueId(UniqueId uniqueId) {
    this._uniqueId = uniqueId;
  }

  /**
   * Gets the the {@code uniqueId} property.
   * @return the property, not null
   */
  public final Property<UniqueId> uniqueId() {
    return metaBean().uniqueId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name of the item.
   * @return the value of the property, not null
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the name of the item.
   * @param name  the new value of the property, not null
   */
  public void setName(String name) {
    JodaBeanUtils.notNull(name, "name");
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Sets the type of the configuration item.
   * @param type  the new value of the property
   */
  public void setType(Class<?> type) {
    this._type = type;
  }

  /**
   * Gets the the {@code type} property.
   * @return the property, not null
   */
  public final Property<Class<?>> type() {
    return metaBean().type().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  @SuppressWarnings("unchecked")
  public ConfigItem<T> clone() {
    BeanBuilder<?> builder = metaBean().builder();
    for (MetaProperty<?> mp : metaBean().metaPropertyIterable()) {
      if (mp.style().isBuildable()) {
        Object value = mp.get(this);
        if (value instanceof Bean) {
          value = ((Bean) value).clone();
        }
        builder.set(mp.name(), value);
      }
    }
    return (ConfigItem<T>) builder.build();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ConfigItem<?> other = (ConfigItem<?>) obj;
      return JodaBeanUtils.equal(getValue(), other.getValue()) &&
          JodaBeanUtils.equal(getUniqueId(), other.getUniqueId()) &&
          JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(getType(), other.getType());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getValue());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUniqueId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getType());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("ConfigItem{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("value").append('=').append(JodaBeanUtils.toString(getValue())).append(',').append(' ');
    buf.append("uniqueId").append('=').append(JodaBeanUtils.toString(getUniqueId())).append(',').append(' ');
    buf.append("name").append('=').append(JodaBeanUtils.toString(getName())).append(',').append(' ');
    buf.append("type").append('=').append(JodaBeanUtils.toString(getType())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ConfigItem}.
   */
  public static class Meta<T> extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    @SuppressWarnings("rawtypes")
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code value} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<T> _value = (DirectMetaProperty) DirectMetaProperty.ofReadWrite(
        this, "value", ConfigItem.class, Object.class);
    /**
     * The meta-property for the {@code uniqueId} property.
     */
    private final MetaProperty<UniqueId> _uniqueId = DirectMetaProperty.ofReadWrite(
        this, "uniqueId", ConfigItem.class, UniqueId.class);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", ConfigItem.class, String.class);
    /**
     * The meta-property for the {@code type} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Class<?>> _type = DirectMetaProperty.ofReadWrite(
        this, "type", ConfigItem.class, (Class) Class.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "value",
        "uniqueId",
        "name",
        "type");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return _value;
        case -294460212:  // uniqueId
          return _uniqueId;
        case 3373707:  // name
          return _name;
        case 3575610:  // type
          return _type;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ConfigItem<T>> builder() {
      return new DirectBeanBuilder<ConfigItem<T>>(new ConfigItem<T>());
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public Class<? extends ConfigItem<T>> beanType() {
      return (Class) ConfigItem.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code value} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<T> value() {
      return _value;
    }

    /**
     * The meta-property for the {@code uniqueId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueId> uniqueId() {
      return _uniqueId;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code type} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Class<?>> type() {
      return _type;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return ((ConfigItem<?>) bean).getValue();
        case -294460212:  // uniqueId
          return ((ConfigItem<?>) bean).getUniqueId();
        case 3373707:  // name
          return ((ConfigItem<?>) bean).getName();
        case 3575610:  // type
          return ((ConfigItem<?>) bean).getType();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          ((ConfigItem<T>) bean).setValue((T) newValue);
          return;
        case -294460212:  // uniqueId
          ((ConfigItem<T>) bean).setUniqueId((UniqueId) newValue);
          return;
        case 3373707:  // name
          ((ConfigItem<T>) bean).setName((String) newValue);
          return;
        case 3575610:  // type
          ((ConfigItem<T>) bean).setType((Class<?>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((ConfigItem<?>) bean)._value, "value");
      JodaBeanUtils.notNull(((ConfigItem<?>) bean)._name, "name");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------

  public void toFudgeMsg(final FudgeSerializer serializer, final MutableFudgeMsg msg) {
    serializer.addToMessage(msg, Meta.INSTANCE.type().name(), null, getType().getName());
    serializer.addToMessage(msg, Meta.INSTANCE.name().name(), null, getName());
    serializer.addToMessage(msg, Meta.INSTANCE.uniqueId().name(), null, getUniqueId());
    serializer.addToMessageWithClassHeaders(msg, Meta.INSTANCE.value().name(), null, getValue(), getType());
  }

  @SuppressWarnings("unchecked")
  private void fromFudgeMsgImpl(final FudgeDeserializer deserializer, final FudgeMsg msg) {
    _type = ClassUtils.loadClassRuntime(msg.getString(Meta.INSTANCE.type().name()));
    _name = msg.getString(Meta.INSTANCE.name().name());
    _value = deserializer.fieldValueToObject((Class<T>) _type, msg.getByName(Meta.INSTANCE.value().name()));
    final FudgeField uniqueId = msg.getByName(Meta.INSTANCE.uniqueId().name());
    if (uniqueId != null) {
      _uniqueId = deserializer.fieldValueToObject(UniqueId.class, uniqueId);
    }
  }

  public static ConfigItem<?> fromFudgeMsg(final FudgeDeserializer deserializer, final FudgeMsg msg) {
    final ConfigItem<?> instance = new ConfigItem<Object>();
    instance.fromFudgeMsgImpl(deserializer, msg);
    return instance;
  }

  /**
   * The fields of a configuration item, specifically the value, are not always serializable but can be Fudge encoded. We can use Java serialization by writing out the binary Fudge encoding.
   */
  private void writeObject(final ObjectOutputStream out) throws IOException {
    final FudgeContext context = OpenGammaFudgeContext.getInstance();
    final FudgeMsgWriter writer = context.createMessageWriter((DataOutput) out);
    final MutableFudgeMsg msg = context.newMessage();
    toFudgeMsg(new FudgeSerializer(context), msg);
    writer.writeMessage(msg);
    // Note - don't close the writer as we don't want to close the underlying stream
  }

  /**
   * The fields of a configuration item, specifically the value, are not always serializable but can be decoded from a binary Fudge encoding written by {@link #writeObject}.
   */
  private void readObject(final ObjectInputStream in) throws IOException {
    final FudgeContext context = OpenGammaFudgeContext.getInstance();
    final FudgeMsgReader reader = context.createMessageReader((DataInput) in);
    final FudgeMsg msg = reader.nextMessage();
    // Note - don't close the reader as we don't want to close the underlying stream
    fromFudgeMsgImpl(new FudgeDeserializer(context), msg);
  }

}
