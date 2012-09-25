/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.instrument;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.time.calendar.LocalDate;

import com.opengamma.analytics.financial.forex.definition.ForexDefinition;
import com.opengamma.analytics.financial.forex.definition.ForexNonDeliverableForwardDefinition;
import com.opengamma.analytics.financial.forex.definition.ForexSwapDefinition;
import com.opengamma.analytics.financial.instrument.annuity.AnnuityCouponFixedDefinition;
import com.opengamma.analytics.financial.instrument.annuity.AnnuityDefinition;
import com.opengamma.analytics.financial.instrument.bond.BillSecurityDefinition;
import com.opengamma.analytics.financial.instrument.bond.BillTransactionDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondFixedSecurityDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondFixedTransactionDefinition;
import com.opengamma.analytics.financial.instrument.cash.CashDefinition;
import com.opengamma.analytics.financial.instrument.fra.ForwardRateAgreementDefinition;
import com.opengamma.analytics.financial.instrument.payment.CouponFixedDefinition;
import com.opengamma.analytics.financial.instrument.payment.PaymentDefinition;
import com.opengamma.analytics.financial.instrument.payment.PaymentFixedDefinition;
import com.opengamma.analytics.financial.instrument.swap.SwapFixedIborDefinition;
import com.opengamma.analytics.financial.instrument.swap.SwapFixedIborSpreadDefinition;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.CurrencyAmount;

/**
 * Returns all of the known cash-flows of an instrument from a particular date.
 */
public class FixedCashFlowVisitor extends AbstractInstrumentDefinitionVisitor<LocalDate, Map<LocalDate, CurrencyAmount>> {

  @Override
  public Map<LocalDate, CurrencyAmount> visitBondFixedSecurityDefinition(final BondFixedSecurityDefinition bond, final LocalDate fromDate) {
    ArgumentChecker.notNull(bond, "Fixed-coupon bond");
    ArgumentChecker.notNull(fromDate, "date");
    final AnnuityCouponFixedDefinition coupons = bond.getCoupons();
    final AnnuityDefinition<PaymentFixedDefinition> nominal = bond.getNominal();
    final Map<LocalDate, CurrencyAmount> result = getDatesAndPaymentsFromAnnuity(coupons, fromDate);
    result.putAll(getDatesAndPaymentsFromAnnuity(nominal, fromDate));
    return result;
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitBondFixedTransactionDefinition(final BondFixedTransactionDefinition bond, final LocalDate fromDate) {
    ArgumentChecker.notNull(bond, "Fixed-coupon bond");
    ArgumentChecker.notNull(fromDate, "date");
    return visitBondFixedSecurityDefinition(bond.getUnderlyingBond());
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitBillSecurityDefinition(final BillSecurityDefinition bill, final LocalDate fromDate) {
    ArgumentChecker.notNull(bill, "bill");
    ArgumentChecker.notNull(fromDate, "date");
    final LocalDate endDate = bill.getEndDate().toLocalDate();
    if (endDate.isBefore(fromDate)) {
      return Collections.emptyMap();
    }
    return Collections.singletonMap(endDate, CurrencyAmount.of(bill.getCurrency(), bill.getNotional()));
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitBillTransactionDefinition(final BillTransactionDefinition bill, final LocalDate fromDate) {
    ArgumentChecker.notNull(bill, "bill");
    ArgumentChecker.notNull(fromDate, "date");
    return visitBillSecurityDefinition(bill.getUnderlying());
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitCashDefinition(final CashDefinition cash, final LocalDate fromDate) {
    ArgumentChecker.notNull(cash, "cash");
    ArgumentChecker.notNull(fromDate, "date");
    final LocalDate endDate = cash.getEndDate().toLocalDate();
    if (endDate.isBefore(fromDate)) {
      return Collections.emptyMap();
    }
    return Collections.singletonMap(endDate, CurrencyAmount.of(cash.getCurrency(), cash.getInterestAmount()));
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitPaymentFixedDefinition(final PaymentFixedDefinition payment, final LocalDate fromDate) {
    ArgumentChecker.notNull(payment, "payment");
    ArgumentChecker.notNull(fromDate, "date");
    final LocalDate endDate = payment.getPaymentDate().toLocalDate();
    if (endDate.isBefore(fromDate)) {
      return Collections.emptyMap();
    }
    return Collections.singletonMap(endDate, CurrencyAmount.of(payment.getCurrency(), payment.getReferenceAmount()));
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitCouponFixedDefinition(final CouponFixedDefinition coupon, final LocalDate fromDate) {
    ArgumentChecker.notNull(coupon, "payment");
    ArgumentChecker.notNull(fromDate, "date");
    final LocalDate endDate = coupon.getPaymentDate().toLocalDate();
    if (endDate.isBefore(fromDate)) {
      return Collections.emptyMap();
    }
    return Collections.singletonMap(endDate, CurrencyAmount.of(coupon.getCurrency(), coupon.getReferenceAmount()));
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitForwardRateAgreementDefinition(final ForwardRateAgreementDefinition forwardRateAgreement, final LocalDate fromDate) {
    ArgumentChecker.notNull(forwardRateAgreement, "payment");
    ArgumentChecker.notNull(fromDate, "date");
    final LocalDate endDate = forwardRateAgreement.getPaymentDate().toLocalDate();
    if (endDate.isBefore(fromDate)) {
      return Collections.emptyMap();
    }
    final double payment = forwardRateAgreement.getReferenceAmount() * forwardRateAgreement.getRate() * forwardRateAgreement.getFixingPeriodAccrualFactor();
    return Collections.singletonMap(endDate, CurrencyAmount.of(forwardRateAgreement.getCurrency(), payment));
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitAnnuityDefinition(final AnnuityDefinition<? extends PaymentDefinition> annuity, final LocalDate fromDate) {
    ArgumentChecker.notNull(annuity, "annuity");
    ArgumentChecker.notNull(fromDate, "date");
    return getDatesAndPaymentsFromAnnuity(annuity, fromDate);
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitSwapFixedIborDefinition(final SwapFixedIborDefinition swap, final LocalDate fromDate) {
    ArgumentChecker.notNull(swap, "swap");
    ArgumentChecker.notNull(fromDate, "date");
    return getDatesAndPaymentsFromAnnuity(swap.getFixedLeg(), fromDate);
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitSwapFixedIborSpreadDefinition(final SwapFixedIborSpreadDefinition swap, final LocalDate fromDate) {
    ArgumentChecker.notNull(swap, "swap");
    ArgumentChecker.notNull(fromDate, "date");
    return getDatesAndPaymentsFromAnnuity(swap.getFixedLeg(), fromDate);
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitForexDefinition(final ForexDefinition fx, final LocalDate fromDate) {
    ArgumentChecker.notNull(fx, "fx");
    ArgumentChecker.notNull(fromDate, "date");
    final Map<LocalDate, CurrencyAmount> result = new HashMap<LocalDate, CurrencyAmount>();
    result.putAll(fx.getPaymentCurrency1().accept(this, fromDate));
    result.putAll(fx.getPaymentCurrency2().accept(this, fromDate));
    return result;
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitForexSwapDefinition(final ForexSwapDefinition fxSwap, final LocalDate fromDate) {
    ArgumentChecker.notNull(fxSwap, "FX swap");
    ArgumentChecker.notNull(fromDate, "date");
    final Map<LocalDate, CurrencyAmount> result = new HashMap<LocalDate, CurrencyAmount>();
    result.putAll(fxSwap.getFarLeg().accept(this, fromDate));
    result.putAll(fxSwap.getNearLeg().accept(this, fromDate));
    return result;
  }

  @Override
  public Map<LocalDate, CurrencyAmount> visitForexNonDeliverableForwardDefinition(final ForexNonDeliverableForwardDefinition ndf, final LocalDate fromDate) {
    ArgumentChecker.notNull(ndf, "ndf");
    ArgumentChecker.notNull(fromDate, "date");
    final LocalDate endDate = ndf.getPaymentDate().toLocalDate();
    if (endDate.isAfter(fromDate)) {
      return Collections.emptyMap();
    }
    return Collections.singletonMap(endDate, CurrencyAmount.of(ndf.getCurrency2(), ndf.getNotional()));
  }

  private Map<LocalDate, CurrencyAmount> getDatesAndPaymentsFromAnnuity(final AnnuityDefinition<? extends PaymentDefinition> annuity, final LocalDate fromDate) {
    final Map<LocalDate, CurrencyAmount> result = new HashMap<LocalDate, CurrencyAmount>();
    final FixedPaymentVisitor fixedPaymentVisitor = FixedPaymentVisitor.getInstance();
    for (final PaymentDefinition payment : annuity.getPayments()) {
      if (payment.accept(fixedPaymentVisitor)) {
        final LocalDate paymentDate = payment.getPaymentDate().toLocalDate();
        if (!paymentDate.isBefore(fromDate)) {
          result.putAll(payment.accept(this, fromDate));
        }
      }
    }
    return result;
  }

  private static final class FixedPaymentVisitor extends AbstractInstrumentDefinitionVisitor<Object, Boolean> {
    static final FixedPaymentVisitor s_instance = new FixedPaymentVisitor();

    static FixedPaymentVisitor getInstance() {
      return s_instance;
    }

    private FixedPaymentVisitor() {
    }

    @Override
    public Boolean visit(final InstrumentDefinition<?> definition) {
      return false;
    }

    @Override
    public Boolean visitPaymentFixedDefinition(final PaymentFixedDefinition payment) {
      return true;
    }

    @Override
    public Boolean visitCouponFixedDefinition(final CouponFixedDefinition coupon) {
      return true;
    }
  }
}
