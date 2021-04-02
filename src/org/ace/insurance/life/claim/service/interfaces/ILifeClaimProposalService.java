package org.ace.insurance.life.claim.service.interfaces;

import java.util.List;
import java.util.Map;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.LCL001;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeClaimSurvey;
import org.ace.insurance.life.claim.LifeDisabilityPaymentCriteria;
import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.report.agent.ClaimMedicalFeesCriteria;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.insurance.report.claim.LifeClaimMonthlyReportDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;

public interface ILifeClaimProposalService {

	public void updateLifeClaimProposal(LifeClaimProposal claimProposal);

	public void deleteLifeClaimProposal(LifeClaimProposal claimProposal);

	public LifeClaimProposal findLifeClaimProposalById(String id);

	public void addNewLifeClaimSurvey(LifeClaimSurvey lifeClaimSurvey, WorkFlowDTO workFlow);

	public void approveLifeClaim(LifeClaimProposal claimProposal, WorkFlowDTO workFlow);

	public void informLifeClaim(ClaimAcceptedInfo claimAcceptedInfo, LifeClaimProposal lifeClaimProposal, WorkFlowDTO workFlow);

	public void confirmLifeClaimPropsal(LifeClaimProposal claimProposal, PaymentDTO paymentDTO, WorkFlowDTO workFlow);

	public void paymentLifeClaimProposal(LifeClaimProposal claimProposal, List<Payment> paymentList, Branch userBranch, WorkFlowDTO workFlowDTO);

	public List<DisabilityLifeClaim> findDisabilityLifeClaimByLifeClaimProposalNo(LifeDisabilityPaymentCriteria criteria);

	public void confirmLifeDisabilityClaim(DisabilityLifeClaim disabilityClaim, PaymentDTO payment, WorkFlowDTO workFlow);

	void addNewLifeClaimProposal(LifePolicyClaim policyClaim, LifeClaimProposal claimProposal, LifeClaimNotification lifeClaimNotification, WorkFlowDTO workFlow,
			Map<String, String> claimAttMap, String proposal_DIR);

	public void rejectLifeClaimPropsal(LifeClaimProposal claimProposal, WorkFlowDTO workFlow);

	public double findTotalDisabilityClaimPercentageByClaimPersonId(String id, String policyNo);

	public void issueLifeClaimPolicy(LifeClaimProposal lifeClaimProposal);

	public List<LCL001> findLifeClaimProposalByCriteria(LCL001 criteria);

	public List<LifeClaimProposal> findByLifepolicyId(String lifePolicyId);

	public List<LifeClaimMonthlyReportDTO> findLifeClaimByCriteria(LCL001 criteria);

	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeSanction(ClaimMedicalFeesCriteria claimMedicalFeesCriteria);

	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeInvoice(ClaimMedicalFeesCriteria claimMedicalFeesCriteria);

	public void invoicedClaimMedicalFees(List<LifeClaimMedicalFeeDTO> meedicalFeesList, WorkFlowDTO workFlow, PaymentChannel paymentChannel, Bank bank, String bankAccountNo);

	public void paymentMedicalFeesInvoice(List<ClaimMedicalFees> claimMedFeesList, SalesPoints salePoint, Branch branch);

	public void sanctionClaimMedicalFees(List<LifeClaimMedicalFeeDTO> meedicalFeesList, WorkFlowDTO workFlow, PaymentChannel paymentChannel, Bank bank, String bankAccountNo);

}
