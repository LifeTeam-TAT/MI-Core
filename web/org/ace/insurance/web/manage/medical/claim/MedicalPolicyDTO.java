package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.medical.policy.MedicalPolicyAttachment;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.web.common.CommonDTO;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.manage.medical.proposal.MedProDTO;

public class MedicalPolicyDTO extends CommonDTO {

	private boolean exitsEntity;
	private boolean delFlag;

	private int lastPaymentTerm;
	private int printCount;

	private int version;
	private String policyNo;
	private String id;
	private Date commenmanceDate;
	private Date activedPolicyStartDate;
	private Date activedPolicyEndDate;
	private PolicyStatus policyStatus;
	private Customer customer;
	private Organization organization;
	private Branch branch;
	private PaymentType paymentType;
	private Agent agent;
	private MedProDTO medProDTO;
	private List<MedicalPolicyInsuredPersonDTO> policyInsuredPersonDtoList;
	private List<MedicalPolicyAttachment> attachmentList;
	private double paidOperationAmount;
	private double paidMedicationAmount;
	private HealthType healthType;
	private CustomerType customerType;
	private SaleChannelType saleChannelType;
	private SalesPoints salesPoints;

	public MedicalPolicyDTO() {

	}

	public MedicalPolicyDTO(MedicalPolicyDTO medicalPolicyDTO) {
		this.delFlag = medicalPolicyDTO.isDelFlag();
		this.lastPaymentTerm = medicalPolicyDTO.getLastPaymentTerm();
		this.printCount = medicalPolicyDTO.getPrintCount();
		this.version = medicalPolicyDTO.getVersion();
		this.policyNo = medicalPolicyDTO.getPolicyNo();
		this.id = medicalPolicyDTO.getId();
		this.commenmanceDate = medicalPolicyDTO.getCommenmanceDate();
		this.activedPolicyStartDate = medicalPolicyDTO.getActivedPolicyStartDate();
		this.activedPolicyEndDate = medicalPolicyDTO.getActivedPolicyEndDate();
		this.policyStatus = medicalPolicyDTO.getPolicyStatus();
		this.customer = medicalPolicyDTO.getCustomer();
		this.organization = medicalPolicyDTO.getOrganization();
		this.branch = medicalPolicyDTO.getBranch();
		this.paymentType = medicalPolicyDTO.getPaymentType();
		this.agent = medicalPolicyDTO.getAgent();
		this.attachmentList = medicalPolicyDTO.getAttachmentList();
		this.exitsEntity = medicalPolicyDTO.isExitsEntity();
		this.medProDTO = medicalPolicyDTO.getMedProDTO();
		this.paidMedicationAmount = medicalPolicyDTO.getPaidMedicationAmount();
		this.paidOperationAmount = medicalPolicyDTO.getPaidOperationAmount();
		this.healthType = medicalPolicyDTO.getHealthType();
		this.customerType = medicalPolicyDTO.getCustomerType();
		this.saleChannelType = medicalPolicyDTO.getSaleChannelType();
		this.salesPoints = medicalPolicyDTO.getSalesPoints();
		for (MedicalPolicyInsuredPersonDTO personDTO : medicalPolicyDTO.getPolicyInsuredPersonDtoList()) {
			addPolicyInsuredPersonDTO(personDTO);
		}
	}

	public void addPolicyInsuredPersonDTO(MedicalPolicyInsuredPersonDTO policyInsuredPerson) {
		getPolicyInsuredPersonDtoList().add(policyInsuredPerson);
	}

	public MedProDTO getMedProDTO() {
		return medProDTO;
	}

	public void setMedProDTO(MedProDTO medProDTO) {
		this.medProDTO = medProDTO;
	}

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public int getLastPaymentTerm() {
		return lastPaymentTerm;
	}

	public void setLastPaymentTerm(int lastPaymentTerm) {
		this.lastPaymentTerm = lastPaymentTerm;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<MedicalPolicyInsuredPersonDTO> getPolicyInsuredPersonDtoList() {
		if (policyInsuredPersonDtoList == null) {
			policyInsuredPersonDtoList = new ArrayList<MedicalPolicyInsuredPersonDTO>();
		}
		return policyInsuredPersonDtoList;
	}

	public void setPolicyInsuredPersonDtoList(List<MedicalPolicyInsuredPersonDTO> policyInsuredPersonDtoList) {
		this.policyInsuredPersonDtoList = policyInsuredPersonDtoList;
	}

	public boolean isExitsEntity() {
		return exitsEntity;
	}

	public void setExitsEntity(boolean exitsEntity) {
		this.exitsEntity = exitsEntity;
	}

	public List<MedicalPolicyAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<MedicalPolicyAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addMedicalPolicyAttachment(MedicalPolicyAttachment medicalPolicyAttachment) {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<MedicalPolicyAttachment>();
		}
		this.attachmentList.add(medicalPolicyAttachment);
	}

	public String getCustomerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getOwnerName();
		}
		return null;
	}

	public String getCustomerAddress() {
		if (customer != null) {
			return customer.getFullAddress();
		}
		if (organization != null) {
			return organization.getFullAddress();
		}
		return null;
	}

	public double getPremium() {
		double result = 0.0;
		for (MedicalPolicyInsuredPersonDTO personDTO : policyInsuredPersonDtoList) {
			result += personDTO.getPremium();
		}
		return result;
	}

	public double getPaidOperationAmount() {
		return paidOperationAmount;
	}

	public void setPaidOperationAmount(double paidOperationAmount) {
		this.paidOperationAmount = paidOperationAmount;
	}

	public double getPaidMedicationAmount() {
		return paidMedicationAmount;
	}

	public void setPaidMedicationAmount(double paidMedicationAmount) {
		this.paidMedicationAmount = paidMedicationAmount;
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public void setHealthType(HealthType healthType) {
		this.healthType = healthType;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public SalesPoints getSalesPoints() {
		return salesPoints;
	}

	public void setSalesPoints(SalesPoints salesPoints) {
		this.salesPoints = salesPoints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((attachmentList == null) ? 0 : attachmentList.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + (delFlag ? 1231 : 1237);
		result = prime * result + (exitsEntity ? 1231 : 1237);
		result = prime * result + ((healthType == null) ? 0 : healthType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lastPaymentTerm;
		result = prime * result + ((medProDTO == null) ? 0 : medProDTO.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		long temp;
		temp = Double.doubleToLongBits(paidMedicationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(paidOperationAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
		result = prime * result + printCount;
		result = prime * result + version;
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
		MedicalPolicyDTO other = (MedicalPolicyDTO) obj;
		if (activedPolicyEndDate == null) {
			if (other.activedPolicyEndDate != null)
				return false;
		} else if (!activedPolicyEndDate.equals(other.activedPolicyEndDate))
			return false;
		if (activedPolicyStartDate == null) {
			if (other.activedPolicyStartDate != null)
				return false;
		} else if (!activedPolicyStartDate.equals(other.activedPolicyStartDate))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (attachmentList == null) {
			if (other.attachmentList != null)
				return false;
		} else if (!attachmentList.equals(other.attachmentList))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (commenmanceDate == null) {
			if (other.commenmanceDate != null)
				return false;
		} else if (!commenmanceDate.equals(other.commenmanceDate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (delFlag != other.delFlag)
			return false;
		if (exitsEntity != other.exitsEntity)
			return false;
		if (healthType != other.healthType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastPaymentTerm != other.lastPaymentTerm)
			return false;
		if (medProDTO == null) {
			if (other.medProDTO != null)
				return false;
		} else if (!medProDTO.equals(other.medProDTO))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (Double.doubleToLongBits(paidMedicationAmount) != Double.doubleToLongBits(other.paidMedicationAmount))
			return false;
		if (Double.doubleToLongBits(paidOperationAmount) != Double.doubleToLongBits(other.paidOperationAmount))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (policyStatus != other.policyStatus)
			return false;
		if (printCount != other.printCount)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
