package org.ace.insurance.medical.proposal.persistence.interfaces;

/***************************************************************************************
 * @author Kyaw Myat Htut
 * @Date 2014-6-31
 * @Version 1.0
 * @Purpose This class serves as the DAO to manipulate the <code>Process</code>
 *          object.
 * 
 ***************************************************************************************/
import java.util.List;

import org.ace.insurance.common.ProposalStatus;
import org.ace.insurance.medical.proposal.MP001;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.MedicalSurvey;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.mobileforhealth.MedicalProposalDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalProposalDAO {

	/**
	 * 
	 * @param MedicalProposal
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose update Process data into DB.
	 */
	public void update(MedicalProposal medicalProposal) throws DAOException;

	/**
	 * 
	 * @param MedicalProposal
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose delete Process data from DB.
	 */
	public void delete(MedicalProposal medicalProposal) throws DAOException;

	/**
	 * 
	 * @param String
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find Process data by medicalProposal Id from DB
	 */
	public MedicalProposal findById(String id) throws DAOException;

	/**
	 * 
	 * @param
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find all process data from DB
	 */
	public List<MedicalProposal> findAll() throws DAOException;

	/**
	 * 
	 * @param boolean
	 *            , String
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose updateCompleteStatus Process data into DB.
	 */
	public void updateCompleteStatus(boolean status, String proposalId) throws DAOException;

	/**
	 * 
	 * @param MedicalProposal
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Insert Process data into DB.
	 */
	public MedicalProposal insert(MedicalProposal medicalProposal) throws DAOException;

	/**
	 * 
	 * @param MedicalProposal
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Insert Survey Process data into DB.
	 */
	public void insertSurvey(MedicalSurvey medicalSurvey) throws DAOException;

	/**
	 * 
	 * @param MedicalProposal
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Update Insuredperson status Process data into DB.
	 */
	public void updateInsuPersonMedicalStatus(MedicalProposalInsuredPerson proposalInsuredPerson) throws DAOException;

	public void updateInsuredPersonApprovalInfo(MedicalProposalInsuredPerson proposalInsuredPerson) throws DAOException;

	public List<MP001> findByEnquiryCriteria(EnquiryCriteria criteria) throws DAOException;

	public MedicalSurvey findSurveyByProposalId(String proposalId) throws DAOException;

	public List<SurveyQuestionAnswer> findSurveyQuestionAnswerByProposalId(String proposalId) throws DAOException;

	public void updateInsPerWithReasonAndApprove(String rejectReason, boolean approved, String id) throws DAOException;

	public void updateSpecialDiscount(String medicalProposalId, double specialDiscount) throws DAOException;

	public void updateProposalStatus(ProposalStatus status, String proposalId) throws DAOException;

	public void updateNCBAmount(String medicalProposalId, double ncbAmount) throws DAOException;

	public void updateMedicalProposalAttachment(MedicalProposal medicalproposal) throws DAOException;
	
	public List<MedicalProposalDTO> findMedicalProposal() throws DAOException;
	
	public MedicalProposal findByProposalNoFromTerm(String medicalProposalNo)throws DAOException;

}
