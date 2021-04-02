package org.ace.insurance.payment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.Utils;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.TLF)
@TableGenerator(name = "TLF_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "TLF_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class TLF implements Serializable {
	private static final long serialVersionUID = -299125992570974772L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TLF_GEN")
	private String id;

	private String customerId;
	private String ccoaId;
	private double homeAmount;
	private double localAmount;
	private String currencyId;
	private String chequeNo;
	private String bankId;
	private String purchaseOrderId;
	private String loanId;
	private String narration;
	private String tranTypeId;
	private String departmentId;
	private String orgnTLFid;
	private String branchId;
	private double rate;

	@Temporal(TemporalType.DATE)
	private Date settlementDate;

	private boolean reverse;

	private String referenceNo;

	@Column(name = "REFERENCETYPE")
	@Enumerated(value = EnumType.STRING)
	private PolicyReferenceType referenceType;

	@Column(name = "ENO")
	private String enoNo;

	@Column(name = "PAID")
	private boolean paid;

	/**
	 * TLF Number (tlfNo) is cash receipt number.
	 */
	@Column(name = "TLFNO")
	private String tlfNo;

	@Column(name = "CLEARING")
	private boolean clearing;

	@Column(name = "ISRENEWAL")
	private boolean isRenewal;

	private boolean payableTran;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public TLF() {

	}

	public TLF(double localAmount, double rate, String currency, String chequeNo, String tranTypeId, String enoNo, String referenceNo, String bankId, String customerId,
			String branchId, String ccoaId, String narration, Date settlementDate, PolicyReferenceType referenceType, boolean isRenewal, String tlfNo) {
		this.localAmount = localAmount;
		this.rate = rate;
		this.homeAmount = Utils.getTwoDecimalPoint(localAmount * rate);
		this.currencyId = currency;
		this.chequeNo = chequeNo;
		this.tranTypeId = tranTypeId;
		this.enoNo = enoNo;
		this.referenceNo = referenceNo;
		this.bankId = bankId;
		this.customerId = customerId;
		this.branchId = branchId;
		this.ccoaId = ccoaId;
		this.narration = narration;
		this.settlementDate = settlementDate;
		this.referenceType = referenceType;
		this.isRenewal = isRenewal;
		this.tlfNo = tlfNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCcoaId() {
		return ccoaId;
	}

	public void setCcoaId(String ccoaId) {
		this.ccoaId = ccoaId;
	}

	public double getHomeAmount() {
		return homeAmount;
	}

	public void setHomeAmount(double homeAmount) {
		this.homeAmount = homeAmount;
	}

	public double getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(double localAmount) {
		this.localAmount = localAmount;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getEnoNo() {
		return enoNo;
	}

	public void setEnoNo(String enoNo) {
		this.enoNo = enoNo;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getOrgnTLFid() {
		return orgnTLFid;
	}

	public void setOrgnTLFid(String orgnTLFid) {
		this.orgnTLFid = orgnTLFid;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getTlfNo() {
		return tlfNo;
	}

	public void setTlfNo(String tlfNo) {
		this.tlfNo = tlfNo;
	}

	public boolean isClearing() {
		return clearing;
	}

	public void setClearing(boolean clearing) {
		this.clearing = clearing;
	}

	public boolean isRenewal() {
		return isRenewal;
	}

	public void setRenewal(boolean isRenewal) {
		this.isRenewal = isRenewal;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public boolean isPayableTran() {
		return payableTran;
	}

	public void setPayableTran(boolean payableTran) {
		this.payableTran = payableTran;
	}

	public String getTranTypeId() {
		return tranTypeId;
	}

	public void setTranTypeId(String tranTypeId) {
		this.tranTypeId = tranTypeId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankId == null) ? 0 : bankId.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((chequeNo == null) ? 0 : chequeNo.hashCode());
		result = prime * result + (clearing ? 1231 : 1237);
		result = prime * result + ((ccoaId == null) ? 0 : ccoaId.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((currencyId == null) ? 0 : currencyId.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((enoNo == null) ? 0 : enoNo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(homeAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isRenewal ? 1231 : 1237);
		result = prime * result + ((loanId == null) ? 0 : loanId.hashCode());
		temp = Double.doubleToLongBits(localAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((narration == null) ? 0 : narration.hashCode());
		result = prime * result + ((orgnTLFid == null) ? 0 : orgnTLFid.hashCode());
		result = prime * result + (paid ? 1231 : 1237);
		result = prime * result + ((purchaseOrderId == null) ? 0 : purchaseOrderId.hashCode());
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
		result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
		result = prime * result + (reverse ? 1231 : 1237);
		result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
		result = prime * result + ((tlfNo == null) ? 0 : tlfNo.hashCode());
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
		TLF other = (TLF) obj;
		if (bankId == null) {
			if (other.bankId != null)
				return false;
		} else if (!bankId.equals(other.bankId))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (chequeNo == null) {
			if (other.chequeNo != null)
				return false;
		} else if (!chequeNo.equals(other.chequeNo))
			return false;
		if (clearing != other.clearing)
			return false;
		if (ccoaId == null) {
			if (other.ccoaId != null)
				return false;
		} else if (!ccoaId.equals(other.ccoaId))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (currencyId == null) {
			if (other.currencyId != null)
				return false;
		} else if (!currencyId.equals(other.currencyId))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (enoNo == null) {
			if (other.enoNo != null)
				return false;
		} else if (!enoNo.equals(other.enoNo))
			return false;
		if (Double.doubleToLongBits(homeAmount) != Double.doubleToLongBits(other.homeAmount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isRenewal != other.isRenewal)
			return false;
		if (loanId == null) {
			if (other.loanId != null)
				return false;
		} else if (!loanId.equals(other.loanId))
			return false;
		if (Double.doubleToLongBits(localAmount) != Double.doubleToLongBits(other.localAmount))
			return false;
		if (narration == null) {
			if (other.narration != null)
				return false;
		} else if (!narration.equals(other.narration))
			return false;
		if (orgnTLFid == null) {
			if (other.orgnTLFid != null)
				return false;
		} else if (!orgnTLFid.equals(other.orgnTLFid))
			return false;
		if (paid != other.paid)
			return false;
		if (purchaseOrderId == null) {
			if (other.purchaseOrderId != null)
				return false;
		} else if (!purchaseOrderId.equals(other.purchaseOrderId))
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (referenceNo == null) {
			if (other.referenceNo != null)
				return false;
		} else if (!referenceNo.equals(other.referenceNo))
			return false;
		if (referenceType != other.referenceType)
			return false;
		if (reverse != other.reverse)
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		if (tlfNo == null) {
			if (other.tlfNo != null)
				return false;
		} else if (!tlfNo.equals(other.tlfNo))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
