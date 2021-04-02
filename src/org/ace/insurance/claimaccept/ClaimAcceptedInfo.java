/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimaccept;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CLAIMACCEPTEDINFO)
@TableGenerator(name = "CLAIMACCEPTEDINFO_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CLAIMACCEPTEDINFO_GEN", allocationSize = 10)
@NamedQueries(value = {
		@NamedQuery(name = "ClaimAcceptedInfo.findByReferenceNo", query = "SELECT a FROM ClaimAcceptedInfo a WHERE a.referenceNo = :referenceNo AND a.referenceType = :referenceType") })
@EntityListeners(IDInterceptor.class)
public class ClaimAcceptedInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CLAIMACCEPTEDINFO_GEN")
	private String id;

	@Column(name = "REFERENCENO")
	private String referenceNo;

	@Column(name = "REFERENCETYPE")
	@Enumerated(value = EnumType.STRING)
	private ReferenceType referenceType;

	@Column(name = "CLAIMAMOUNT")
	private double claimAmount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@Column(name = "SERVICECHARGES")
	private double servicesCharges;

	@Column(name = "REINSTATEMENTPREMIUM")
	private double reinstatementPremium;

	private double salvageValue;
	private String freeNote;

	@Column(name = "INFORMDATE")
	@Temporal(TemporalType.DATE)
	private Date informDate;

	@Embedded
	private UserRecorder recorder;
	
	
	private double  medicalfees;

	@Version
	private int version;

	public ClaimAcceptedInfo() {
		informDate = new Date();
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public double getServicesCharges() {
		return servicesCharges;
	}

	public void setServicesCharges(double servicesCharges) {
		this.servicesCharges = servicesCharges;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getReinstatementPremium() {
		return reinstatementPremium;
	}

	public void setReinstatementPremium(double reinstatementPremium) {
		this.reinstatementPremium = reinstatementPremium;
	}

	public Date getInformDate() {
		return informDate;
	}

	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}

	public double getTotalAmount() {
		return claimAmount - servicesCharges - reinstatementPremium - salvageValue;
	}

	public double getSalvageValue() {
		return salvageValue;
	}

	public void setSalvageValue(double salvageValue) {
		this.salvageValue = salvageValue;
	}

	public String getFreeNote() {
		return freeNote;
	}

	public void setFreeNote(String freeNote) {
		this.freeNote = freeNote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(claimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((freeNote == null) ? 0 : freeNote.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((informDate == null) ? 0 : informDate.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
		result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
		temp = Double.doubleToLongBits(reinstatementPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(salvageValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(servicesCharges);
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
		ClaimAcceptedInfo other = (ClaimAcceptedInfo) obj;
		if (Double.doubleToLongBits(claimAmount) != Double.doubleToLongBits(other.claimAmount))  
			return false;
		if (freeNote == null) {
			if (other.freeNote != null)
				return false;
		} else if (!freeNote.equals(other.freeNote))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (informDate == null) {
			if (other.informDate != null)
				return false;
		} else if (!informDate.equals(other.informDate))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (referenceNo == null) {
			if (other.referenceNo != null)
				return false;
		} else if (!referenceNo.equals(other.referenceNo))
			return false;
		if (referenceType != other.referenceType)
			return false;
		if (Double.doubleToLongBits(reinstatementPremium) != Double.doubleToLongBits(other.reinstatementPremium))
			return false;
		if (Double.doubleToLongBits(salvageValue) != Double.doubleToLongBits(other.salvageValue))
			return false;
		if (Double.doubleToLongBits(servicesCharges) != Double.doubleToLongBits(other.servicesCharges))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public double getMedicalfees() {
		return medicalfees;
	}

	public void setMedicalfees(double medicalfees) {
		this.medicalfees = medicalfees;
	}
	
	
}