package org.ace.insurance.life.surrender;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.system.common.PaymentChannel;

public class PaymentTrackDTO implements Serializable {
	private static final long serialVersionUID = -1758198996634522646L;

	private String id;
	private String policyNo;
	private Date paymentDate;
	private String receiptNo;
	private String paymentChannel;
	private Number premium;
	private int fromTerm;
	private int toTerm;
	private String paymentType;

	public PaymentTrackDTO() {
	}

	public PaymentTrackDTO(String id, String policyNo, Date paymentDate, String receiptNo, PaymentChannel paymentChannel, Number premium, int fromTerm, int toTerm) {
		this.id = id;
		this.policyNo = policyNo;
		this.paymentDate = paymentDate;
		this.receiptNo = receiptNo;
		this.paymentChannel = paymentChannel.getLabel();
		this.premium = premium;
		this.fromTerm = fromTerm;
		this.toTerm = toTerm;
		this.paymentType = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public Number getPremium() {
		return premium;
	}

	public void setPremium(Number premium) {
		this.premium = premium;
	}

	public int getFromTerm() {
		return fromTerm;
	}

	public void setFromTerm(int fromTerm) {
		this.fromTerm = fromTerm;
	}

	public int getToTerm() {
		return toTerm;
	}

	public void setToTerm(int toTerm) {
		this.toTerm = toTerm;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fromTerm;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paymentChannel == null) ? 0 : paymentChannel.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((premium == null) ? 0 : premium.hashCode());
		result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
		result = prime * result + toTerm;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentTrackDTO other = (PaymentTrackDTO) obj;
		if (fromTerm != other.fromTerm)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paymentChannel == null) {
			if (other.paymentChannel != null)
				return false;
		} else if (!paymentChannel.equals(other.paymentChannel))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (premium == null) {
			if (other.premium != null)
				return false;
		} else if (!premium.equals(other.premium))
			return false;
		if (receiptNo == null) {
			if (other.receiptNo != null)
				return false;
		} else if (!receiptNo.equals(other.receiptNo))
			return false;
		if (toTerm != other.toTerm)
			return false;
		return true;
	}

}
