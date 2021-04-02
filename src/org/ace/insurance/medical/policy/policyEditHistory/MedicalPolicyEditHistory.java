package org.ace.insurance.medical.policy.policyEditHistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.interfaces.IInsuredItem;
import org.ace.insurance.common.interfaces.IPolicy;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyAttachment;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonAddOn;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalAttachment;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.MEDPOLICY_EDITHISTORY)
@EntityListeners(IDInterceptor.class)
@TableGenerator(name = "MEDICALPOLICYEDITHISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALPOLICYEDITHISTORY_GEN", allocationSize = 10)
public class MedicalPolicyEditHistory implements IPolicy, Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPOLICYEDITHISTORY_GEN")
	private String id;
	private String policyNo;

	private boolean delFlag;

	private int printCount;
	private int paymentTerm;
	private int lastPaymentTerm;
	private int periodMonth;

	private double specialDiscount;

	@Column(name = "RATE")
	private double rate;

	@Temporal(TemporalType.DATE)
	private Date coverageDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEDPOLICYSTARTDATE")
	private Date activedPolicyStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEDPOLICYENDDATE")
	private Date activedPolicyEndDate;

	@Enumerated(EnumType.STRING)
	private PolicyStatus policyStatus;

	@Enumerated(EnumType.STRING)
	private HealthType healthType;

	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@Enumerated(EnumType.STRING)
	private CustomerType customerType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALESPOINTSID", referencedColumnName = "ID")
	private SalesPoints salesPoints;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne
	@JoinColumn(name = "PROPOSALID")
	private MedicalProposal medicalProposal;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEDICALPOLICYID", referencedColumnName = "ID")
	private List<MedicalPolicyInuredPersonEditHistory> policyInsuredPersonList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEDICALPOLICYID", referencedColumnName = "ID")
	private List<MedicalPolicyAttachment> attachmentList;

	private String referencePolicyNo;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public MedicalPolicyEditHistory() {
		super();
	}

	public MedicalPolicyEditHistory(MedicalProposal medicalProposal) {
		this.customer = medicalProposal.getCustomer();
		this.organization = medicalProposal.getOrganization();
		this.branch = medicalProposal.getBranch();
		this.salesPoints = medicalProposal.getSalesPoints();
		this.paymentType = medicalProposal.getPaymentType();
		this.agent = medicalProposal.getAgent();
		this.medicalProposal = medicalProposal;
		this.customerType = medicalProposal.getCustomerType();
		this.healthType = medicalProposal.getHealthType();
		this.periodMonth = medicalProposal.getPeriodMonth();
		this.paymentTerm = medicalProposal.getPaymentTerm();
		this.rate = medicalProposal.getRate();
		this.specialDiscount = medicalProposal.getSpecialDiscount();
		this.saleChannelType = medicalProposal.getSaleChannelType();

		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			addPolicyInsuredPerson(new MedicalPolicyInuredPersonEditHistory(person));
		}
		for (MedicalProposalAttachment attach : medicalProposal.getAttachmentList()) {
			addMedicalPolicyAttachment(new MedicalPolicyAttachment(attach));
		}
	}

	public MedicalPolicyEditHistory(MedicalPolicy medicalPolicy) {
		this.customer = medicalPolicy.getCustomer();
		this.organization = medicalPolicy.getOrganization();
		this.branch = medicalPolicy.getBranch();
		this.paymentType = medicalPolicy.getPaymentType();
		this.agent = medicalPolicy.getAgent();
		this.medicalProposal = medicalPolicy.getMedicalProposal();
		this.policyNo = medicalPolicy.getPolicyNo();
		this.commenmanceDate = medicalPolicy.getCommenmanceDate();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
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

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public void setMedicalProposal(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
	}

	public SalesPoints getSalesPoints() {
		return salesPoints;
	}

	public void setSalesPoints(SalesPoints salesPoints) {
		this.salesPoints = salesPoints;
	}

	public List<MedicalPolicyAttachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<MedicalPolicyAttachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<MedicalPolicyAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<MedicalPolicyInuredPersonEditHistory> getPolicyInsuredPersonList() {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<MedicalPolicyInuredPersonEditHistory>();
		}
		return policyInsuredPersonList;
	}

	public void setPolicyInsuredPersonList(List<MedicalPolicyInuredPersonEditHistory> policyInsuredPersonList) {
		this.policyInsuredPersonList = policyInsuredPersonList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getReferencePolicyNo() {
		return referencePolicyNo;
	}

	public void setReferencePolicyNo(String referencePolicyNo) {
		this.referencePolicyNo = referencePolicyNo;
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

	public Date getCoverageDate() {
		return coverageDate;
	}

	public void setCoverageDate(Date coverageDate) {
		this.coverageDate = coverageDate;
	}

	public void addMedicalPolicyAttachment(MedicalPolicyAttachment attachment) {
		getAttachmentList().add(attachment);
	}

	public void addPolicyInsuredPerson(MedicalPolicyInuredPersonEditHistory policyInsuredPerson) {
		getPolicyInsuredPersonList().add(policyInsuredPerson);
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public void setHealthType(HealthType healthType) {
		this.healthType = healthType;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	/**************************
	 * System Generated Method
	 **************************/
	public String getCustomerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getOwnerName();
		}
		return null;
	}

	public String getInsuredPersonName() {
		return policyInsuredPersonList.get(0).getCustomer().getFullName();
	}

	public double getAgentCommission() {
		double totalCommission = 0.0;
		if (agent != null) {
			double commissionPercent = policyInsuredPersonList.get(0).getProduct().getFirstCommission();
			if (commissionPercent > 0) {
				totalCommission = (getTotalTermPremium() * commissionPercent) / 100;
			}
		}
		return totalCommission;
	}

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		}
		return "";
	}

	public String getCustomerPhoneNo() {
		if (customer != null) {
			return customer.getContentInfo().getPhone();
		}
		if (organization != null) {
			return organization.getContentInfo().getPhone();
		}
		return null;
	}

	@Override
	public double getPremium() {
		double premium = 0.0;
		for (MedicalPolicyInuredPersonEditHistory pi : policyInsuredPersonList) {
			premium += pi.getPremium();
		}
		return Utils.getTwoDecimalPoint(premium);
	}

	@Override
	public double getAddOnPremium() {
		double addonPremium = 0.0;
		for (MedicalPolicyInuredPersonEditHistory pi : policyInsuredPersonList) {
			addonPremium += pi.getAddOnPremium();
		}
		return Utils.getTwoDecimalPoint(addonPremium);

	}

	public double getBasicTermPremium() {
		double termPermium = paymentType.getMonth() > 0 ? getPremium() * paymentType.getMonth() / 12 : getPremium();
		termPermium = Utils.getTwoDecimalPoint(termPermium);
		return termPermium;
	}

	public double getAddonTermPremium() {
		double termPermium = paymentType.getMonth() > 0 ? getAddOnPremium() * paymentType.getMonth() / 12 : getAddOnPremium();
		termPermium = Utils.getTwoDecimalPoint(termPermium);
		return termPermium;
	}

	public String getTotalBasicTermPremiumString() {
		double termPermium = 0.0;
		for (MedicalPolicyInuredPersonEditHistory pi : policyInsuredPersonList) {
			termPermium = Utils.getTwoDecimalPoint(termPermium + pi.getBasicTermPremium());
		}
		return Utils.getCurrencyFormatString(termPermium);
	}

	public String getTotalSumInsuredString() {
		return Utils.getCurrencyFormatString(getTotalSumInsured());
	}

	// TODO delete same as paymentTerm
	public int getTotalPaymentTimes() {
		// if (paymentType.getMonth() == 0) {
		// return 1;
		// } else {
		// int totalPaymentTimes = 0;
		// int paymentMonths = 0;
		// for (MedicalPolicyInuredPersonEditHistory i :
		// policyInsuredPersonList) {
		// if (i.getPeriodMonth() > paymentMonths) {
		// paymentMonths = i.getPeriodMonth();
		// }
		// }
		// totalPaymentTimes = paymentMonths / paymentType.getMonth();
		// return totalPaymentTimes;
		// }
		return 0;
	}

	public int getTotalUnit() {
		return getTotalBasicUnit() + getTotalAddOnUnit();
	}

	public int getTotalBasicUnit() {
		int unit = 0;
		for (MedicalPolicyInuredPersonEditHistory person : policyInsuredPersonList) {
			unit += person.getUnit();
		}
		return unit;
	}

	public int getTotalAddOnUnit() {
		int unit = 0;
		for (MedicalPolicyInuredPersonEditHistory person : policyInsuredPersonList) {
			for (MedicalPolicyInsuredPersonAddOn addOn : person.getPolicyInsuredPersonAddOnList()) {
				unit += addOn.getUnit();
			}
		}
		return unit;
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

	public double getTotalAddOnPremium() {
		double premium = 0.0;
		for (MedicalPolicyInuredPersonEditHistory person : policyInsuredPersonList) {
			premium += person.getAddOnPremium();
		}
		return premium;
	}

	public double getTotalPremium() {
		double premium = 0.0;
		for (MedicalPolicyInuredPersonEditHistory person : policyInsuredPersonList) {
			premium += person.getPremium() + person.getAddOnPremium();
		}
		return premium;
	}

	public double getRenewalAgentCommission() {
		double totalCommission = 0.0;
		if (agent != null) {
			double commissionPercent = policyInsuredPersonList.get(0).getProduct().getRenewalCommission();
			if (commissionPercent > 0) {
				// double totalPremium =
				// policyInsuredPersonList.get(0).getBasicTermPremium();// +
				// policyInsuredPerson.getAddOnTermPremium();
				// double commission = (totalPremium * commissionPercent) / 100;
				// totalCommission = totalCommission + commission;
			}
		}
		return totalCommission;
	}

	public MedicalPolicyInuredPersonEditHistory getMedicalPolicyInsuredPerson(Customer customer) {
		MedicalPolicyInuredPersonEditHistory result = null;
		for (MedicalPolicyInuredPersonEditHistory person : policyInsuredPersonList) {
			if (customer != null && customer.getId().equals(person.getCustomer().getId())) {
				result = person;
				break;
			}
		}
		return result;
	}

	public double getTotalTermPremiumDouble() {
		double termPermium = 0.0;
		for (MedicalPolicyInuredPersonEditHistory pi : policyInsuredPersonList) {
			termPermium += pi.getBasicTermPremium();
			for (MedicalPolicyInsuredPersonAddOn pia : pi.getPolicyInsuredPersonAddOnList()) {
				double addOnTermPremium = paymentType.getMonth() > 0 ? (pia.getPremium()) * paymentType.getMonth() / 12 : pia.getPremium();
				termPermium += addOnTermPremium;
			}
		}
		return Utils.getTwoDecimalPoint(termPermium);

	}

	@Override
	public double getTotalTermPremium() {
		return getBasicTermPremium() + getAddonTermPremium();
	}

	@Override
	public double getTotalSumInsured() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCoinsuranceApplied() {
		return false;
	}

	@Override
	public ProductGroup getProductGroup() {
		return policyInsuredPersonList.get(0).getProduct().getProductGroup();
	}

	@Override
	public String getCustomerId() {
		if (customer != null) {
			return customer.getId();
		}
		if (organization != null) {
			return organization.getId();
		}
		return null;
	}

	@Override
	public List<IInsuredItem> getInsuredItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InsuranceType getInsuranceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProposalType getProposalType() {
		return this.medicalProposal.getProposalType();
	}

	public int getPeriodYears() {
		return periodMonth / 12;
	}

	public double getSpecialDiscountAmount() {
		double specialDiscountAmount = Utils.getPercentOf(specialDiscount, getPremium());
		return Utils.getTwoDecimalPoint(specialDiscountAmount);
	}

	public double getTotalDiscountAmount() {
		return getSpecialDiscountAmount();
	}

	public void addPolicyInsuredPersonInfo(MedicalPolicyInuredPersonEditHistory policyInsuredPersonInfo) {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<MedicalPolicyInuredPersonEditHistory>();
		}
		policyInsuredPersonList.add(policyInsuredPersonInfo);
	}

	public String getTimeSlotList() {
		List<String> result = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(activedPolicyStartDate);
		int months = getPaymentType().getMonth();
		if (months > 0 && months != 12) {
			result = new ArrayList<String>();
			int a = 12 / months;
			for (int i = 1; i < a; i++) {
				cal.add(Calendar.MONTH, months);
				if (activedPolicyEndDate.after(cal.getTime()))
					result.add(Utils.formattedDate(cal.getTime()));
			}
		}
		return result != null ? result.toString().substring(1, result.toString().length() - 1) : null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((coverageDate == null) ? 0 : coverageDate.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
		result = prime * result + (delFlag ? 1231 : 1237);
		result = prime * result + ((healthType == null) ? 0 : healthType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lastPaymentTerm;
		result = prime * result + ((medicalProposal == null) ? 0 : medicalProposal.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + paymentTerm;
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + periodMonth;
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
		result = prime * result + printCount;
		long temp;
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((referencePolicyNo == null) ? 0 : referencePolicyNo.hashCode());
		result = prime * result + ((saleChannelType == null) ? 0 : saleChannelType.hashCode());
		result = prime * result + ((salesPoints == null) ? 0 : salesPoints.hashCode());
		temp = Double.doubleToLongBits(specialDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + version;
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
		MedicalPolicyEditHistory other = (MedicalPolicyEditHistory) obj;
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
		if (coverageDate == null) {
			if (other.coverageDate != null)
				return false;
		} else if (!coverageDate.equals(other.coverageDate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (customerType != other.customerType)
			return false;
		if (delFlag != other.delFlag)
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
		if (medicalProposal == null) {
			if (other.medicalProposal != null)
				return false;
		} else if (!medicalProposal.equals(other.medicalProposal))
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
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (policyStatus != other.policyStatus)
			return false;
		if (printCount != other.printCount)
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (referencePolicyNo == null) {
			if (other.referencePolicyNo != null)
				return false;
		} else if (!referencePolicyNo.equals(other.referencePolicyNo))
			return false;
		if (saleChannelType != other.saleChannelType)
			return false;
		if (salesPoints == null) {
			if (other.salesPoints != null)
				return false;
		} else if (!salesPoints.equals(other.salesPoints))
			return false;
		if (Double.doubleToLongBits(specialDiscount) != Double.doubleToLongBits(other.specialDiscount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
