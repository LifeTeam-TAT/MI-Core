package org.ace.insurance.report.customer;

import java.util.Date;

import org.ace.insurance.report.customer.view.CustomerInformationReportView;

public class CustomerInformationReport {
	public String customerId;
	public String customerName;
	public String gender;
	public String nrc;
	public String age;
	public Date dob;
	public String qualificaiton;
	public String training;
	public String address;
	public String email;
	public String mobile;
	public String phoneNo;
	public String fatherName;
	public int activePolicy;

	public CustomerInformationReport() {
	}

	public CustomerInformationReport(CustomerInformationReportView view) {
		super();
		this.customerId = view.getCustomerId();
		this.customerName = view.getCustomerName();
		this.gender = view.getGender();
		this.nrc = view.getNrc();
		this.age = view.getAge();
		this.dob = view.getDob();
		this.qualificaiton = view.getQualificaiton();
		this.address = view.getCustomerAddress();
		this.email = view.getEmail();
		this.mobile = view.getMobile();
		this.phoneNo = view.getPhoneNo();
		this.fatherName = view.getFatherName();
		this.training = "";
		this.activePolicy = view.getActivePolicy();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public int getActivePolicy() {
		return activePolicy;
	}

	public void setActivePolicy(int activePolicy) {
		this.activePolicy = activePolicy;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

}