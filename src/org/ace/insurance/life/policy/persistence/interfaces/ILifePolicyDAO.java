package org.ace.insurance.life.policy.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.NotificationCriteria;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.ReceiptNoCriteria;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.LifePolicyCriteria;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.policy.EndorsementLifePolicyPrint;
import org.ace.insurance.life.policy.LPC001;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.life.proposal.LPL002;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.life.UPRReportCriteria;
import org.ace.insurance.report.life.view.UPRReportView;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
import org.ace.insurance.web.manage.life.billcollection.LifePolicyNotificationDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyDAO {
	public void insert(LifePolicy lifePolicy) throws DAOException;
	
	public void insertSportManTravelAbroad(List<SportManTravelAbroad> sportManTravelList) throws DAOException;

	public void update(LifePolicy lifePolicy) throws DAOException;

	public void delete(LifePolicy lifePolicy) throws DAOException;

	public LifePolicy findById(String id) throws DAOException;

	public LifePolicy findByProposalId(String proposalId) throws DAOException;

	public List<LifePolicy> findByReceiptNo(String receiptNo) throws DAOException;

	public List<LifePolicy> findAll() throws DAOException;

	public List<LifePolicy> findAllActiveLifePolicy() throws DAOException;

	public List<LifePolicy> findByDate(Date startDate, Date endDate) throws DAOException;

	public void increasePrintCount(String id) throws DAOException;

	public void updateCommenmanceDate(Date date, String id) throws DAOException;

	public List<LPL002> findByEnquiryCriteria(EnquiryCriteria criteria, List<Product> productList) throws DAOException;

	public List<LifePolicy> findByCustomer(Customer customer) throws DAOException;

	public void updatePaymentDate(String lifePolicyId, Date paymentDate, Date paymentValidDate) throws DAOException;

	public void updateEndorsementStatus(boolean status, String policylId) throws DAOException;

	public List<LifePolicy> findByPolicyId(String policylId) throws DAOException;

	public PolicyInsuredPerson findInsuredPersonByCodeNo(String codeNo) throws DAOException;

	public void updateInsuredPersonStatusByCodeNo(String codeNo, EndorsementStatus status) throws DAOException;

	public void updatePolicyStatusById(String id, PolicyStatus status) throws DAOException;

	public void updatePolicyAttachment(LifePolicy lifePolicy) throws DAOException;

	public void updateBeneficiaryClaimStatusById(String id, ClaimStatus status) throws DAOException;

	public void updateInsuredPersonClaimStatusById(String id, ClaimStatus status) throws DAOException;

	public List<LifePolicySearch> findActiveLifePolicy(LifePolicyCriteria criteria, List<Product> productList) throws DAOException;

	public List<LifePolicySearch> findActiveSportManLifePolicy(LifePolicyCriteria criteria) throws DAOException;

	public LifePolicy findLifePolicyByPolicyNo(String policyNo) throws DAOException;

	public List<LifePolicySearch> findLifePolicyForClaimByCriteria(LifePolicyCriteria criteria) throws DAOException;

	public List<LPC001> findByCriteria(PolicyCriteria criteria, int max) throws DAOException;

	public List<LPC001> findPolicyByCriteria(PolicyCriteria criteria, int max) throws DAOException;

	public List<LifePolicy> findByPageCriteria(PolicyCriteria criteria) throws DAOException;

	public void updateBillCollection(LifePolicy lifePolicy) throws DAOException;

	public List<LifePolicyNotificationDTO> findNotificationLifePolicy(NotificationCriteria criteria, List<String> productList) throws DAOException;

	public List<BillCollectionDTO> findPaidUpPolicyByCriteria(PolicyCriteria criteria) throws DAOException;

	public List<EndorsementLifePolicyPrint> findEndorsementPolicyPrintByLifePolicyNo(String lifePolicyNo) throws DAOException;

	public EndorsementLifePolicyPrint findEndorsementLifePolicyPrintById(String id) throws DAOException;

	public void update(EndorsementLifePolicyPrint endorsementPolicyPrint) throws DAOException;

	public void insert(EndorsementLifePolicyPrint endorsementPolicyPrint) throws DAOException;

	public String findPolicyNoById(String policyId) throws DAOException;

	public List<Payment> findByReceiptNoCriteria(ReceiptNoCriteria criteria, int max) throws DAOException;

	public List<LifePolicy> findPaymentOrderByReceiptNo(String receiptNo, PolicyReferenceType policyReferenceType) throws DAOException;

	public LifePolicy findByLifeProposalId(String lifeProposalId) throws DAOException;

	public void updatePolicyPersonCode(List<PolicyInsuredPerson> personList) throws DAOException;

	public void updatePolicyBeneficiaryCode(List<PolicyInsuredPersonBeneficiaries> beneficiaryList) throws DAOException;

	public List<BillCollectionDTO> findBillCollectionByCriteria(PolicyCriteria criteria) throws DAOException;

	public List<LPC001> findEndorsementByCriteria(PolicyCriteria criteria, int max) throws DAOException;

	public List<SportManTravelAbroad> findSportManAbroadListByInvoiceNo(String invoiceNo) throws DAOException;

	public List<UPRReportView> findUPRReport(UPRReportCriteria criteria) throws DAOException;

	public List<LifePolicySearch> findNonFullPaidDisClaimPolicy(LifePolicyCriteria lifePolicyCriteria);

	public void updateEndorsementStatusAndIssueDate(LifePolicy lifePolicy);

}
