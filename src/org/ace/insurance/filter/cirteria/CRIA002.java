package org.ace.insurance.filter.cirteria;

import java.util.Date;

import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;

public class CRIA002 {
	private Date startDate;
	private Date endDate;
	private String agentId;
	private String customerId;
	private String productId;
	private String organizationId;
	private String typeOfBodyId;
	private String saleManId;
	private String branchId;
	private Agent agent;
	private Customer customer;
	private String product;
	private Organization organization;
	private String typeOfBody;
	private String branch;
	private String referenceNo;
	private boolean isEndorsement;

	public CRIA002() {
	}

	public CRIA002(Date startDate, Date endDate, String agentId,
			String customerId, String productId, String organizationId,
			String typeOfBodyId, String saleManId, String branchId,
			String referenceNo, boolean isEndorsement) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.agentId = agentId;
		this.customerId = customerId;
		this.productId = productId;
		this.organizationId = organizationId;
		this.typeOfBodyId = typeOfBodyId;
		this.saleManId = saleManId;
		this.branchId = branchId;
		this.referenceNo = referenceNo;
		this.isEndorsement = isEndorsement;
	}

	public Date getStartDate() {
		return startDate;
	}

	public java.sql.Timestamp getSQLStartDate() {
		return new java.sql.Timestamp(startDate.getTime());
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public java.sql.Timestamp getSQLEndDate() {
		return new java.sql.Timestamp(endDate.getTime());
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getTypeOfBodyId() {
		return typeOfBodyId;
	}

	public void setTypeOfBodyId(String typeOfBodyId) {
		this.typeOfBodyId = typeOfBodyId;
	}

	public String getSaleManId() {
		return saleManId;
	}

	public void setSaleManId(String saleManId) {
		this.saleManId = saleManId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public boolean isEndorsement() {
		return isEndorsement;
	}

	public void setEndorsement(boolean isEndorsement) {
		this.isEndorsement = isEndorsement;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getTypeOfBody() {
		return typeOfBody;
	}

	public void setTypeOfBody(String typeOfBody) {
		this.typeOfBody = typeOfBody;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
}
