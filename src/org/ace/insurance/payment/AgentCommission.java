package org.ace.insurance.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.AgentCommissionEntryType;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.AGENTCOMMISSION)
@TableGenerator(name = "AGENTCOMMISSION_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "AGENTCOMMISSION_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "AgentCommission.findAll", query = "SELECT a FROM AgentCommission a "),
		@NamedQuery(name = "AgentCommission.findById", query = "SELECT a FROM AgentCommission a WHERE a.id = :id"),
		@NamedQuery(name = "AgentCommission.findSanctionNoById", query = "SELECT a.sanctionNo FROM AgentCommission a WHERE a.id = :id"),
		@NamedQuery(name = "AgentCommission.findBySanctionNo", query = "SELECT a FROM AgentCommission a WHERE a.sanctionNo = :sanctionNo"),
		@NamedQuery(name = "AgentCommission.findByReferenceNo", query = "SELECT a FROM AgentCommission a WHERE a.referenceNo = :referenceNo") })
@EntityListeners(IDInterceptor.class)
public class AgentCommission implements IDataModel {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AGENTCOMMISSION_GEN")
	private String id;
	private String invoiceNo;
	private String referenceNo;
	private String sanctionNo;
	private String receiptNo;
	private String policyNo;
	// private String chequeNo;
	// private String bankaccountno;
	private double commission;
	private double premium;
	private double percentage;
	private double homeCommission;
	private double rate;
	private double homePremium;
	private double withHoldingTax;
	private double homeWithHoldingTax;
	// private Boolean isPaid;
	private Boolean status;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUR", referencedColumnName = "ID")
	private Currency currency;

	@Temporal(TemporalType.DATE)
	private Date commissionStartDate;

	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	// @Temporal(TemporalType.DATE)
	// private Date paymentDate;

	@Temporal(TemporalType.DATE)
	private Date sanctionDate;

	@Column(name = "REFERENCETYPE")
	@Enumerated(value = EnumType.STRING)
	private PolicyReferenceType referenceType;

	// @Enumerated(value = EnumType.STRING)
	// private PaymentChannel paymentChannel;

	@Column(name = "ENTRY_TYPE")
	@Enumerated(EnumType.STRING)
	private AgentCommissionEntryType entryType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "SALESPOINTSID", referencedColumnName = "ID")
	// private SalesPoints salesPoints;

	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "BANKID", referencedColumnName = "ID")
	// private Bank bank;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public AgentCommission() {
	}

	// TODO delete
	public AgentCommission(String referenceNo, PolicyReferenceType referenceType, Agent agent, double commission, Date commissionStartDate) {
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
		this.agent = agent;
		this.commission = commission;
		this.commissionStartDate = commissionStartDate;
		this.status = false;
	}

	/**
	 * used from insert agentCommision in payment(proposalService,
	 * autorRenwalService, paymentDelegateService)
	 */
	public AgentCommission(String referenceNo, PolicyReferenceType referenceType, String policyNo, Customer customer, Organization organization, Branch branch, Agent agent,
			double commission, Date commissionStartDate, String receiptNo, double premium, double percentage, AgentCommissionEntryType entryType, double rate,
			double homeCommission, Currency cur, double homePremium) {
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
		this.policyNo = policyNo;
		this.customer = customer;
		this.organization = organization;
		this.branch = branch;
		this.agent = agent;
		this.commission = commission;
		this.commissionStartDate = commissionStartDate;
		this.status = false;
		this.receiptNo = receiptNo;
		this.premium = premium;
		this.percentage = percentage;
		this.entryType = entryType;
		this.rate = rate;
		this.homeCommission = homeCommission;
		this.currency = cur;
		this.homePremium = homePremium;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public Date getCommissionStartDate() {
		return commissionStartDate;
	}

	public void setCommissionStartDate(Date commissionStartDate) {
		this.commissionStartDate = commissionStartDate;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public AgentCommissionEntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(AgentCommissionEntryType entryType) {
		this.entryType = entryType;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getHomeCommission() {
		return homeCommission;
	}

	public void setHomeCommission(double homeCommission) {
		this.homeCommission = homeCommission;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getHomePremium() {
		return homePremium;
	}

	public void setHomePremium(double homePremium) {
		this.homePremium = homePremium;
	}

	public double getWithHoldingTax() {
		return withHoldingTax;
	}

	public void setWithHoldingTax(double withHoldingTax) {
		this.withHoldingTax = withHoldingTax;
	}

	public double getHomeWithHoldingTax() {
		return homeWithHoldingTax;
	}

	public void setHomeWithHoldingTax(double homeWithHoldingTax) {
		this.homeWithHoldingTax = homeWithHoldingTax;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		long temp;
		temp = Double.doubleToLongBits(commission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((commissionStartDate == null) ? 0 : commissionStartDate.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		temp = Double.doubleToLongBits(homeCommission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(homePremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(homeWithHoldingTax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + ((invoiceNo == null) ? 0 : invoiceNo.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		temp = Double.doubleToLongBits(percentage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
		result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
		result = prime * result + ((sanctionDate == null) ? 0 : sanctionDate.hashCode());
		result = prime * result + ((sanctionNo == null) ? 0 : sanctionNo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + version;
		temp = Double.doubleToLongBits(withHoldingTax);
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
		AgentCommission other = (AgentCommission) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
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
		if (Double.doubleToLongBits(commission) != Double.doubleToLongBits(other.commission))
			return false;
		if (commissionStartDate == null) {
			if (other.commissionStartDate != null)
				return false;
		} else if (!commissionStartDate.equals(other.commissionStartDate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (entryType != other.entryType)
			return false;
		if (Double.doubleToLongBits(homeCommission) != Double.doubleToLongBits(other.homeCommission))
			return false;
		if (Double.doubleToLongBits(homePremium) != Double.doubleToLongBits(other.homePremium))
			return false;
		if (Double.doubleToLongBits(homeWithHoldingTax) != Double.doubleToLongBits(other.homeWithHoldingTax))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceNo == null) {
			if (other.invoiceNo != null)
				return false;
		} else if (!invoiceNo.equals(other.invoiceNo))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (Double.doubleToLongBits(percentage) != Double.doubleToLongBits(other.percentage))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (referenceNo == null) {
			if (other.referenceNo != null)
				return false;
		} else if (!referenceNo.equals(other.referenceNo))
			return false;
		if (referenceType != other.referenceType)
			return false;
		if (sanctionDate == null) {
			if (other.sanctionDate != null)
				return false;
		} else if (!sanctionDate.equals(other.sanctionDate))
			return false;
		if (sanctionNo == null) {
			if (other.sanctionNo != null)
				return false;
		} else if (!sanctionNo.equals(other.sanctionNo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (version != other.version)
			return false;
		if (Double.doubleToLongBits(withHoldingTax) != Double.doubleToLongBits(other.withHoldingTax))
			return false;
		return true;
	}

}
