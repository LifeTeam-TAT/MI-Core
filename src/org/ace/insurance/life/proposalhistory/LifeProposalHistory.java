package org.ace.insurance.life.proposalhistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.ProposalHistoryEntryType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeProposalAttachment;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEPROPOSAL_HISTORY)
@TableGenerator(name = "LIFEPROPOSAL_HISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPROPOSAL_HISTORY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeProposalHistory.findAll", query = "SELECT m FROM LifeProposalHistory m "),
		@NamedQuery(name = "LifeProposalHistory.findByDate", query = "SELECT m FROM LifeProposalHistory m WHERE m.submittedDate BETWEEN :startDate AND :endDate"),
		@NamedQuery(name = "LifeProposalHistory.updateCompleteStatus", query = "UPDATE LifeProposalHistory m SET m.complete = :complete WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifeProposalHistory implements Serializable, IDataModel {
	private static final long serialVersionUID = 7564214263861012292L;

	private boolean complete;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPROPOSAL_HISTORY_GEN")
	private String id;

	private String proposalNo;
	private String portalId;
	private int paymentTerm;
	private double currencyRate;

	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Enumerated(EnumType.STRING)
	private ProposalHistoryEntryType entryType;

	@Enumerated(EnumType.STRING)
	private WorkflowTask workflowTask;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFERRALID", referencedColumnName = "ID")
	private Customer referral;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPROPOSALID", referencedColumnName = "ID")
	private LifeProposal lifeProposal;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposalHistory", orphanRemoval = true)
	private List<LifeProposalInsuredPersonHistory> lifeProposalInsuredPersonHistoryList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposalHistory", orphanRemoval = true)
	private List<LifeProposalAttachmentHistory> lifeProposalAttachmentHistoryList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeProposalHistory() {
	}

	public LifeProposalHistory(LifeProposal lifeProposal) {
		this.agent = lifeProposal.getAgent();
		this.branch = lifeProposal.getBranch();
		this.customer = lifeProposal.getCustomer();
		this.paymentType = lifeProposal.getPaymentType();
		this.organization = lifeProposal.getOrganization();
		this.lifeProposal = lifeProposal;
		this.submittedDate = lifeProposal.getSubmittedDate();
		this.proposalNo = lifeProposal.getProposalNo();
		this.currencyRate = lifeProposal.getCurrencyRate();

		for (ProposalInsuredPerson insuredPerson : lifeProposal.getProposalInsuredPersonList()) {
			addLifeProposalInsuredPersonHistory(new LifeProposalInsuredPersonHistory(insuredPerson));
		}
		for (LifeProposalAttachment attachment : lifeProposal.getAttachmentList()) {
			addLifeProposalAttachmentHistory(new LifeProposalAttachmentHistory(attachment));
		}
	}

	/******************************************************
	 * getter / setter
	 **********************************************************/

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

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public boolean getComplete() {
		return this.complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
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

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Customer getReferral() {
		return referral;
	}

	public void setReferral(Customer referral) {
		this.referral = referral;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public ProposalHistoryEntryType getEntryType() {
		return entryType;
	}

	public WorkflowTask getWorkflowTask() {
		return workflowTask;
	}

	public void setWorkflowTask(WorkflowTask workflowTask) {
		this.workflowTask = workflowTask;
	}

	public void setEntryType(ProposalHistoryEntryType entryType) {
		this.entryType = entryType;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
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

	public void setLifeProposalInsuredPersonHistoryList(List<LifeProposalInsuredPersonHistory> lifeProposalInsuredPersonHistoryList) {
		this.lifeProposalInsuredPersonHistoryList = lifeProposalInsuredPersonHistoryList;
	}

	public List<LifeProposalInsuredPersonHistory> getLifeProposalInsuredPersonHistoryList() {
		if (this.lifeProposalInsuredPersonHistoryList == null) {
			this.lifeProposalInsuredPersonHistoryList = new ArrayList<LifeProposalInsuredPersonHistory>();
		}
		return this.lifeProposalInsuredPersonHistoryList;
	}

	public List<LifeProposalAttachmentHistory> getLifeProposalAttachmentHistoryList() {
		if (lifeProposalAttachmentHistoryList == null) {
			lifeProposalAttachmentHistoryList = new ArrayList<LifeProposalAttachmentHistory>();
		}
		return lifeProposalAttachmentHistoryList;
	}

	public void setLifeProposalAttachmentHistoryList(List<LifeProposalAttachmentHistory> lifeProposalAttachmentHistoryList) {
		this.lifeProposalAttachmentHistoryList = lifeProposalAttachmentHistoryList;
	}

	public void addLifeProposalAttachmentHistory(LifeProposalAttachmentHistory lifeProposalAttachmentHistory) {
		lifeProposalAttachmentHistory.setLifeProposalHistory(this);
		getLifeProposalAttachmentHistoryList().add(lifeProposalAttachmentHistory);
	}

	public void addLifeProposalInsuredPersonHistory(LifeProposalInsuredPersonHistory lifeProposalInsuredPersonHistory) {
		lifeProposalInsuredPersonHistory.setLifeProposalHistory(this);
		getLifeProposalInsuredPersonHistoryList().add(lifeProposalInsuredPersonHistory);
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + (complete ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(currencyRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lifeProposal == null) ? 0 : lifeProposal.hashCode());
		result = prime * result + ((lifeProposalAttachmentHistoryList == null) ? 0 : lifeProposalAttachmentHistoryList.hashCode());
		result = prime * result + ((lifeProposalInsuredPersonHistoryList == null) ? 0 : lifeProposalInsuredPersonHistoryList.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + paymentTerm;
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + periodMonth;
		result = prime * result + ((portalId == null) ? 0 : portalId.hashCode());
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		result = prime * result + version;
		result = prime * result + ((workflowTask == null) ? 0 : workflowTask.hashCode());
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
		LifeProposalHistory other = (LifeProposalHistory) obj;
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
		if (complete != other.complete)
			return false;
		if (Double.doubleToLongBits(currencyRate) != Double.doubleToLongBits(other.currencyRate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (entryType != other.entryType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lifeProposal == null) {
			if (other.lifeProposal != null)
				return false;
		} else if (!lifeProposal.equals(other.lifeProposal))
			return false;
		if (lifeProposalAttachmentHistoryList == null) {
			if (other.lifeProposalAttachmentHistoryList != null)
				return false;
		} else if (!lifeProposalAttachmentHistoryList.equals(other.lifeProposalAttachmentHistoryList))
			return false;
		if (lifeProposalInsuredPersonHistoryList == null) {
			if (other.lifeProposalInsuredPersonHistoryList != null)
				return false;
		} else if (!lifeProposalInsuredPersonHistoryList.equals(other.lifeProposalInsuredPersonHistoryList))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (paymentTerm != other.paymentTerm)
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (periodMonth != other.periodMonth)
			return false;
		if (portalId == null) {
			if (other.portalId != null)
				return false;
		} else if (!portalId.equals(other.portalId))
			return false;
		if (proposalNo == null) {
			if (other.proposalNo != null)
				return false;
		} else if (!proposalNo.equals(other.proposalNo))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (referral == null) {
			if (other.referral != null)
				return false;
		} else if (!referral.equals(other.referral))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (version != other.version)
			return false;
		if (workflowTask != other.workflowTask)
			return false;
		return true;
	}

}