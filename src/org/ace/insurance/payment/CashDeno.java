package org.ace.insurance.payment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CASHDENO)
@TableGenerator(name = "CASHDENO_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CASHDENO_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class CashDeno implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CASHDENO_GEN")
	private String id;

	private String deno_eno;
	private String tlf_eno;
	private String acType;
	private String fromType;
	private String branchCode;
	private String receiptNo;
	private double amount;
	private double adjustAmt;
	@Temporal(TemporalType.DATE)
	private Date cash_date;
	private String deno_detail;
	private String denoRefund_detail;
	private String userNo;
	private String counterNo;
	private String status;
	private boolean reverse;
	private String sourceBR;
	private String cur;
	private String denoRate;
	private String denoRefundRate;
	@Temporal(TemporalType.DATE)
	private Date settlementDate;
	private String allDenoRate;
	private double rate;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public CashDeno() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDeno_eno() {
		return deno_eno;
	}

	public void setDeno_eno(String deno_eno) {
		this.deno_eno = deno_eno;
	}

	public String getTlf_eno() {
		return tlf_eno;
	}

	public void setTlf_eno(String tlf_eno) {
		this.tlf_eno = tlf_eno;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAdjustAmt() {
		return adjustAmt;
	}

	public void setAdjustAmt(double adjustAmt) {
		this.adjustAmt = adjustAmt;
	}

	public Date getCash_date() {
		return cash_date;
	}

	public void setCash_date(Date cash_date) {
		this.cash_date = cash_date;
	}

	public String getDeno_detail() {
		return deno_detail;
	}

	public void setDeno_detail(String deno_detail) {
		this.deno_detail = deno_detail;
	}

	public String getDenoRefund_detail() {
		return denoRefund_detail;
	}

	public void setDenoRefund_detail(String denoRefund_detail) {
		this.denoRefund_detail = denoRefund_detail;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCounterNo() {
		return counterNo;
	}

	public void setCounterNo(String counterNo) {
		this.counterNo = counterNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public String getSourceBR() {
		return sourceBR;
	}

	public void setSourceBR(String sourceBR) {
		this.sourceBR = sourceBR;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getDenoRate() {
		return denoRate;
	}

	public void setDenoRate(String denoRate) {
		this.denoRate = denoRate;
	}

	public String getDenoRefundRate() {
		return denoRefundRate;
	}

	public void setDenoRefundRate(String denoRefundRate) {
		this.denoRefundRate = denoRefundRate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getAllDenoRate() {
		return allDenoRate;
	}

	public void setAllDenoRate(String allDenoRate) {
		this.allDenoRate = allDenoRate;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acType == null) ? 0 : acType.hashCode());
		long temp;
		temp = Double.doubleToLongBits(adjustAmt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((allDenoRate == null) ? 0 : allDenoRate.hashCode());
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
		result = prime * result + ((cash_date == null) ? 0 : cash_date.hashCode());
		result = prime * result + ((counterNo == null) ? 0 : counterNo.hashCode());
		result = prime * result + ((cur == null) ? 0 : cur.hashCode());
		result = prime * result + ((denoRate == null) ? 0 : denoRate.hashCode());
		result = prime * result + ((denoRefundRate == null) ? 0 : denoRefundRate.hashCode());
		result = prime * result + ((denoRefund_detail == null) ? 0 : denoRefund_detail.hashCode());
		result = prime * result + ((deno_detail == null) ? 0 : deno_detail.hashCode());
		result = prime * result + ((deno_eno == null) ? 0 : deno_eno.hashCode());
		result = prime * result + ((fromType == null) ? 0 : fromType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
		result = prime * result + (reverse ? 1231 : 1237);
		result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
		result = prime * result + ((sourceBR == null) ? 0 : sourceBR.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tlf_eno == null) ? 0 : tlf_eno.hashCode());
		result = prime * result + ((userNo == null) ? 0 : userNo.hashCode());
		result = prime * result + version;
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
		CashDeno other = (CashDeno) obj;
		if (acType == null) {
			if (other.acType != null)
				return false;
		} else if (!acType.equals(other.acType))
			return false;
		if (Double.doubleToLongBits(adjustAmt) != Double.doubleToLongBits(other.adjustAmt))
			return false;
		if (allDenoRate == null) {
			if (other.allDenoRate != null)
				return false;
		} else if (!allDenoRate.equals(other.allDenoRate))
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (branchCode == null) {
			if (other.branchCode != null)
				return false;
		} else if (!branchCode.equals(other.branchCode))
			return false;
		if (cash_date == null) {
			if (other.cash_date != null)
				return false;
		} else if (!cash_date.equals(other.cash_date))
			return false;
		if (counterNo == null) {
			if (other.counterNo != null)
				return false;
		} else if (!counterNo.equals(other.counterNo))
			return false;
		if (cur == null) {
			if (other.cur != null)
				return false;
		} else if (!cur.equals(other.cur))
			return false;
		if (denoRate == null) {
			if (other.denoRate != null)
				return false;
		} else if (!denoRate.equals(other.denoRate))
			return false;
		if (denoRefundRate == null) {
			if (other.denoRefundRate != null)
				return false;
		} else if (!denoRefundRate.equals(other.denoRefundRate))
			return false;
		if (denoRefund_detail == null) {
			if (other.denoRefund_detail != null)
				return false;
		} else if (!denoRefund_detail.equals(other.denoRefund_detail))
			return false;
		if (deno_detail == null) {
			if (other.deno_detail != null)
				return false;
		} else if (!deno_detail.equals(other.deno_detail))
			return false;
		if (deno_eno == null) {
			if (other.deno_eno != null)
				return false;
		} else if (!deno_eno.equals(other.deno_eno))
			return false;
		if (fromType == null) {
			if (other.fromType != null)
				return false;
		} else if (!fromType.equals(other.fromType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (receiptNo == null) {
			if (other.receiptNo != null)
				return false;
		} else if (!receiptNo.equals(other.receiptNo))
			return false;
		if (reverse != other.reverse)
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		if (sourceBR == null) {
			if (other.sourceBR != null)
				return false;
		} else if (!sourceBR.equals(other.sourceBR))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tlf_eno == null) {
			if (other.tlf_eno != null)
				return false;
		} else if (!tlf_eno.equals(other.tlf_eno))
			return false;
		if (userNo == null) {
			if (other.userNo != null)
				return false;
		} else if (!userNo.equals(other.userNo))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
