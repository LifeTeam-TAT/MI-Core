package org.ace.insurance.life.lifePolicySummary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEPOLICYSUMMARY)
@TableGenerator(name = "LIFEPOLICYSUMMARY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICYSUMMARY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifePolicySummary.findAll", query = "SELECT l FROM LifePolicySummary l "),
		@NamedQuery(name = "LifePolicySummary.findByPolicyNo", query = "SELECT l FROM LifePolicySummary l WHERE l.policyNo = :policyNo ") })
@EntityListeners(IDInterceptor.class)

public class LifePolicySummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICYSUMMARY_GEN")
	private String id;

	@Column(name = "POLICYNO")
	private String policyNo;

	@Column(name = "PAIDPREMIUM")
	private double paidPremium;

	@Column(name = "OUTSTANDINGPREMIUM")
	private double outStandingPremium;

	@Column(name = "LOAN")
	private double loan;

	@Column(name = "INTEREST")
	private double interest;

	@Column(name = "REFUND")
	private double refund;

	@Column(name = "PAIDUPVALUE")
	private double paidUpValue;

	@Column(name = "COVERTIME")
	private int coverTime;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifePolicySummary() {

	}

	public LifePolicySummary(String policyNo, double loan, double interest, double refund, double paidUpValue, int coverTime) {
		this.policyNo = policyNo;
		this.loan = loan;
		this.interest = interest;
		this.refund = refund;
		this.paidUpValue = paidUpValue;
		this.coverTime = coverTime;
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

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public double getPaidPremium() {
		return paidPremium;
	}

	public void setPaidPremium(double paidPremium) {
		this.paidPremium = paidPremium;
	}

	public double getOutStandingPremium() {
		return outStandingPremium;
	}

	public void setOutStandingPremium(double outStandingPremium) {
		this.outStandingPremium = outStandingPremium;
	}

	public double getLoan() {
		return loan;
	}

	public void setLoan(double loan) {
		this.loan = loan;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public double getPaidUpValue() {
		return paidUpValue;
	}

	public void setPaidUpValue(double paidUpValue) {
		this.paidUpValue = paidUpValue;
	}

	public int getCoverTime() {
		return coverTime;
	}

	public void setCoverTime(int coverTime) {
		this.coverTime = coverTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coverTime;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(interest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(loan);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(outStandingPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(paidPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(paidUpValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		temp = Double.doubleToLongBits(refund);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		LifePolicySummary other = (LifePolicySummary) obj;
		if (coverTime != other.coverTime)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(interest) != Double.doubleToLongBits(other.interest))
			return false;
		if (Double.doubleToLongBits(loan) != Double.doubleToLongBits(other.loan))
			return false;
		if (Double.doubleToLongBits(outStandingPremium) != Double.doubleToLongBits(other.outStandingPremium))
			return false;
		if (Double.doubleToLongBits(paidPremium) != Double.doubleToLongBits(other.paidPremium))
			return false;
		if (Double.doubleToLongBits(paidUpValue) != Double.doubleToLongBits(other.paidUpValue))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(refund) != Double.doubleToLongBits(other.refund))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
