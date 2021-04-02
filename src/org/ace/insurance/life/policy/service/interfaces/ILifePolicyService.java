package org.ace.insurance.life.policy.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.NotificationCriteria;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.ReceiptNoCriteria;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.LifePolicyCriteria;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.policy.EndorsementLifePolicyPrint;
import org.ace.insurance.life.policy.LPC001;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.life.proposal.LPL002;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.life.UPRReportCriteria;
import org.ace.insurance.report.life.view.UPRReportView;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
import org.ace.insurance.web.manage.life.billcollection.LifePolicyNotificationDTO;
import org.ace.java.component.SystemException;

public interface ILifePolicyService {

	public void addNewLifePolicy(LifePolicy lifePolicy) throws SystemException;

	public List<Payment> addNewSportManTravelAbroad(List<SportManTravelAbroad> sportManTravelList, WorkFlowDTO workFlowDTO);

	public void deleteLifePolicy(LifePolicy lifePolicy);

	public LifePolicy activateLifePolicy(LifeProposal lifeProposal);

	public void updateLifePolicy(LifePolicy lifePolicy);

	public void overwriteLifePolicy(LifePolicy lifePolicy);

	public LifePolicy findLifePolicyById(String id);

	public List<LifePolicy> findLifePolicyByReceiptNo(String receiptNo);

	public List<LifePolicy> findAllLifePolicy();

	public List<LifePolicy> findAllActiveLifePolicy();

	public List<LifePolicy> findByDate(Date startDate, Date endDate);

	public void increaseLifePolicyPrintCount(String lifeProposalId);

	public void updateLifePolicyCommenmanceDate(Date date, String lifeProposalId);

	public List<LPL002> findLifePolicyByEnquiryCriteria(EnquiryCriteria criteria, List<Product> productList);

	public List<LifePolicy> findByCustomer(Customer customer);

	public void updatePaymentDate(String lifePolicyId, Date paymentDate, Date paymentValidDate);

	public void updateEndorsementStatus(Boolean status, LifePolicy lifePolicy);

	public List<LifePolicy> findLifePolicyByPolicyId(String policyId);

	public PolicyInsuredPerson findInsuredPersonByCodeNo(String codeNo);

	public void updateInsuredPersonStatusByCodeNo(String codeNo, EndorsementStatus status);

	public void updatePolicyStatusById(String id, PolicyStatus status);

	public void terminateLifePolicy(LifePolicy lifePolicy);

	public void updatePolicyAttachment(LifePolicy lifePolicy);

	public void updateBeneficiaryClaimStatusById(String id, ClaimStatus status);

	public List<LifePolicySearch> findActiveLifePolicy(LifePolicyCriteria criteria, List<Product> productList);

	public List<LifePolicySearch> findActiveSportManLifePolicy(LifePolicyCriteria criteria);

	public LifePolicy findLifePolicyByPolicyNo(String policyNo);

	public List<LifePolicySearch> findLifePolicyForClaimByCriteria(LifePolicyCriteria criteria);

	public List<LPC001> findLifePolicyByPolicyCriteria(PolicyCriteria criteria, int max);

	public List<LPC001> findLifePolicyCloneByPolicyCriteria(PolicyCriteria criteria, int max);

	public List<LifePolicy> findLifePolicyByPageCriteria(PolicyCriteria criteria);

	public void activateBillCollection(LifePolicy lifePolicy);

	public List<LifePolicyNotificationDTO> findNotificationLifePolicy(NotificationCriteria criteria, List<String> productList);

	public List<EndorsementLifePolicyPrint> findEndorsementPolicyPrintByLifePolicyNo(String lifePolicyNo);

	public EndorsementLifePolicyPrint findEndorsementPolicyPrintById(String id);

	public void updateEndorsementLifePolicyPrint(EndorsementLifePolicyPrint endorsementPolicyPrint);

	public void addNewEndorsementLifePolicyPrint(EndorsementLifePolicyPrint endorsementPolicyPrint);

	public List<Payment> findLifePolicyByReceiptNoCriteria(ReceiptNoCriteria criteria, int max);

	public List<LifePolicy> findLifePolicyPOByReceiptNo(String receiptNo, PolicyReferenceType policyReferenceType);

	public LifePolicy findLifePolicyByLifeProposalId(String lifeProposaId);

	public List<BillCollectionDTO> findBillCollectionByCriteria(PolicyCriteria criteria);

	public List<BillCollectionDTO> findLifePaidUpProposalByCriteria(PolicyCriteria criteria);

	public LifePolicy findPolicyByProposalId(String lifeProposaId);

	public List<LPC001> findByPolicyCriteria(PolicyCriteria criteria, int max);

	public void updateLifePolicyAttachment(LifePolicy lifePolicy);

	public List<SportManTravelAbroad> findSportManAbroadListByInvoiceNo(String invoiceNo);

	public List<UPRReportView> findUPRReport(UPRReportCriteria criteria);

	public List<LifePolicySearch> findNonFullPaidDisClaimPolicy(LifePolicyCriteria lifePolicyCriteria);

	public void updateEndorsementStatusAndIssueDate(LifePolicy lifePolicy);

	public void updateLifePolicyEndorsement(LifePolicy lifePolicy);

}
