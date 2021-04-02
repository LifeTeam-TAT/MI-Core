package org.ace.insurance.web.common;

public class PaymentTableDTO {

	private StringBuffer beneficiaryName;
	private String chalanNo;
	private double operationAmount;
	private double medicationAmount;
	private double hospitalizedAmount;
	private double deathAmount;
	private double totalAmount;

	public PaymentTableDTO(StringBuffer beneficiaryName, String chalanNo, double operationAmount, double medicationAmount, double hospitalizedAmount, double deathAmount,
			double totalAmount) {
		super();
		this.beneficiaryName = beneficiaryName;
		this.chalanNo = chalanNo;
		this.operationAmount = operationAmount;
		this.medicationAmount = medicationAmount;
		this.hospitalizedAmount = hospitalizedAmount;
		this.deathAmount = deathAmount;
		this.totalAmount = totalAmount;
	}

	public StringBuffer getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(StringBuffer beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
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

	public double getHospitalizedAmount() {
		return hospitalizedAmount;
	}

	public void setHospitalizedAmount(double hospitalizedAmount) {
		this.hospitalizedAmount = hospitalizedAmount;
	}

	public double getDeathAmount() {
		return deathAmount;
	}

	public void setDeathAmount(double deathAmount) {
		this.deathAmount = deathAmount;
	}

	public double getTotalAmount() {
		return this.getDeathAmount() + this.getHospitalizedAmount() + this.getMedicationAmount() + this.getOperationAmount();
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chalanNo == null) ? 0 : chalanNo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(deathAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hospitalizedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(medicationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(operationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalAmount);
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
		PaymentTableDTO other = (PaymentTableDTO) obj;
		if (chalanNo == null) {
			if (other.chalanNo != null)
				return false;
		} else if (!chalanNo.equals(other.chalanNo))
			return false;
		if (Double.doubleToLongBits(deathAmount) != Double.doubleToLongBits(other.deathAmount))
			return false;
		if (Double.doubleToLongBits(hospitalizedAmount) != Double.doubleToLongBits(other.hospitalizedAmount))
			return false;
		if (Double.doubleToLongBits(medicationAmount) != Double.doubleToLongBits(other.medicationAmount))
			return false;
		if (Double.doubleToLongBits(operationAmount) != Double.doubleToLongBits(other.operationAmount))
			return false;
		if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
			return false;
		return true;
	}
}
