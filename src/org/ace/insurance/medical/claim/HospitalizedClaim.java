package org.ace.insurance.medical.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.common.TableName;
import org.ace.insurance.system.common.hospital.Hospital;

@Entity
@Table(name = TableName.HOSPITALIZEDCLAIM)
@DiscriminatorValue(value = MedicalClaimRole.HOSPITALIZED_CLAIM)
@NamedQueries(value = { @NamedQuery(name = "HospitalizedClaim.findAll", query = "SELECT h FROM HospitalizedClaim h "),
		@NamedQuery(name = "HospitalizedClaim.findById", query = "SELECT h FROM HospitalizedClaim h WHERE h.id = :id") })
public class HospitalizedClaim extends MedicalClaim implements Serializable {

	private static final long serialVersionUID = 1331695817152712583L;

	@Column(name = "HOSPITALIZED_STARTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hospitalizedStartDate;

	@Column(name = "HOSPITALIZED_ENDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hospitalizedEndDate;

	@Column(name = "HOSP_CLAIM_AMT")
	private double hospitalizedAmount;

	@Column(name = "ACTUAL_HOSP_CLAIM_AMT")
	private double actualHospitalizedAmount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSP_PLACE_ID", referencedColumnName = "ID")
	private Hospital medicalPlace;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> attachmentList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hospitalizedClaim", orphanRemoval = true)
	private List<HospitalizedClaimICD10> hospitalizedClaimICD10List;

	public HospitalizedClaim() {

	}

	public Date getHospitalizedStartDate() {
		return hospitalizedStartDate;
	}

	public void setHospitalizedStartDate(Date hospitalizedStartDate) {
		this.hospitalizedStartDate = hospitalizedStartDate;
	}

	public Date getHospitalizedEndDate() {
		return hospitalizedEndDate;
	}

	public void setHospitalizedEndDate(Date hospitalizedEndDate) {
		this.hospitalizedEndDate = hospitalizedEndDate;
	}

	public Hospital getMedicalPlace() {
		return medicalPlace;
	}

	public void setMedicalPlace(Hospital medicalPlace) {
		this.medicalPlace = medicalPlace;
	}

	public double getHospitalizedAmount() {
		return hospitalizedAmount;
	}

	public void setHospitalizedAmount(double hospitalizedAmount) {
		this.hospitalizedAmount = hospitalizedAmount;
	}

	public void addAttachment(Attachment attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		attachmentList.add(attachment);
	}

	public List<Attachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public double getActualHospitalizedAmount() {
		return actualHospitalizedAmount;
	}

	public void setActualHospitalizedAmount(double actualHospitalizedAmount) {
		this.actualHospitalizedAmount = actualHospitalizedAmount;
	}

	public List<HospitalizedClaimICD10> getHospitalizedClaimICD10List() {
		if (hospitalizedClaimICD10List == null) {
			hospitalizedClaimICD10List = new ArrayList<HospitalizedClaimICD10>();
		}
		return hospitalizedClaimICD10List;
	}

	public void setHospitalizedClaimICD10List(List<HospitalizedClaimICD10> hospitalizedClaimICD10List) {
		this.hospitalizedClaimICD10List = hospitalizedClaimICD10List;
	}

	public void addHospitalizedClaimICD10(HospitalizedClaimICD10 hospitalizedClaimICD10) {
		if (hospitalizedClaimICD10List == null) {
			hospitalizedClaimICD10List = new ArrayList<HospitalizedClaimICD10>();
		}
		hospitalizedClaimICD10.setHospitalizedClaim(this);
		hospitalizedClaimICD10List.add(hospitalizedClaimICD10);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(actualHospitalizedAmount);
		temp = Double.doubleToLongBits(hospitalizedAmount);
		result = prime * result + ((hospitalizedEndDate == null) ? 0 : hospitalizedEndDate.hashCode());
		result = prime * result + ((hospitalizedStartDate == null) ? 0 : hospitalizedStartDate.hashCode());
		result = prime * result + ((medicalPlace == null) ? 0 : medicalPlace.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HospitalizedClaim other = (HospitalizedClaim) obj;
		if (Double.doubleToLongBits(actualHospitalizedAmount) != Double.doubleToLongBits(other.actualHospitalizedAmount))
			return false;
		if (Double.doubleToLongBits(hospitalizedAmount) != Double.doubleToLongBits(other.hospitalizedAmount))
			return false;
		if (hospitalizedEndDate == null) {
			if (other.hospitalizedEndDate != null)
				return false;
		} else if (!hospitalizedEndDate.equals(other.hospitalizedEndDate))
			return false;
		if (hospitalizedStartDate == null) {
			if (other.hospitalizedStartDate != null)
				return false;
		} else if (!hospitalizedStartDate.equals(other.hospitalizedStartDate))
			return false;
		if (medicalPlace == null) {
			if (other.medicalPlace != null)
				return false;
		} else if (!medicalPlace.equals(other.medicalPlace))
			return false;
		return true;
	}

}
