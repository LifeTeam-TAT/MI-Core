package org.ace.insurance.web.manage.enquires;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.web.common.SaleChannelType;

public class EnquiryCriteria {
	private Date startDate;
	private Date endDate;
	private Agent agent;
	private Customer customer;
	private Product product;
	private Organization organization;
	private BankBranch saleBank;

	private Branch branch;
	private String proposalNo;
	private String policyNo;
	private ProposalType proposalType;
	private boolean isClosed;
	private String registrationNo;
	private String claimReferenceNo;

	private InsuranceType insuranceType;
	private SaleChannelType saleChannelType;
	private CoinsuranceCompany coinsuranceCompany;
	private String coInsuPolicyNo;
	private List<String> productIdList;
	private String number;
	private SalesPoints salePoint;
	private String insuredPersonName;

	public EnquiryCriteria() {
	}

	public EnquiryCriteria(Date startDate, Date endDate, Agent agent, Customer customer, Product product, Organization organization, Branch branch, String number,
			String registrationNo, BankBranch saleBank, String claimReferencNo, InsuranceType insuranceType) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.agent = agent;
		this.customer = customer;
		this.product = product;
		this.organization = organization;

		this.branch = branch;
		this.policyNo = number;
		this.registrationNo = registrationNo;
		this.saleBank = saleBank;
		this.claimReferenceNo = claimReferencNo;

		this.insuranceType = insuranceType;
	}

	public EnquiryCriteria(Date startDate, Date endDate, Agent agent, Customer customer, Product product, Organization organization, Branch branch, String number,
			String registrationNo, String claimReferencNo, InsuranceType insuranceType) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.agent = agent;
		this.customer = customer;
		this.product = product;
		this.organization = organization;

		this.branch = branch;
		this.policyNo = number;
		this.registrationNo = registrationNo;
		this.claimReferenceNo = claimReferencNo;

		this.insuranceType = insuranceType;
	}

	public String getClaimReferenceNo() {
		return claimReferenceNo;
	}

	public void setClaimReferenceNo(String claimReferenceNo) {
		this.claimReferenceNo = claimReferenceNo;
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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public CoinsuranceCompany getCoinsuranceCompany() {
		return coinsuranceCompany;
	}

	public void setCoinsuranceCompany(CoinsuranceCompany coinsuranceCompany) {
		this.coinsuranceCompany = coinsuranceCompany;
	}

	public String getCoInsuPolicyNo() {
		return coInsuPolicyNo;
	}

	public void setCoInsuPolicyNo(String coInsuPolicyNo) {
		this.coInsuPolicyNo = coInsuPolicyNo;
	}

	public BankBranch getSaleBank() {
		return saleBank;
	}

	public void setSaleBank(BankBranch saleBank) {
		this.saleBank = saleBank;
	}

	public List<String> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public SalesPoints getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalesPoints salePoint) {
		this.salePoint = salePoint;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}
}
