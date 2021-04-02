package org.ace.insurance.web.manage.life.billcollection;

import java.util.Date;

import org.ace.insurance.common.IDateSorter;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.system.common.paymenttype.PaymentType;

public class BC0001 implements IDateSorter {
	private static final long serialVersionUID = 1L;
	private String policyId;
	private String policyNo;
	private String receiptNo;
	private String invoiceNo;
	private int fromTerm;
	private int toTerm;
	private String referenceNo;
	private PaymentType paymentType;
	private Date confirmDate;
	private Date coverageDate;
	private double basicTermPremium;
	private PolicyReferenceType referenceType;
	private boolean lastbillcollection;

	/* BillCollection Enquire */
	public BC0001(String policyId, String policyNo, PaymentType paymentType, double basicTermPremium, Date coverageDate, Date confirmDate, String receiptNo, String invoiceNo,
			int fromTerm, int toTerm, String referenceNo, PolicyReferenceType referenceType) {
		this.policyId = policyId;
		this.policyNo = policyNo;
		this.paymentType = paymentType;
		this.basicTermPremium = basicTermPremium;
		this.coverageDate = coverageDate;
		this.confirmDate = confirmDate;
		this.receiptNo = receiptNo;
		this.invoiceNo = invoiceNo;
		this.fromTerm = fromTerm;
		this.toTerm = toTerm;
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
	}

	public BC0001() {

	}

	public String getPaymentTermStrings() {
		StringBuffer buffer = new StringBuffer();
		for (int i = fromTerm; i <= toTerm; i++) {
			buffer.append(i);
			if (i < toTerm) {
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}

	public String getPolicyId() {
		return policyId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public int getFromTerm() {
		return fromTerm;
	}

	public int getToTerm() {
		return toTerm;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public Date getCoverageDate() {
		return coverageDate;
	}

	public double getBasicTermPremium() {
		return basicTermPremium;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public boolean isLastbillcollection() {
		return lastbillcollection;
	}

	public void setLastbillcollection(boolean lastbillcollection) {
		this.lastbillcollection = lastbillcollection;
	}

	@Override
	public Date getSortingDate() {
		return confirmDate;
	}
}
