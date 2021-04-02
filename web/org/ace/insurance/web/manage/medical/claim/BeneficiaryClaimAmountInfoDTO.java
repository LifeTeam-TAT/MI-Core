package org.ace.insurance.web.manage.medical.claim;

public class BeneficiaryClaimAmountInfoDTO {

	private String beneficiaryName;
	private double hospitalizedAmount;
	private double operationAmount;
	private double medicationAmount;
	private double deathAmount;
	private double total;

	public BeneficiaryClaimAmountInfoDTO() {

	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public double getHospitalizedAmount() {
		return hospitalizedAmount;
	}

	public void setHospitalizedAmount(double hospitalizedAmount) {
		this.hospitalizedAmount = hospitalizedAmount;
	}

	public double getOperationAmount() {
		return operationAmount;
	}

	public void setOperationAmount(double operationAmount) {
		this.operationAmount = operationAmount;
	}

	public double getMedicationAmount() {
		return medicationAmount;
	}

	public void setMedicationAmount(double medicationAmount) {
		this.medicationAmount = medicationAmount;
	}

	public double getDeathAmount() {
		return deathAmount;
	}

	public void setDeathAmount(double deathAmount) {
		this.deathAmount = deathAmount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beneficiaryName == null) ? 0 : beneficiaryName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(deathAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hospitalizedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(medicationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(operationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		BeneficiaryClaimAmountInfoDTO other = (BeneficiaryClaimAmountInfoDTO) obj;
		if (beneficiaryName == null) {
			if (other.beneficiaryName != null)
				return false;
		} else if (!beneficiaryName.equals(other.beneficiaryName))
			return false;
		if (Double.doubleToLongBits(deathAmount) != Double.doubleToLongBits(other.deathAmount))
			return false;
		if (Double.doubleToLongBits(hospitalizedAmount) != Double.doubleToLongBits(other.hospitalizedAmount))
			return false;
		if (Double.doubleToLongBits(medicationAmount) != Double.doubleToLongBits(other.medicationAmount))
			return false;
		if (Double.doubleToLongBits(operationAmount) != Double.doubleToLongBits(other.operationAmount))
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}

}
