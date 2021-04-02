package org.ace.insurance.medical.surveyquestion.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.medical.productprocess.ProductProcessCriteriaDTO;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This class serves as the DAO to manipulate the
 *          <code>SurveyQuestion</code> object.
 * 
 * 
 ***************************************************************************************/

@Repository("SurveyQuestionDAO")
public class SurveyQuestionDAO extends BasicDAO implements ISurveyQuestionDAO {

	/**
	 * @see org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO
	 *      #insert(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(SurveyQuestion surveyQuestion) throws DAOException {
		try {
			em.persist(surveyQuestion);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert SurveyQuestion", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO
	 *      #update(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(SurveyQuestion surveyQuestion) throws DAOException {
		try {
			em.merge(surveyQuestion);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update SurveyQuestion", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO
	 *      #delete(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(SurveyQuestion surveyQuestion) throws DAOException {
		try {
			surveyQuestion = em.merge(surveyQuestion);
			em.remove(surveyQuestion);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update SurveyQuestion", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO
	 *      #findAll()
	 * @return List of SurveyQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestion> findAll() throws DAOException {
		List<SurveyQuestion> result = null;
		try {
			Query q = em.createNamedQuery("SurveyQuestion.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of SurveyQuestion", pe);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO
	 *      #findSurveyQuestionById(String)
	 * @return SurveyQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public SurveyQuestion findSurveyQuestionById(String surveyQuestionId) throws DAOException {
		SurveyQuestion result = null;
		try {
			Query q = em.createNamedQuery("SurveyQuestion.findById");
			q.setParameter("id", surveyQuestionId);
			result = (SurveyQuestion) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of SurveyQuestion", pe);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO
	 *      #findSurveyQuestionById(String)
	 * @return List of SurveyQuestion
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcessQuestionLink> findPProQueByPPId(String productId, String processId, BuildingOccupationType buildingOccupationType) throws DAOException {
		List<ProductProcessQuestionLink> result = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT distinct(ppq) FROM ProductProcessQuestionLink ppq INNER JOIN ppq.productProcess pp INNER JOIN ppq.surveyQuestion sq  ");
			sb.append("WHERE pp.product.id = :productId AND pp.process.id = :processId AND sq.deleteFlag = FALSE ");
			sb.append("AND pp.activeStatus = org.ace.insurance.medical.productprocess.ActiveStatus.ACTIVATE ");
			if (buildingOccupationType != null)
				sb.append("AND pp.buildingOccupationType = :buildingOccupationType");
			Query query = em.createQuery(sb.toString());
			query.setParameter("productId", productId);
			query.setParameter("processId", processId);
			if (buildingOccupationType != null)
				query.setParameter("buildingOccupationType", buildingOccupationType);
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Faield to find find ProductProcessQuestionLink By Product'Id, Process'Id and buildingOccupationType", pe);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO
	 *      #findSurveyQuestionBypp(String)
	 * @return List of SurveyQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestion> findSurveyQuestionBypp(String productProcessId) throws DAOException {
		List<SurveyQuestion> result = null;
		try {
			Query query = em.createQuery(" SELECT que FROM SurveyQuestion que INNER JOIN que.productProcessQuestionLinkList ppq WHERE ppq.productProcess.id = :ppId ");
			query.setParameter("ppId", productProcessId);
			result = query.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Faield to find find ProductProcessQuestionLink By ProductProcess'Id ", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcessQuestionLink> findPProQueByPPId(String productName, String processName, ProductProcessCriteriaDTO dto) throws DAOException {
		List<ProductProcessQuestionLink> result = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT distinct(ppq) FROM ProductProcessQuestionLink ppq inner join ppq.productProcess pp inner join ppq.surveyQuestion sq  ");
			sb.append(
					"where pp.product.id = :productId and  pp.process.id = :processId and sq.deleteFlag = FALSE and pp.activeStatus = org.ace.insurance.medical.productprocess.ActiveStatus.ACTIVATE");
			if (dto.getStudentAgeType() != null) {
				sb.append(" And pp.productProcessCriteria.studentAgeType=:studentAgeType");
			} else if (dto.getAge() != 0) {
				sb.append(" And pp.productProcessCriteria.minAge<=:age And pp.productProcessCriteria.maxAge>=:age");
			} else if (dto.getSumInsured() != 0) {
				sb.append(" And pp.productProcessCriteria.sumInsured<=:sumInsured");
			}
			Query query = em.createQuery(sb.toString());
			query.setParameter("productId", productName);
			query.setParameter("processId", processName);
			if (dto.getStudentAgeType() != null) {
				query.setParameter("studentAgeType", dto.getStudentAgeType());
			} else if (dto.getAge() != 0) {
				query.setParameter("age", dto.getAge());
			} else if (dto.getSumInsured() != 0) {
				query.setParameter("sumInsured", dto.getSumInsured());
			}
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Faield to find find ProductProcessQuestionLink By Product'Id and Process'Id ", pe);
		}
		return result;
	}
}
