package org.ace.insurance.medical.surveyquestion.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.medical.productprocess.ProductProcessCriteriaDTO;
import org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO;
import org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This class serves as the Service Layer to manipulate the
 *          <code>SurveyQuestion</code> object.
 * 
 ***************************************************************************************/

@Service(value = "SurveyQuestionService")
public class SurveyQuestionService extends BaseService implements ISurveyQuestionService {

	@Resource(name = "SurveyQuestionDAO")
	private ISurveyQuestionDAO surveyQuestionDAO;

	@Resource(name = "ProductProcessService")
	private IProductProcessService productProcessService;

	/**
	 * @see org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService
	 *      #addNewSurveyQuestion(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSurveyQuestion(SurveyQuestion surveyQuestion) {
		try {
			surveyQuestionDAO.insert(surveyQuestion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SurveyQuestion", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService
	 *      #updateSurveyQuestion(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSurveyQuestion(SurveyQuestion surveyQuestion) {
		try {
			surveyQuestionDAO.update(surveyQuestion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a SurveyQuestion", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService
	 *      #deleteSurveyQuestion(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSurveyQuestion(SurveyQuestion surveyQuestion) {
		try {
			surveyQuestionDAO.delete(surveyQuestion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a SurveyQuestion", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService
	 *      #findAllSurveyQuestion(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 * @return List of SurveyQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestion> findAllSurveyQuestion() {
		List<SurveyQuestion> result = null;
		try {
			result = surveyQuestionDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of SurveyQuestion)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService
	 *      #findSurveyQuestionById(String)
	 * @return SurveyQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public SurveyQuestion findSurveyQuestionById(String surveyQuestionId) {
		SurveyQuestion result = null;
		try {
			result = surveyQuestionDAO.findSurveyQuestionById(surveyQuestionId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SurveyQuestion)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService
	 *      #findSurveyQuestionBypp(String)
	 * @return List of SurveyQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestion> findSurveyQuestionBypp(String productProcessId) {
		List<SurveyQuestion> result = null;
		try {
			result = surveyQuestionDAO.findSurveyQuestionBypp(productProcessId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SurveyQuestion by productProcess id)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService
	 *      #findProductProcessQuestionLinkList(String, String,
	 *      BuildingOccupationType)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcessQuestionLink> findProductProcessQuestionLinkList(String productId, String processId, BuildingOccupationType buildingOccupationType) {
		List<ProductProcessQuestionLink> result = null;
		try {
			result = surveyQuestionDAO.findPProQueByPPId(productId, processId, buildingOccupationType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find find ProductProcessQuestionLink By ProductProcess'Id ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcessQuestionLink> findPProQueByPPId(String productId, String proposalProcessId, ProductProcessCriteriaDTO dto) {
		List<ProductProcessQuestionLink> result = null;
		try {
			result = surveyQuestionDAO.findPProQueByPPId(productId, proposalProcessId, dto);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find find ProductProcessQuestionLink By ProductProcess'Id ", e);
		}
		return result;
	}

}
