package org.ace.insurance.medical.proposal.service.interfaces;

/***************************************************************************************
 * @author Kyaw Myat Htut
 * @Date 2014-6-31
 * @Version 1.0
 * @Purpose 
 * 
 *    
 ***************************************************************************************/
import java.util.List;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.proposal.MP001;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalSurvey;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.mobileforhealth.MedicalProposalDTO;

public interface IMedicalProposalService {
	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @return
	 * @Purpose calculate all of the total premium (proposal premium and addOn
	 *          premium) of MedicalProposal (based on key factor)
	 */
	public MedicalProposal calculatePremium(MedicalProposal medicalProposal);

	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @return{@link MedicalProposal} instance
	 * @Purpose to add new MedicalProposal at Underwriting
	 */
	public MedicalProposal addNewMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO);

	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @return{@link MedicalProposal} instance
	 * @Purpose to update MedicalProposal at Confirm Edit, Enquire
	 */

	public MedicalProposal updateMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO);

	/****
	 * 
	 * @param {@link
	 * 			MedicalSurvey} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @Purposse to insert MedicalSurvey and update MedicalProposal for
	 *           surveyQuestion at Survey
	 */
	public void addNewSurvey(MedicalSurvey medicalSurvey, WorkFlowDTO workFlowDTO);

	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @Purpose to update MedicalPropoal at Approve
	 */
	public void approveMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO);

	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @param {@link
	 * 			AcceptedInfo} instance
	 * @Purpose to insert AcceptedInfo and update discountPercent in
	 *          MedicalPropsoal at Inform
	 */
	public void informProposal(String medicalProposalId, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo);

	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @param {@link
	 * 			PaymentDTO} instance
	 * @return
	 * @Purpose to update MedicalProposal, add MedicalPolicy, add Payment at
	 *          Confirm
	 */
	public List<Payment> confirmMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO);

	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @Purpose to change deny status of MedicalProposal at Confirm
	 */
	public void rejectMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO);

	/**
	 * 
	 * @param {@link
	 * 			MedicalProposal} instance
	 * @param {@link
	 * 			WorkFlowDTO} instance
	 * @param {@link
	 * 			Payment} instance List
	 * @param {@link
	 * 			Branch} instance
	 * @Purpose to activate MedicalPolicy, Payment and to add AgentCommission,
	 *          TLF, update Customer for activePolicyCount at Payment
	 */
	public void paymentMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO, List<Payment> paymentList, Branch userBranch);

	/**
	 * 
	 * @param {@link
	 * 			MedicalPolicy} instance
	 * @Purpose to change complete status of MedicalPropsoal at Issue
	 */
	public void issueMedicalProposal(String medicalProposalId);

	/**
	 * @param id
	 * @return
	 * @Purpose to find instance of MedicalProposal by proposalId
	 */
	public MedicalProposal findMedicalProposalById(String proposalId);

	/**
	 * @param {@link
	 * 			EnquiryCriteria}
	 * @return {@link List<MP001>}
	 * @Purpose to find {@link List<MP001>} by {@link EnquiryCriteria} at
	 *          Proposal Enquire
	 */
	public List<MP001> findAllMedicalPolicy(EnquiryCriteria enquiryCriteria);

	/**
	 * 
	 * @param {@link
	 * 			String}
	 * @return {@link MedicalSurvey}
	 * @Purpose to find MedicalSurvey by propsoalId at Proposal Enquire
	 */
	public MedicalSurvey findMedicalSurveyByProposalId(String id);

	/****
	 * @param {@link
	 * 			MedicalPolicy},{@link WorkFlowDTO}
	 * @purpose to delete Payment, TLF, MedicalPolicy at WorkFlow Changer
	 */
	public void deletePayment(MedicalPolicy medicalPolicy, WorkFlowDTO workFlowDTO);

	public void updateMedicalProposalAttachment(MedicalProposal medicalProposal);
	
	
	

   public	List<MedicalProposalDTO> findMedicalProposal();
   
   public MedicalProposal findLifeProposalByProposalNoFromTerm(String medicalproposalNo);

}
