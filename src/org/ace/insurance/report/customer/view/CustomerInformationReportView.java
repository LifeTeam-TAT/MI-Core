package org.ace.insurance.report.customer.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_CUSTOMER_INFORMATION_REPORT)
@ReadOnly
public class CustomerInformationReportView {
	@Id
	public String customerId;
	public String customerName;
	public String customerAddress;
	public String gender;
	public String nrc;
	public String age;
	@Temporal(TemporalType.TIMESTAMP)
	public Date dob;
	public String qualificaiton;
	public String email;
	public String mobile;
	public String phoneNo;
	public String fatherName;
	public int activePolicy;
	public String branchId;

	public CustomerInformationReportView() {
	}

	public CustomerInformationReportView(String customerId, String customerName, String customerAddress, String gender, String nrc, String age, Date dob, String qualificaiton,
			String email, String mobile, String phoneNo, String fatherName, int activePolicy, String branchId) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.gender = gender;
		this.nrc = nrc;
		this.age = age;
		this.dob = dob;
		this.qualificaiton = qualificaiton;
		this.email = email;
		this.mobile = mobile;
		this.phoneNo = phoneNo;
		this.fatherName = fatherName;
		this.activePolicy = activePolicy;
		this.branchId = branchId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public String getGender() {
		return gender;
	}

	public String getNrc() {
		return nrc;
	}

	public String getAge() {
		return age;
	}

	public Date getDob() {
		return dob;
	}

	public String getQualificaiton() {
		return qualificaiton;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public String getFatherName() {
		return fatherName;
	}

	public int getActivePolicy() {
		return activePolicy;
	}

	public String getBranchId() {
		return branchId;
	}

}
