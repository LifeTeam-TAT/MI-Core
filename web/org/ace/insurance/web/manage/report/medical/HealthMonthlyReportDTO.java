package org.ace.insurance.web.manage.report.medical;

public class HealthMonthlyReportDTO {
	private String policyNo;
	private String insuredName;
	private String nrc;
	private String occupation;
	private int age;
	private String address;
	private String unit;
	private String periodOfInsurance;
	private double premium;
	private float comission;
	private String reNoAndDate;
	private String agentName;

	public HealthMonthlyReportDTO() {
	}

	public HealthMonthlyReportDTO(String policyNo, String insuredName, String nrc, String occupation, int age, String address, String unit, String periodOfInsurance,
			double premium, float comission, String reNoAndDate, String agentName) {
		this.policyNo = policyNo;
		this.insuredName = insuredName;
		this.nrc = nrc;
		this.occupation = occupation;
		this.age = age;
		this.address = address;
		this.unit = unit;
		this.periodOfInsurance = periodOfInsurance;
		this.premium = premium;
		this.comission = comission;
		this.reNoAndDate = reNoAndDate;
		this.agentName = agentName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPeriodOfInsurance() {
		return periodOfInsurance;
	}

	public void setPeriodOfInsurance(String periodOfInsurance) {
		this.periodOfInsurance = periodOfInsurance;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public float getComission() {
		return comission;
	}

	public void setComission(float comission) {
		this.comission = comission;
	}

	public String getReNoAndDate() {
		return reNoAndDate;
	}

	public void setReNoAndDate(String reNoAndDate) {
		this.reNoAndDate = reNoAndDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

}
