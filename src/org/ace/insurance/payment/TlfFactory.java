package org.ace.insurance.payment;

import java.util.Date;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.payment.enums.DoubleEntry;
import org.ace.insurance.payment.enums.TlfCategory;
import org.ace.insurance.system.common.PaymentChannel;
import org.springframework.stereotype.Service;

@Service("TlfFactory")
public class TlfFactory {
	private boolean isRenewal;
	// private double homeAmount;
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

	public TlfFactory() {

	}

	/** with no Agent Commission case */
	private TlfFactory(TlfData data, String branchId, String coaCode, String narration, TlfCategory category) {
		Payment payment = data.getPayment();
		if (category.equals(TlfCategory.PREMIUM_TLF)) {
			this.localAmount = data.getPremium();
		} else if (category.equals(TlfCategory.SERVICE_CHARGES_TLF)) {
			this.localAmount = payment.getServicesCharges();
		} else if (category.equals(TlfCategory.STAMP_TLF)) {
			this.localAmount = payment.getStampFees();
		} else if (category.equals(TlfCategory.ADMINIS_FEE)) {
			this.localAmount = payment.getAdministrationFees();
		} else if (category.equals(TlfCategory.LIFE_CLAIM_TLF)) {
			this.localAmount = payment.getClaimAmount();
		} else if (category.equals(TlfCategory.MEDICAL_FEES_TLF)) {
			this.localAmount = payment.getMedicalFees();
		} else if (category.equals(TlfCategory.RECEIVABLE_SP_TLF)) {
			// TODO ** big issue ** sepreate StempFee with kyat and premium is
			// other cur
			this.localAmount = payment.getTotalAmount();
		}

		if (category.equals(TlfCategory.MEDICAL_FEES_PAYMENT_TLF)) {
			this.localAmount = payment.getMedicalFees();
			this.tlfNo = null;
			this.enoNo = payment.getInvoiceNo();
		} else {
			this.tlfNo = payment.getReceiptNo();
			this.enoNo = payment.getReceiptNo();
		}
		this.rate = payment.getRate();
		this.currencyId = payment.getCurrency() != null ? payment.getCurrency().getId() : "";
		this.referenceNo = payment.getReferenceNo();
		this.referenceType = payment.getReferenceType();
		this.customerId = data.getCustomerId();
		this.branchId = branchId;
		this.coaId = coaCode;
		this.narration = narration;
		this.settlementDate = new Date();
		this.isRenewal = data.isRenewal();
	}

	/** only for Agent Commission case */
	private TlfFactory(TlfData data, AgentCommission agentCommission, String branchId, String coaCode, String narration, TlfCategory category) {
		if (category.equals(TlfCategory.AGENTCOMMISION_TLF)) {
			this.tlfNo = agentCommission.getReceiptNo();
			this.enoNo = agentCommission.getId();
			this.currencyId = agentCommission.getCurrency() != null ? agentCommission.getCurrency().getId() : "";
			this.rate = agentCommission.getRate();
		} else if (TlfCategory.AGCOMMISSION_PAYMENT_TLF.equals(category)) {
			Payment payment = data.getPayment();
			this.tlfNo = null;
			this.enoNo = payment.getInvoiceNo();
			this.currencyId = payment.getCurrency() != null ? payment.getCurrency().getId() : "";
			this.rate = payment.getRate();
		}
		this.localAmount = Utils.getTwoDecimalPoint(agentCommission.getCommission());
		this.referenceNo = agentCommission.getReferenceNo();
		this.referenceType = agentCommission.getReferenceType();
		this.customerId = agentCommission.getAgent().getId();
		this.branchId = branchId;
		this.coaId = coaCode;
		this.narration = narration;
		this.settlementDate = new Date();
		this.isRenewal = data.isRenewal();
	}

	public TlfFactory(String tranTypeId, TlfData data, String branchId, String coaCode, String narration, TlfCategory category) {
		this(data, branchId, coaCode, narration, category);
		this.tranTypeId = tranTypeId;
	}

	/** used from Underwriting Payment */
	public TlfFactory(DoubleEntry doubleEntry, TlfData data, String branchId, String coaCode, String narration, TlfCategory category) {
		this(data, branchId, coaCode, narration, category);
		Payment payment = data.getPayment();
		PaymentChannel paymentChannel = payment.getPaymentChannel();
		/* Credit *****/
		if (DoubleEntry.CREDIT.equals(doubleEntry)) {
			if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getCSCredit();
			} else if (PaymentChannel.CHEQUE.equals(paymentChannel) || PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getTRCredit();
			}
			/* Debit *****/
		} else if (DoubleEntry.DEBIT.equals(doubleEntry)) {
			if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getCSDebit();
			} else if (PaymentChannel.CHEQUE.equals(paymentChannel) || PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getTRDebit();
			}
		}

		if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
			this.chequeNo = payment.getChequeNo();
			this.bankId = payment.getBank() == null ? payment.getAccountBank().getId() : payment.getBank().getId();
		}
	}

	/** used from Underwriting Agent Commission Payable */
	public TlfFactory(String tranTypeId, TlfData data, AgentCommission agentComm, String branchId, String coaCode, String narration, TlfCategory category) {
		this(data, agentComm, branchId, coaCode, narration, category);
		this.tranTypeId = tranTypeId;
	}

	/** used from Agent Commission payment */
	public TlfFactory(DoubleEntry doubleEntry, TlfData data, AgentCommission agentComm, String branchId, String coaCode, String narration, TlfCategory category) {
		this(data, agentComm, branchId, coaCode, narration, category);
		Payment payment = data.getPayment();
		PaymentChannel paymentChannel = payment.getPaymentChannel();
		/* Credit *****/
		if (DoubleEntry.CREDIT.equals(doubleEntry)) {
			if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getCSCredit();
			} else if (PaymentChannel.CHEQUE.equals(paymentChannel) || PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getTRCredit();
			}
			/* Debit *****/
		} else if (DoubleEntry.DEBIT.equals(doubleEntry)) {
			if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getCSDebit();
			} else if (PaymentChannel.CHEQUE.equals(paymentChannel) || PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.tranTypeId = KeyFactorIDConfig.getTRDebit();
			}
		}

		if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
			this.chequeNo = payment.getBankAccountNo();
			this.bankId = payment.getAccountBank().getId();
		}
	}

	public TLF getInstance() {
		TLF tlf = new TLF(localAmount, rate, currencyId, chequeNo, tranTypeId, enoNo, referenceNo, bankId, customerId, branchId, coaId, narration, settlementDate, referenceType,
				isRenewal, tlfNo);
		tlf.setPaid(true);
		tlf.setSettlementDate(new Date());
		return tlf;
	}
}
