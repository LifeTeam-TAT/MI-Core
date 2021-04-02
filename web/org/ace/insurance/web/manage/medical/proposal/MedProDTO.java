package org.ace.insurance.web.manage.medical.proposal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.web.common.CommonDTO;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyDTO;

public class MedProDTO extends CommonDTO {
	private String id;
	private boolean existsEntity;
	private String proposalNo;
	private ProposalType proposalType;
	private Date submittedDate;
	private Branch branch;
	private CustomerDTO customer;
	private PaymentType paymentType;
	private Agent agent;
	private List<MedProAttDTO> attachmentList;
	private List<MedProInsuDTO> medProInsuDTOList;
	private int version;
	private MedicalPolicyDTO medicalPolicyDTO;
	private CustomerType customerType;
	private SaleChannelType saleChannelType;
	private OrganizationDTO organization;
	private HealthType healthType;
	private int paymentTerm;
	private int periodOfMonth;
	private Date startDate;
	private Date endDate;

	public MedProDTO() {
	}

	public String getCustomerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getName();
		}
		return null;
	}

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		}
		return null;
	}

	public List<MedProInsuDTO> getMedProInsuDTOList() {
		if (medProInsuDTOList == null) {
			medProInsuDTOList = new ArrayList<MedProInsuDTO>();
		}
		return medProInsuDTOList;
	}

	public MedicalPolicyDTO getMedicalPolicyDTO() {
		return medicalPolicyDTO;
	}

	public void setMedicalPolicyDTO(MedicalPolicyDTO medicalPolicyDTO) {
		this.medicalPolicyDTO = medicalPolicyDTO;
	}

	public void setMedProInsuDTOList(List<MedProInsuDTO> medProInsuDTOList) {
		this.medProInsuDTOList = medProInsuDTOList;
	}

	public boolean isExistsEntity() {
		return existsEntity;
	}

	public void setExistsEntity(boolean existsEntity) {
		this.existsEntity = existsEntity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public List<MedProAttDTO> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<MedProAttDTO>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<MedProAttDTO> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addAttachment(MedProAttDTO attachment) {
		if (!getAttachmentList().contains(attachment)) {
			getAttachmentList().add(attachment);
		}
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public void setHealthType(HealthType healthType) {
		this.healthType = healthType;
	}

	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public int getPeriodOfMonth() {
		return periodOfMonth;
	}

	public void setPeriodOfMonth(int periodOfMonth) {
		this.periodOfMonth = periodOfMonth;
	}

	public void addInsuredPerson(MedProInsuDTO dto) {
		if (!getMedProInsuDTOList().contains(dto)) {
			getMedProInsuDTOList().add(dto);
		}
	}

	public double getTotalPremium() {
		return getTotalBasicPremium() + getTotalAddOnPremium();
	}

	public double getTotalAddOnPremium() {
		double premium = 0.0;
		for (MedProInsuDTO insuDTO : medProInsuDTOList) {
			premium += insuDTO.getAddOnPremium();
		}
		return premium;
	}

	public double getTotalBasicPremium() {
		double premium = 0.0;
		for (MedProInsuDTO insuDTO : medProInsuDTOList) {
			premium += insuDTO.getPremium();
		}
		return premium;
	}

	public double getTotalUnit() {
		return getTotalBasicUnit() + getTotalInsuAddOnUnit();
	}

	public double getTotalInsuAddOnUnit() {
		double premium = 0.0;
		for (MedProInsuDTO insuDTO : medProInsuDTOList) {
			premium += insuDTO.getTotalAddOnUnit();
		}
		return premium;
	}

	public double getTotalBasicUnit() {
		double unit = 0;
		for (MedProInsuDTO insuDTO : medProInsuDTOList) {
			unit += insuDTO.getUnit();
		}
		return unit;
	}

	public double getTotalPremiumwithDiscount() {
		return getTotalPremium();
	}

	public String getAttachmentRootPath() {
		return id;
	}

	public void addInsurancePerson(MedProInsuDTO insurancePerson) {
		if (!getMedProInsuDTOList().contains(insurancePerson)) {
			getMedProInsuDTOList().add(insurancePerson);
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
		result = prime * result + (existsEntity ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicalPolicyDTO == null) ? 0 : medicalPolicyDTO.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result + ((proposalType == null) ? 0 : proposalType.hashCode());
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
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
		MedProDTO other = (MedProDTO) obj;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (customerType != other.customerType)
			return false;
		if (existsEntity != other.existsEntity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalPolicyDTO == null) {
			if (other.medicalPolicyDTO != null)
				return false;
		} else if (!medicalPolicyDTO.equals(other.medicalPolicyDTO))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (proposalNo == null) {
			if (other.proposalNo != null)
				return false;
		} else if (!proposalNo.equals(other.proposalNo))
			return false;
		if (proposalType != other.proposalType)
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
