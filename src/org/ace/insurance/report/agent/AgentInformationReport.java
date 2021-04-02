package org.ace.insurance.report.agent;

import java.util.Calendar;
import java.util.Date;

import org.ace.insurance.system.common.agent.Agent;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class AgentInformationReport {
	public String agentCode;
	public String agentName;
	public String gender;
	public String liscenseNo;
	public String nrc;
	public Date appDate;
	public String service;
	public String age;
	public Date dob;
	public String qualificaiton;
	public String training;
	public String address;
	public String email;
	public String mobile;
	public String phoneNo;
	public String remark;
	public String filePath;
	public String outstandingEvent;
	public String organization;
	public String groupType;

	public AgentInformationReport() {
	}

	public AgentInformationReport(Agent agent) {
		this.agentCode = agent.getCodeNo();
		this.agentName = agent.getFullName();
		this.gender = agent.getGender().toString();
		this.liscenseNo = agent.getLiscenseNo();
		this.nrc = agent.getIdNo();
		this.appDate = agent.getAppointedDate();
		this.service = getServiceDetail(agent.getAppointedDate());
		this.age = getAge(agent.getDateOfBirth());
		this.dob = agent.getDateOfBirth();
		if (agent.getOrganization() != null) {
			this.organization = agent.getOrganization().getName();
		} else {
			this.organization = "";
		}
		this.groupType = agent.getGroupType().getLabel();

		if (agent.getQualification() == null) {
			this.qualificaiton = "";
		} else {
			this.qualificaiton = agent.getQualification().getName();
		}

		this.training = agent.getTraining();
		this.address = agent.getFullAddress();

		if (agent.getContentInfo() != null) {
			this.email = agent.getContentInfo().getEmail();
			this.mobile = agent.getContentInfo().getMobile();
			this.phoneNo = agent.getContentInfo().getPhone();
		} else {
			this.email = "";
			this.mobile = "";
			this.phoneNo = "";
		}

		this.remark = agent.getRemark();

		if (agent.getAttachment() != null) {
			this.filePath = agent.getAttachment().getFilePath();
		} else {
			this.filePath = null;
		}
		this.outstandingEvent = agent.getOutstandingEvent();

	}

	public AgentInformationReport(String agentCode, String agentName, String gender, String liscenseNo, String nrc, Date appDate, String service, String age, Date dob,
			String qualificaiton, String training, String address, String email, String mobile, String phoneNo, String remark, String filePath, String outstandingEvent,
			String orgaization, String groupType) {
		this.agentCode = agentCode;
		this.agentName = agentName;
		this.gender = gender;
		this.liscenseNo = liscenseNo;
		this.nrc = nrc;
		this.appDate = appDate;
		this.service = service;
		this.age = age;
		this.dob = dob;
		this.qualificaiton = qualificaiton;
		this.training = training;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
		this.phoneNo = phoneNo;
		this.remark = remark;
		this.filePath = filePath;
		this.outstandingEvent = outstandingEvent;
		this.organization = orgaization;
		this.groupType = groupType;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getQualificaiton() {
		return qualificaiton;
	}

	public void setQualificaiton(String qualificaiton) {
		this.qualificaiton = qualificaiton;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOutstandingEvent() {
		return outstandingEvent;
	}

	public void setOutstandingEvent(String outstandingEvent) {
		this.outstandingEvent = outstandingEvent;
	}

	public String getOrganization() {
		return organization;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	// public int getAge(Date dateOfBirth) {
	//
	// Calendar today = Calendar.getInstance();
	// Calendar birthDate = Calendar.getInstance();
	//
	// int age = 0;
	//
	// birthDate.setTime(dateOfBirth);
	// if (birthDate.after(today)) {
	// throw new IllegalArgumentException("Can't be born in the future");
	// }
	//
	// age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
	//
	// // If birth date is greater than todays date (after 2 days adjustment of
	// leap year) then decrement age one year
	// if ( (birthDate.get(Calendar.DAY_OF_YEAR) -
	// today.get(Calendar.DAY_OF_YEAR) > 3) ||
	// (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
	// age--;
	//
	// // If birth date and todays date are of same month and birth day of month
	// is greater than todays day of month then decrement age
	// }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH ))
	// &&
	// (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH
	// ))){
	// age--;
	// }
	//
	// return age;
	// }

	public String getAge(Date dob) {
		Period period = getPeriod(dob);
		return period.getYears() + " Y";
	}

	public String getServiceDetail(Date appDate) {
		Period period = getPeriod(appDate);
		return period.getYears() + "Y " + period.getMonths() + "M " + period.getDays() + "D";
	}

	public Period getPeriod(Date date) {
		DateTime startDate = new DateTime(date);
		Calendar cal = Calendar.getInstance();
		Date todayDate = cal.getTime();
		DateTime endDate = new DateTime(todayDate);
		Period period = new Period(startDate, endDate);
		return period;
	}

}