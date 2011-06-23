// Automatically created - do not modify
///CLOVER:OFF
// CSOFF: Generated File
package com.opengamma.financial.security.fx;
public class FXSecurity extends com.opengamma.financial.security.FinancialSecurity implements java.io.Serializable {
           public <T> T accept (FXSecurityVisitor<T> visitor) { return visitor.visitFXSecurity(this); }
         public final <T> T accept(com.opengamma.financial.security.FinancialSecurityVisitor<T> visitor) { return visitor.visitFXSecurity(this); }
  private static final long serialVersionUID = -8963298790706894517l;
  private com.opengamma.util.money.Currency _payCurrency;
  public static final String PAY_CURRENCY_KEY = "payCurrency";
  private com.opengamma.util.money.Currency _receiveCurrency;
  public static final String RECEIVE_CURRENCY_KEY = "receiveCurrency";
  private double _payAmount;
  public static final String PAY_AMOUNT_KEY = "payAmount";
  private double _receiveAmount;
  public static final String RECEIVE_AMOUNT_KEY = "receiveAmount";
  private String _tradeCountry;
  public static final String TRADE_COUNTRY_KEY = "tradeCountry";
  public static final String SECURITY_TYPE = "FX";
  public FXSecurity (com.opengamma.util.money.Currency payCurrency, com.opengamma.util.money.Currency receiveCurrency, double payAmount, double receiveAmount, String tradeCountry) {
    super (SECURITY_TYPE);
    if (payCurrency == null) throw new NullPointerException ("payCurrency' cannot be null");
    _payCurrency = payCurrency;
    if (receiveCurrency == null) throw new NullPointerException ("receiveCurrency' cannot be null");
    _receiveCurrency = receiveCurrency;
    _payAmount = payAmount;
    _receiveAmount = receiveAmount;
    if (tradeCountry == null) throw new NullPointerException ("tradeCountry' cannot be null");
    _tradeCountry = tradeCountry;
  }
  protected FXSecurity (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeMsg fudgeMsg) {
    super (fudgeContext, fudgeMsg);
    org.fudgemsg.FudgeField fudgeField;
    fudgeField = fudgeMsg.getByName (PAY_CURRENCY_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'payCurrency' is not present");
    try {
      _payCurrency = fudgeMsg.getFieldValue (com.opengamma.util.money.Currency.class, fudgeField);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'payCurrency' is not Currency typedef", e);
    }
    fudgeField = fudgeMsg.getByName (RECEIVE_CURRENCY_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'receiveCurrency' is not present");
    try {
      _receiveCurrency = fudgeMsg.getFieldValue (com.opengamma.util.money.Currency.class, fudgeField);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'receiveCurrency' is not Currency typedef", e);
    }
    fudgeField = fudgeMsg.getByName (PAY_AMOUNT_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'payAmount' is not present");
    try {
      _payAmount = fudgeMsg.getFieldValue (Double.class, fudgeField);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'payAmount' is not double", e);
    }
    fudgeField = fudgeMsg.getByName (RECEIVE_AMOUNT_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'receiveAmount' is not present");
    try {
      _receiveAmount = fudgeMsg.getFieldValue (Double.class, fudgeField);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'receiveAmount' is not double", e);
    }
    fudgeField = fudgeMsg.getByName (TRADE_COUNTRY_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'tradeCountry' is not present");
    try {
      _tradeCountry = fudgeField.getValue ().toString ();
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a FXSecurity - field 'tradeCountry' is not string", e);
    }
  }
  public FXSecurity (com.opengamma.id.UniqueIdentifier uniqueId, String name, String securityType, com.opengamma.id.IdentifierBundle identifiers, com.opengamma.util.money.Currency payCurrency, com.opengamma.util.money.Currency receiveCurrency, double payAmount, double receiveAmount, String tradeCountry) {
    super (uniqueId, name, securityType, identifiers);
    if (payCurrency == null) throw new NullPointerException ("payCurrency' cannot be null");
    _payCurrency = payCurrency;
    if (receiveCurrency == null) throw new NullPointerException ("receiveCurrency' cannot be null");
    _receiveCurrency = receiveCurrency;
    _payAmount = payAmount;
    _receiveAmount = receiveAmount;
    if (tradeCountry == null) throw new NullPointerException ("tradeCountry' cannot be null");
    _tradeCountry = tradeCountry;
  }
  protected FXSecurity (final FXSecurity source) {
    super (source);
    if (source == null) throw new NullPointerException ("'source' must not be null");
    _payCurrency = source._payCurrency;
    _receiveCurrency = source._receiveCurrency;
    _payAmount = source._payAmount;
    _receiveAmount = source._receiveAmount;
    _tradeCountry = source._tradeCountry;
  }
  public FXSecurity clone () {
    return new FXSecurity (this);
  }
  public org.fudgemsg.FudgeMsg toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext) {
    if (fudgeContext == null) throw new NullPointerException ("fudgeContext must not be null");
    final org.fudgemsg.MutableFudgeMsg msg = fudgeContext.newMessage ();
    toFudgeMsg (fudgeContext, msg);
    return msg;
  }
  public void toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext, final org.fudgemsg.MutableFudgeMsg msg) {
    super.toFudgeMsg (fudgeContext, msg);
    if (_payCurrency != null)  {
      msg.add (PAY_CURRENCY_KEY, null, _payCurrency);
    }
    if (_receiveCurrency != null)  {
      msg.add (RECEIVE_CURRENCY_KEY, null, _receiveCurrency);
    }
    msg.add (PAY_AMOUNT_KEY, null, _payAmount);
    msg.add (RECEIVE_AMOUNT_KEY, null, _receiveAmount);
    if (_tradeCountry != null)  {
      msg.add (TRADE_COUNTRY_KEY, null, _tradeCountry);
    }
  }
  public static FXSecurity fromFudgeMsg (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeMsg fudgeMsg) {
    final java.util.List<org.fudgemsg.FudgeField> types = fudgeMsg.getAllByOrdinal (0);
    for (org.fudgemsg.FudgeField field : types) {
      final String className = (String)field.getValue ();
      if ("com.opengamma.financial.security.fx.FXSecurity".equals (className)) break;
      try {
        return (com.opengamma.financial.security.fx.FXSecurity)Class.forName (className).getDeclaredMethod ("fromFudgeMsg", org.fudgemsg.mapping.FudgeDeserializationContext.class, org.fudgemsg.FudgeMsg.class).invoke (null, fudgeContext, fudgeMsg);
      }
      catch (Throwable t) {
        // no-action
      }
    }
    return new FXSecurity (fudgeContext, fudgeMsg);
  }
  public com.opengamma.util.money.Currency getPayCurrency () {
    return _payCurrency;
  }
  public void setPayCurrency (com.opengamma.util.money.Currency payCurrency) {
    if (payCurrency == null) throw new NullPointerException ("payCurrency' cannot be null");
    _payCurrency = payCurrency;
  }
  public com.opengamma.util.money.Currency getReceiveCurrency () {
    return _receiveCurrency;
  }
  public void setReceiveCurrency (com.opengamma.util.money.Currency receiveCurrency) {
    if (receiveCurrency == null) throw new NullPointerException ("receiveCurrency' cannot be null");
    _receiveCurrency = receiveCurrency;
  }
  public double getPayAmount () {
    return _payAmount;
  }
  public void setPayAmount (double payAmount) {
    _payAmount = payAmount;
  }
  public double getReceiveAmount () {
    return _receiveAmount;
  }
  public void setReceiveAmount (double receiveAmount) {
    _receiveAmount = receiveAmount;
  }
  public String getTradeCountry () {
    return _tradeCountry;
  }
  public void setTradeCountry (String tradeCountry) {
    if (tradeCountry == null) throw new NullPointerException ("tradeCountry' cannot be null");
    _tradeCountry = tradeCountry;
  }
  public boolean equals (final Object o) {
    if (o == this) return true;
    if (!(o instanceof FXSecurity)) return false;
    FXSecurity msg = (FXSecurity)o;
    if (_payCurrency != null) {
      if (msg._payCurrency != null) {
        if (!_payCurrency.equals (msg._payCurrency)) return false;
      }
      else return false;
    }
    else if (msg._payCurrency != null) return false;
    if (_receiveCurrency != null) {
      if (msg._receiveCurrency != null) {
        if (!_receiveCurrency.equals (msg._receiveCurrency)) return false;
      }
      else return false;
    }
    else if (msg._receiveCurrency != null) return false;
    if (_payAmount != msg._payAmount) return false;
    if (_receiveAmount != msg._receiveAmount) return false;
    if (_tradeCountry != null) {
      if (msg._tradeCountry != null) {
        if (!_tradeCountry.equals (msg._tradeCountry)) return false;
      }
      else return false;
    }
    else if (msg._tradeCountry != null) return false;
    return super.equals (msg);
  }
  public int hashCode () {
    int hc = super.hashCode ();
    hc *= 31;
    if (_payCurrency != null) hc += _payCurrency.hashCode ();
    hc *= 31;
    if (_receiveCurrency != null) hc += _receiveCurrency.hashCode ();
    hc = (hc * 31) + (int)_payAmount;
    hc = (hc * 31) + (int)_receiveAmount;
    hc *= 31;
    if (_tradeCountry != null) hc += _tradeCountry.hashCode ();
    return hc;
  }
  public String toString () {
    return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this, org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
///CLOVER:ON
// CSON: Generated File
