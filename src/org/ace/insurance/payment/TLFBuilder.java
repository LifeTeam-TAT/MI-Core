package org.ace.insurance.payment;

import java.util.Date;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.payment.enums.DoubleEntry;
import org.ace.insurance.system.common.PaymentChannel;
import org.springframework.stereotype.Service;

@Service("TLFBuilder")
public class TLFBuilder {

	private boolean isRenewal;
	private double homeAmount;
	private double localAmount;
	private double rate;
	private String currencyId;
	private String chequeNo;
	private String tranTypeId;
	private String enoNo;
	private String referenceNo;
	private String bankId;
	private String customerId;
	private String branchId;
	private String coaId;
	private String narration;
	private String tlfNo;
	private Date settlementDate;
	private PolicyReferenceType referenceType;

	public TLFBuilder() {
	}

	private TLFBuilder(Payment payment, boolean isRenewal) {
		this.enoNo = payment.getReceiptNo();
		this.referenceNo = payment.getReferenceNo();
		this.referenceType = payment.getReferenceType();
		this.isRenewal = isRenewal;
	}

	private TLFBuilder(DoubleEntry doubleEntry, PaymentChannel paymentChannel, String chequeNo, String bankId, boolean isRenewal) {
		this.isRenewal = isRenewal;

		// credit
		if (DoubleEntry.CREDIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
				this.chequeNo = chequeNo;
				this.bankId = bankId;

				this.tranTypeId = KeyFactorIDConfig.getTRCredit();
			} else if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getCSCredit();
			} else if (PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getTRCredit();
			}

			// debit
		} else if (DoubleEntry.DEBIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
				this.chequeNo = chequeNo;
				this.bankId = bankId;

				this.tranTypeId = KeyFactorIDConfig.getTRDebit();
			} else if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getCSDebit();
			} else if (PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getTRDebit();
			}
		}
	}

	private TLFBuilder(DoubleEntry doubleEntry, Payment payment, boolean isRenewal) {
		this.enoNo = payment.getReceiptNo();
		this.referenceNo = payment.getReferenceNo();
		this.referenceType = payment.getReferenceType();
		this.isRenewal = isRenewal;

		// credit
		if (DoubleEntry.CREDIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
				this.chequeNo = payment.getChequeNo();
				this.bankId = payment.getBank().getId();

				this.tranTypeId = KeyFactorIDConfig.getTRCredit();
			} else if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
				this.tranTypeId = KeyFactorIDConfig.getCSCredit();
			} else if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
				this.tranTypeId = KeyFactorIDConfig.getTRCredit();
			}

			// debit
		} else if (DoubleEntry.DEBIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
				chequeNo = payment.getChequeNo();
				bankId = payment.getBank().getId();

				this.tranTypeId = KeyFactorIDConfig.getTRDebit();
			} else if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
				this.tranTypeId = KeyFactorIDConfig.getCSDebit();
			} else if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
				this.tranTypeId = KeyFactorIDConfig.getTRDebit();
			}
		}
	}

	/* With Double Entry, Payment (not used) */
	public TLFBuilder(DoubleEntry doubleEntry, double homeAmount, String customerId, String branchId, String coaId, String tlfNo, String narration, Payment payment,
			boolean isRenewal) {
		this(doubleEntry, payment, isRenewal);
		this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.customerId = customerId;
		this.branchId = branchId;
		this.coaId = coaId;
		this.narration = narration;
		this.isRenewal = isRenewal;
		this.tlfNo = tlfNo;

		this.currencyId = payment.getCurrency() == null ? "" : payment.getCurrency().getId();
		this.rate = payment.getRate();
		this.settlementDate = new Date();
		if (rate > 1) {
			this.homeAmount = homeAmount * payment.getRate();
		}
	}

	/* For Claim Outstanding (not used) */
	public TLFBuilder(DoubleEntry doubleEntry, double homeAmount, double localAmount, String currencyId, String enoNo, String referenceNo, String customerId, String branchId,
			String coaId, String narration, Date settlementDate, PolicyReferenceType referenceType) {
		this.homeAmount = homeAmount;
		this.localAmount = localAmount;
		this.currencyId = currencyId;
		this.enoNo = enoNo;
		this.referenceNo = referenceNo;
		this.customerId = customerId;
		this.branchId = branchId;
		this.coaId = coaId;
		this.narration = narration;
		this.settlementDate = settlementDate;
		this.referenceType = referenceType;
		if (DoubleEntry.CREDIT.equals(doubleEntry)) {
			this.tranTypeId = KeyFactorIDConfig.getCSCredit();
		} else {
			this.tranTypeId = KeyFactorIDConfig.getCSDebit();
		}
	}

	/* With Double Entry, PaymentChannel (not used) */
	public TLFBuilder(DoubleEntry doubleEntry, PaymentChannel channel, double homeAmount, String customerId, String branchId, String coaId, String chequeNo, String bankId,
			String tlfNo, String narration, String enoNo, String referenceNo, PolicyReferenceType refType, boolean isRenewal) {
		this(doubleEntry, channel, chequeNo, bankId, isRenewal);
		this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.customerId = customerId;
		this.branchId = branchId;
		this.tlfNo = tlfNo;
		this.narration = narration;
		this.coaId = coaId;
		this.enoNo = enoNo;
		this.referenceNo = referenceNo;
		this.referenceType = refType;
		this.isRenewal = isRenewal;
		this.currencyId = "";
		this.rate = 1.0;
		this.settlementDate = new Date();
	}

	/* With TranCode and Status, Payment (not used) */
	// public TLFBuilder(String tranTypeId, double homeAmount, String
	// customerId, String branchId, String coaId, String tlfNo, String
	// narration, Payment payment, boolean isRenewal) {
	// this(payment, isRenewal);
	// this.tranTypeId = tranTypeId;
	// this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
	// this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
	// this.customerId = customerId;
	// this.branchId = branchId;
	// this.coaId = coaId;
	// this.narration = narration;
	// this.isRenewal = isRenewal;
	// this.tlfNo = tlfNo;
	// this.currencyId = "";
	// this.rate = 1.0;
	// this.settlementDate = new Date();
	// }

	public TLF getTLFInstance() {
		TLF tlf = new TLF(localAmount, rate, currencyId, chequeNo, tranTypeId, enoNo, referenceNo, bankId, customerId, branchId, coaId, narration, settlementDate, referenceType,
				isRenewal, tlfNo);
		tlf.setPaid(false);
		tlf.setClearing(false);
		return tlf;
	}

}
