// package org.ace.insurance.policytermination;
//
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
//
// import javax.persistence.CascadeType;
// import javax.persistence.Column;
// import javax.persistence.Embedded;
// import javax.persistence.Entity;
// import javax.persistence.EntityListeners;
// import javax.persistence.EnumType;
// import javax.persistence.Enumerated;
// import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.OneToMany;
// import javax.persistence.OneToOne;
// import javax.persistence.Table;
// import javax.persistence.TableGenerator;
// import javax.persistence.Temporal;
// import javax.persistence.TemporalType;
// import javax.persistence.Version;
//
// import org.ace.insurance.common.PolicyStatus;
// import org.ace.insurance.common.TableName;
// import org.ace.insurance.common.UserRecorder;
// import org.ace.insurance.life.policy.LifePolicy;
// import org.ace.insurance.life.policy.LifePolicyAttachment;
// import org.ace.insurance.life.policy.PolicyInsuredPerson;
// import org.ace.insurance.life.proposal.LifeProposal;
// import org.ace.insurance.system.common.agent.Agent;
// import org.ace.insurance.system.common.bankBranch.BankBranch;
// import org.ace.insurance.system.common.branch.Branch;
// import org.ace.insurance.system.common.customer.Customer;
// import org.ace.insurance.system.common.organization.Organization;
// import org.ace.insurance.system.common.paymenttype.PaymentType;
// import org.ace.insurance.system.common.salesPoints.SalesPoints;
// import org.ace.insurance.user.User;
// import org.ace.insurance.web.common.SaleChannelType;
// import org.ace.java.component.idgen.service.IDInterceptor;
//
// @Entity
// @Table(name = TableName.POLICYTERMINATION)
// @TableGenerator(name = "POLICYTERMINATION_GEN", table = "ID_GEN",
// pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue =
// "POLICYTERMINATION_GEN", allocationSize = 10)
// @EntityListeners(IDInterceptor.class)
// public class PolicyTermination {
//
// @Id
// @GeneratedValue(strategy = GenerationType.TABLE, generator =
// "POLICYTERMINATION_GEN")
// private String id;
//
// private boolean delFlag;
// private boolean isCoinsuranceApplied;
// private boolean isEndorsementApplied;
// private boolean isNonFinancialEndorse;
// private int lastPaymentTerm;
// @Column(name = "PERIODOFMONTH")
// private int periodMonth;
// private int printCount;
// private double totalDiscountAmount;
// private double standardExcess;
// private double specialDiscount;
// private double currencyRate;
// private String policyNo;
// private String endorsementNo;
//
// /* Underwriting payment date */
// @Temporal(TemporalType.TIMESTAMP)
// private Date commenmanceDate;
//
// @Temporal(TemporalType.TIMESTAMP)
// @Column(name = "ACTIVEDPOLICYSTARTDATE")
// private Date activedPolicyStartDate;
//
// @Temporal(TemporalType.TIMESTAMP)
// @Column(name = "ACTIVEDPOLICYENDDATE")
// private Date activedPolicyEndDate;
//
// @Temporal(TemporalType.TIMESTAMP)
// @Column(name = "COVERAGEDATE")
// private Date coverageDate;
//
// @Column(name = "ENDORSEMENTCONFIRMDATE")
// @Temporal(TemporalType.DATE)
// private Date endorsementConfirmDate;
//
// @Column(name = "RENEWALCONFIRMDATE")
// @Temporal(TemporalType.DATE)
// private Date renewalConfirmDate;
//
// @Enumerated(EnumType.STRING)
// private PolicyStatus policyStatus;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
// private Customer customer;
//
// @Enumerated(EnumType.STRING)
// private SaleChannelType saleChannelType;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
// private Organization organization;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "APPROVERID", referencedColumnName = "ID")
// private User approvedBy;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
// private Branch branch;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "SALESPOINTSID", referencedColumnName = "ID")
// private SalesPoints salesPoints;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
// private PaymentType paymentType;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "AGENTID", referencedColumnName = "ID")
// private Agent agent;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "SALEBANKID", referencedColumnName = "ID")
// private BankBranch saleBank;
//
// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
// "lifePolicy", orphanRemoval = true)
// private List<PolicyTerminateInsuredPerson> policyInsuredPersonList;
//
// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
// "lifePolicy", orphanRemoval = true)
// private List<LifePolicyTerminateAttachment> attachmentList;
//
// @OneToOne
// @JoinColumn(name = "PROPOSALID")
// private LifeProposal lifeProposal;
//
// @Embedded
// private UserRecorder recorder;
// @Version
// private int version;
//
// public PolicyTermination() {
// super();
// }
//
// public PolicyTermination(LifePolicy lifepolicy) {
//
// this.activedPolicyEndDate = lifepolicy.getActivedPolicyEndDate();
// this.activedPolicyStartDate = lifepolicy.getActivedPolicyStartDate();
// this.policyNo = lifepolicy.getPolicyNo();
// this.customer = lifepolicy.getCustomer();
// this.agent = lifepolicy.getAgent();
// this.branch = lifepolicy.getBranch();
// this.salesPoints = lifepolicy.getSalesPoints();
// this.saleBank = lifepolicy.getSaleBank();
// this.paymentType = lifepolicy.getPaymentType();
// this.approvedBy = lifepolicy.getApprovedBy();
// this.organization = lifepolicy.getOrganization();
// this.saleChannelType = lifepolicy.getSaleChannelType();
// this.policyStatus = PolicyStatus.TERMINATE;
// this.renewalConfirmDate = lifepolicy.getRenewalConfirmDate();
// this.endorsementConfirmDate = lifepolicy.getEndorsementConfirmDate();
// this.coverageDate = lifepolicy.getCoverageDate();
// this.commenmanceDate = lifepolicy.getCommenmanceDate();
// this.periodMonth = lifepolicy.getPeriodOfMonths();
// this.endorsementNo = lifepolicy.getEndorsementNo();
// this.periodMonth = lifepolicy.getPeriodMonth();
// this.delFlag = lifepolicy.isDelFlag();
// this.lifeProposal = lifepolicy.getLifeProposal();
// this.isCoinsuranceApplied = lifepolicy.isCoinsuranceApplied();
// this.isEndorsementApplied = lifepolicy.isEndorsementApplied();
// this.isNonFinancialEndorse = lifepolicy.isNonFinancialEndorse();
// this.lastPaymentTerm = lifepolicy.getLastPaymentTerm();
// this.printCount = lifepolicy.getPrintCount();
// this.totalDiscountAmount = lifepolicy.getTotalDiscountAmount();
// this.currencyRate = lifepolicy.getCurrencyRate();
// this.specialDiscount = lifepolicy.getSpecialDiscount();
//
// if (lifepolicy.getAttachmentList() != null) {
// for (LifePolicyAttachment attachment : lifepolicy.getAttachmentList()) {
// addLifePolicyAttachment(new LifePolicyTerminateAttachment(attachment));
// }
// }
// for (PolicyInsuredPerson insuredPerson :
// lifepolicy.getPolicyInsuredPersonList()) {
// addPolicyInsuredPersonInfo(new PolicyTerminateInsuredPerson(insuredPerson));
// }
//
// }
//
// public void addPolicyInsuredPersonInfo(PolicyTerminateInsuredPerson
// policyInsuredPersonInfo) {
// if (policyInsuredPersonList == null) {
// policyInsuredPersonList = new ArrayList<PolicyTerminateInsuredPerson>();
// }
// policyInsuredPersonInfo.setLifePolicy(this);
// policyInsuredPersonList.add(policyInsuredPersonInfo);
// }
//
// public void addLifePolicyAttachment(LifePolicyTerminateAttachment attachment)
// {
// if (attachmentList == null) {
// attachmentList = new ArrayList<LifePolicyTerminateAttachment>();
// }
// attachment.setLifePolicy(this);
// attachmentList.add(attachment);
// }
//
// public String getId() {
// return id;
// }
//
// public void setId(String id) {
// this.id = id;
// }
//
// public boolean isDelFlag() {
// return delFlag;
// }
//
// public void setDelFlag(boolean delFlag) {
// this.delFlag = delFlag;
// }
//
// public boolean isCoinsuranceApplied() {
// return isCoinsuranceApplied;
// }
//
// public void setCoinsuranceApplied(boolean isCoinsuranceApplied) {
// this.isCoinsuranceApplied = isCoinsuranceApplied;
// }
//
// public boolean isEndorsementApplied() {
// return isEndorsementApplied;
// }
//
// public void setEndorsementApplied(boolean isEndorsementApplied) {
// this.isEndorsementApplied = isEndorsementApplied;
// }
//
// public boolean isNonFinancialEndorse() {
// return isNonFinancialEndorse;
// }
//
// public void setNonFinancialEndorse(boolean isNonFinancialEndorse) {
// this.isNonFinancialEndorse = isNonFinancialEndorse;
// }
//
// public int getLastPaymentTerm() {
// return lastPaymentTerm;
// }
//
// public void setLastPaymentTerm(int lastPaymentTerm) {
// this.lastPaymentTerm = lastPaymentTerm;
// }
//
// public int getPeriodMonth() {
// return periodMonth;
// }
//
// public void setPeriodMonth(int periodMonth) {
// this.periodMonth = periodMonth;
// }
//
// public int getPrintCount() {
// return printCount;
// }
//
// public void setPrintCount(int printCount) {
// this.printCount = printCount;
// }
//
// public double getTotalDiscountAmount() {
// return totalDiscountAmount;
// }
//
// public void setTotalDiscountAmount(double totalDiscountAmount) {
// this.totalDiscountAmount = totalDiscountAmount;
// }
//
// public double getStandardExcess() {
// return standardExcess;
// }
//
// public void setStandardExcess(double standardExcess) {
// this.standardExcess = standardExcess;
// }
//
// public double getSpecialDiscount() {
// return specialDiscount;
// }
//
// public void setSpecialDiscount(double specialDiscount) {
// this.specialDiscount = specialDiscount;
// }
//
// public double getCurrencyRate() {
// return currencyRate;
// }
//
// public void setCurrencyRate(double currencyRate) {
// this.currencyRate = currencyRate;
// }
//
// public String getPolicyNo() {
// return policyNo;
// }
//
// public void setPolicyNo(String policyNo) {
// this.policyNo = policyNo;
// }
//
// public String getEndorsementNo() {
// return endorsementNo;
// }
//
// public void setEndorsementNo(String endorsementNo) {
// this.endorsementNo = endorsementNo;
// }
//
// public Date getCommenmanceDate() {
// return commenmanceDate;
// }
//
// public void setCommenmanceDate(Date commenmanceDate) {
// this.commenmanceDate = commenmanceDate;
// }
//
// public Date getActivedPolicyStartDate() {
// return activedPolicyStartDate;
// }
//
// public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
// this.activedPolicyStartDate = activedPolicyStartDate;
// }
//
// public Date getActivedPolicyEndDate() {
// return activedPolicyEndDate;
// }
//
// public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
// this.activedPolicyEndDate = activedPolicyEndDate;
// }
//
// public Date getCoverageDate() {
// return coverageDate;
// }
//
// public void setCoverageDate(Date coverageDate) {
// this.coverageDate = coverageDate;
// }
//
// public Date getEndorsementConfirmDate() {
// return endorsementConfirmDate;
// }
//
// public void setEndorsementConfirmDate(Date endorsementConfirmDate) {
// this.endorsementConfirmDate = endorsementConfirmDate;
// }
//
// public Date getRenewalConfirmDate() {
// return renewalConfirmDate;
// }
//
// public void setRenewalConfirmDate(Date renewalConfirmDate) {
// this.renewalConfirmDate = renewalConfirmDate;
// }
//
// public PolicyStatus getPolicyStatus() {
// return policyStatus;
// }
//
// public void setPolicyStatus(PolicyStatus policyStatus) {
// this.policyStatus = policyStatus;
// }
//
// public Customer getCustomer() {
// return customer;
// }
//
// public void setCustomer(Customer customer) {
// this.customer = customer;
// }
//
// public SaleChannelType getSaleChannelType() {
// return saleChannelType;
// }
//
// public void setSaleChannelType(SaleChannelType saleChannelType) {
// this.saleChannelType = saleChannelType;
// }
//
// public Organization getOrganization() {
// return organization;
// }
//
// public void setOrganization(Organization organization) {
// this.organization = organization;
// }
//
// public User getApprovedBy() {
// return approvedBy;
// }
//
// public void setApprovedBy(User approvedBy) {
// this.approvedBy = approvedBy;
// }
//
// public Branch getBranch() {
// return branch;
// }
//
// public void setBranch(Branch branch) {
// this.branch = branch;
// }
//
// public SalesPoints getSalesPoints() {
// return salesPoints;
// }
//
// public void setSalesPoints(SalesPoints salesPoints) {
// this.salesPoints = salesPoints;
// }
//
// public PaymentType getPaymentType() {
// return paymentType;
// }
//
// public void setPaymentType(PaymentType paymentType) {
// this.paymentType = paymentType;
// }
//
// public Agent getAgent() {
// return agent;
// }
//
// public void setAgent(Agent agent) {
// this.agent = agent;
// }
//
// public BankBranch getSaleBank() {
// return saleBank;
// }
//
// public void setSaleBank(BankBranch saleBank) {
// this.saleBank = saleBank;
// }
//
// public UserRecorder getRecorder() {
// return recorder;
// }
//
// public void setRecorder(UserRecorder recorder) {
// this.recorder = recorder;
// }
//
// public int getVersion() {
// return version;
// }
//
// public void setVersion(int version) {
// this.version = version;
// }
//
// public List<PolicyTerminateInsuredPerson> getPolicyInsuredPersonList() {
// return policyInsuredPersonList;
// }
//
// public void setPolicyInsuredPersonList(List<PolicyTerminateInsuredPerson>
// policyInsuredPersonList) {
// this.policyInsuredPersonList = policyInsuredPersonList;
// }
//
// public List<LifePolicyTerminateAttachment> getAttachmentList() {
// return attachmentList;
// }
//
// public void setAttachmentList(List<LifePolicyTerminateAttachment>
// attachmentList) {
// this.attachmentList = attachmentList;
// }
//
// public LifeProposal getLifeProposal() {
// return lifeProposal;
// }
//
// public void setLifeProposal(LifeProposal lifeProposal) {
// this.lifeProposal = lifeProposal;
// }
//
// }
