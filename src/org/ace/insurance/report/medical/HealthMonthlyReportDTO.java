package org.ace.insurance.report.medical;


public class HealthMonthlyReportDTO {
	private String policyNo;
	private String insuredName;
	private String nrc;
	private String occupation;
	private int age;
	private String address;
	private int unit;
	private String periodOfInsurance;
	private double premium;
	private float comission;
	private String reNoAndDate;
	private String agentName;
	private String fullIdNo;

	public HealthMonthlyReportDTO() {
	}

	public HealthMonthlyReportDTO(String policyNo, String insuredName, String nrc, String occupation, int age, String address, int unit, String periodOfInsurance, double premium,
			float comission, String reNoAndDate, String agentName, String fullIdNo) {
		this.policyNo = policyNo;
		this.insuredName = insuredName;
		if (fullIdNo != null) {
			this.nrc = fullIdNo;
		} else {
			this.nrc = nrc;
		}

		this.occupation = occupation;
		this.age = age;
		this.address = address;
		this.unit = unit;
		this.periodOfInsurance = periodOfInsurance;
		this.premium = premium;
		if (agentName.length() > 3) {
			this.comission = comission;
		} else {
			this.comission = 0;
		}
		this.reNoAndDate = reNoAndDate;
		this.agentName = agentName;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
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

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
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
