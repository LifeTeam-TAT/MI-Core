package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.hospital.Hospital;

@Entity
@Table(name = TableName.LIFEHOSPITALIZEDCLAIM)
@DiscriminatorValue(value = LifeClaimRole.HOSPITALIZED)
public class LifeHospitalizedClaim extends LifePolicyClaim implements Serializable {
	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private int noOfdays;
	private double hospitalizedAmount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSPITALID", referencedColumnName = "ID")
	private Hospital medicalPlace;
	@Embedded
	private UserRecorder recorder;

	public LifeHospitalizedClaim() {
		super();
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public double getHospitalizedAmount() {
		return hospitalizedAmount;
	}

	public Hospital getMedicalPlace() {
		return medicalPlace;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setHospitalizedAmount(double hospitalizedAmount) {
		this.hospitalizedAmount = hospitalizedAmount;
	}

	public void setMedicalPlace(Hospital medicalPlace) {
		this.medicalPlace = medicalPlace;
	}

	public int getNoOfdays() {
		return noOfdays;
	}

	public void setNoOfdays(int noOfdays) {
		this.noOfdays = noOfdays;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(hospitalizedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((medicalPlace == null) ? 0 : medicalPlace.hashCode());
		result = prime * result + noOfdays;
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		LifeHospitalizedClaim other = (LifeHospitalizedClaim) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (Double.doubleToLongBits(hospitalizedAmount) != Double.doubleToLongBits(other.hospitalizedAmount))
			return false;
		if (medicalPlace == null) {
			if (other.medicalPlace != null)
				return false;
		} else if (!medicalPlace.equals(other.medicalPlace))
			return false;
		if (noOfdays != other.noOfdays)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

}
