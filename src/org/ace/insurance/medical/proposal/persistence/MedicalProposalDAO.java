package org.ace.insurance.medical.proposal.persistence;

/***************************************************************************************
 * @author Kyaw Myat Htut
 * @Date 2014-6-31
 * @Version 1.0
 * @Purpose This class serves as the DAO to manipulate the <code>Process</code>
 *          object.
 * 
 ***************************************************************************************/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.ProposalStatus;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.medical.proposal.MP001;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.MedicalSurvey;
import org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.mobileforhealth.MedicalProposalDTO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalProposalDAO")
public class MedicalProposalDAO extends BasicDAO implements IMedicalProposalDAO {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateInsPerWithReasonAndApprove(String rejectReason, boolean approved, String id) throws DAOException {
		try {
			Query query = em
					.createQuery("UPDATE MedicalProposalInsuredPerson s SET s.rejectReason =:rejectReason , s.approved =:approved, s.finishedApproved=true WHERE s.id =:id");
			query.setParameter("rejectReason", rejectReason);
			query.setParameter("approved", approved);
			query.setParameter("id", id);
			query.executeUpdate();

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update InsPerWithReasonAndApprove", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 *      #insert(org.ace.insurance.medical.proposal.MedicalProposal)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal insert(MedicalProposal medicalProposal) throws DAOException {
		try {
			em.persist(medicalProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert MedicalProposal", pe);
		}
		return medicalProposal;
	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 *      #update(org.ace.insurance.medical.proposal.MedicalProposal)
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(MedicalProposal medicalProposal) throws DAOException {
		try {
			em.merge(medicalProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MedicalProposal", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 *      #delete(org.ace.insurance.medical.proposal.MedicalProposal)
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(MedicalProposal medicalProposal) throws DAOException {
		try {
			medicalProposal = em.merge(medicalProposal);
			em.remove(medicalProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete MedicalProposal", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 *      #findById(String)
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal findById(String id) throws DAOException {
		MedicalProposal result = null;
		try {
			result = em.find(MedicalProposal.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MedicalProposal", pe);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalProposal> findAll() throws DAOException {
		List<MedicalProposal> result = null;
		try {
			Query q = em.createNamedQuery("MedicalProposal.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MedicalProposal", pe);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCompleteStatus(boolean status, String proposalId) throws DAOException {
		try {
			Query q = em.createNamedQuery("MedicalProposal.updateCompleteStatus");
			q.setParameter("complete", status);
			q.setParameter("id", proposalId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update complete status", pe);
		}

	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertSurvey(MedicalSurvey medicalSurvey) throws DAOException {
		try {
			Query delQuery = em.createQuery("DELETE FROM MedicalSurvey l WHERE l.medicalProposal.id = :medicalProposalId");
			delQuery.setParameter("medicalProposalId", medicalSurvey.getMedicalProposal().getId());
			delQuery.executeUpdate();
			em.persist(medicalSurvey);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Survey", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateInsuPersonMedicalStatus(MedicalProposalInsuredPerson proposalInsuredPerson) throws DAOException {
		try {

			String queryString = "UPDATE MedicalProposalInsuredPerson p SET p.clsOfHealth = :clsOfHealth WHERE p.id = :insuPersonId";
			Query query = em.createQuery(queryString);
			query.setParameter("insuPersonId", proposalInsuredPerson.getId());
			query.executeUpdate();

		} catch (PersistenceException pe) {
			throw translate("Failed to approved InsuredPerson Approbal Info", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateInsuredPersonApprovalInfo(MedicalProposalInsuredPerson proposalInsuredPerson) throws DAOException {
		try {
			em.merge(proposalInsuredPerson);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to approved InsuredPerson Approbal Info", pe);
		}
	}

	/* used for Medical Proposal Enquire (underwritingEnquery.xhtml) */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MP001> findByEnquiryCriteria(EnquiryCriteria criteria) throws DAOException {
		List<Object[]> objArr = null;
		List<MP001> result = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT m.id, m.proposalNo,m.saleChannelType, a.initialId, a.name, ");
			buffer.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)), ");
			buffer.append(" o.name,b.name, SUM(mp.unit),");
			buffer.append(" SUM(COALESCE(mp.premium, 0)),mp.product.id,");
			buffer.append(" m.submittedDate,sp.name");
			buffer.append(" FROM MedicalProposal m JOIN m.medicalProposalInsuredPersonList mp");
			buffer.append(" LEFT OUTER JOIN mp.customer ip");
			buffer.append(" LEFT OUTER JOIN m.agent a");
			buffer.append(" LEFT OUTER JOIN m.salesPoints sp");
			buffer.append(" LEFT OUTER JOIN m.customer c LEFT OUTER JOIN m.branch b LEFT OUTER JOIN m.organization o");
			buffer.append(" WHERE m.proposalNo IS NOT NULL");
			if (criteria.getAgent() != null) {
				buffer.append(" AND m.agent.id = :agentId");
			}
			if (criteria.getStartDate() != null) {
				buffer.append(" AND m.submittedDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				buffer.append(" AND m.submittedDate <= :endDate");
			}
			if (criteria.getCustomer() != null) {
				buffer.append(" AND m.customer.id = :customerId");
			}
			if (criteria.getOrganization() != null) {
				buffer.append(" AND m.organization.id = :organizationId");
			}
			if (criteria.getBranch() != null) {
				buffer.append(" AND m.branch.id = :branchId");
			}
			if (criteria.getSalePoint() != null) {
				buffer.append(" AND sp.id = :salePointId");
			}
			if (criteria.getProduct() != null) {
				buffer.append(" AND mp.product.id = :productId");
			}
			if (criteria.getProposalNo() != null && !criteria.getProposalNo().isEmpty()) {
				buffer.append(" AND m.proposalNo like :proposalNo");
			}
			if (criteria.getProposalType() != null) {
				buffer.append(" AND m.proposalType = :proposalType");
			}
			if (criteria.getSaleChannelType() != null) {
				buffer.append(" AND m.saleChannelType = :saleChannelType");
			}
			if (criteria.getInsuredPersonName() != null) {
				buffer.append(" AND CONCAT(FUNCTION('REPLACE',ip.name.firstName,' ',''),FUNCTION('REPLACE',ip.name.middleName,' ','')");
				buffer.append(",FUNCTION('REPLACE',ip.name.lastName,' ','')) LIKE :insuredPersonName");
			}
			buffer.append(" GROUP BY m.id, m.proposalNo,m.saleChannelType,");
			buffer.append("  a.initialId, a.name,c.initialId, c.name,o.name,");
			buffer.append(" b.name,mp.product.id,m.submittedDate,sp.name");

			/* Executed query */
			Query query = em.createQuery(buffer.toString());
			if (criteria.getAgent() != null) {
				query.setParameter("agentId", criteria.getAgent().getId());
			}
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getCustomer() != null) {
				query.setParameter("customerId", criteria.getCustomer().getId());
			}
			if (criteria.getOrganization() != null) {
				query.setParameter("organizationId", criteria.getOrganization().getId());
			}
			if (criteria.getBranch() != null) {
				query.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getSalePoint() != null) {
				query.setParameter("salePointId", criteria.getSalePoint().getId());
			}
			if (criteria.getProduct() != null) {
				query.setParameter("productId", criteria.getProduct().getId());
			}
			if (criteria.getProposalNo() != null && !criteria.getProposalNo().isEmpty()) {
				query.setParameter("proposalNo", "%" + criteria.getProposalNo() + "%");
			}
			if (criteria.getProposalType() != null) {
				query.setParameter("proposalType", criteria.getProposalType());
			}
			if (criteria.getSaleChannelType() != null) {
				query.setParameter("saleChannelType", criteria.getSaleChannelType());
			}
			if (criteria.getInsuredPersonName() != null) {
				query.setParameter("insuredPersonName", "%" + criteria.getInsuredPersonName().replace(" ", "") + "%");
			}
			objArr = query.getResultList();
			String id = null;
			String proposalNo = null;
			SaleChannelType saleChannelType = null;
			String branch = null;
			String salePerson = null;
			String customer = null;
			String organization = null;
			String salePoint = null;
			Long unit = null;
			Double totalPremium = null;
			String productId = null;
			Date submittedDate = null;
			for (Object[] arr : objArr) {
				salePerson = "";
				id = (String) arr[0];
				proposalNo = (String) arr[1];
				saleChannelType = (SaleChannelType) arr[2];
				if (arr[3] != null) {
					salePerson = arr[3] + ((Name) arr[4]).getFullName();
				}
				customer = (String) arr[5];
				organization = (String) arr[6];
				branch = (String) arr[7];
				unit = (Long) arr[8];
				totalPremium = (Double) arr[9];
				productId = (String) arr[10];
				submittedDate = (Date) arr[11];
				salePoint = (String) arr[12];
				result.add(new MP001(id, proposalNo, saleChannelType, salePerson, customer, organization, branch, unit, totalPremium.doubleValue(), productId, submittedDate, "",
						"", salePoint));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeProposal by EnquiryCriteria : ", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalSurvey findSurveyByProposalId(String proposalId) throws DAOException {
		MedicalSurvey result = null;
		try {
			logger.debug("findSurveyByProposalId() method has been started.");
			Query query = em.createQuery("SELECT l FROM MedicalSurvey l WHERE l.medicalProposal.id = :id");
			query.setParameter("id", proposalId);
			result = (MedicalSurvey) query.getSingleResult();
			em.flush();
			logger.debug("findSurveyByProposalId() method has been started.");
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Proposal Survey : ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestionAnswer> findSurveyQuestionAnswerByProposalId(String proposalId) throws DAOException {
		List<SurveyQuestionAnswer> surveyQuestionAnswerList = null;
		try {
			Query q = em.createQuery("SELECT p.surveyQuestionAnswerList FROM MedicalProposal m  JOIN m.medicalProposalInsuredPersonList p  where m.id =:proposalId ");
			q.setParameter("proposalId", proposalId);
			surveyQuestionAnswerList = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Survey Question Answer : ", pe);
		}
		return surveyQuestionAnswerList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMedicalProposalCompleteStatus(String proposalId) throws DAOException {
		try {
			Query query = em.createNamedQuery("MedicalProposal.updateCompleteStatus");
			query.setParameter("id", proposalId);
			query.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Medical Proposal Complete Status", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSpecialDiscount(String medicalProposalId, double specialDiscount) throws DAOException {
		try {
			Query query = em.createQuery("UPDATE MedicalProposal p SET p.specialDiscount = :specialDiscount WHERE p.id = :medicalProposalId");
			query.setParameter("medicalProposalId", medicalProposalId);
			query.setParameter("specialDiscount", specialDiscount);
			query.executeUpdate();
		} catch (PersistenceException pe) {
			throw translate("Failed to update specialDiscount", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateNCBAmount(String medicalProposalId, double ncbAmount) throws DAOException {
		try {
			Query query = em.createQuery("UPDATE MedicalProposal p SET p.totalNcbAmount = :totalNcbAmount WHERE p.id = :medicalProposalId");
			query.setParameter("medicalProposalId", medicalProposalId);
			query.setParameter("totalNcbAmount", ncbAmount);
			query.executeUpdate();
		} catch (PersistenceException pe) {
			throw translate("Failed to update specialDiscount", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProposalStatus(ProposalStatus status, String proposalId) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE MedicalProposal f SET f.proposalStatus = :status WHERE f.id = :id");
			q.setParameter("status", status);
			q.setParameter("id", proposalId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Proposal status", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMedicalProposalAttachment(MedicalProposal medicalProposal) throws DAOException {
		try {
			em.merge(medicalProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Medical Proposal", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalProposalDTO> findMedicalProposal() throws DAOException {
		List<MedicalProposalDTO> medicalproposallist=new ArrayList<>();
		List<Object[]> objectList = new ArrayList<>();
		try {
		Query q = em.createNativeQuery("select p.proposalNo as proposalNo,p.SUBMITTEDDATE as submitteddate,p.SALECHANNELTYPE as salechanneltype\r\n" + 
				" from MEDICALPROPOSAL_TEMP p \r\n" + 
				" where status='false' ");
		objectList= q.getResultList();
		for(Object[] object: objectList) {
			MedicalProposalDTO medicalProposal = new MedicalProposalDTO();
			medicalProposal.setProposalNo(String.valueOf(object[0]));	
			medicalProposal.setSubmitteddate((Date)(object[1]));
			medicalProposal.setSalechanneltype(String.valueOf(object[2]));
			medicalproposallist.add(medicalProposal);
		}
		em.flush();
	}catch (PersistenceException pe) {
		throw translate("Failed to find Medical Proposal", pe);
		
	}
	return medicalproposallist;

}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal findByProposalNoFromTerm(String proposalNo) throws DAOException {
		MedicalProposal result = null;
		try {
			Query query = em.createNativeQuery("SELECT * FROM MedicalProposal_Temp  WHERE proposalNo = :proposalNo");
			query.setParameter("proposalNo", proposalNo);
			result = (MedicalProposal) query.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeProposal by Proposal No : " + proposalNo, pe);
		}
		return result;
	}
	
}

