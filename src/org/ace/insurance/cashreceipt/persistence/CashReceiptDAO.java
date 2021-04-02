package org.ace.insurance.cashreceipt.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.cashreceipt.CashReceiptCriteria;
import org.ace.insurance.cashreceipt.CashReceiptDTO;
import org.ace.insurance.cashreceipt.persistence.interfaces.ICashReceiptDAO;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.user.User;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CashReceiptDAO")
public class CashReceiptDAO extends BasicDAO implements ICashReceiptDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CashReceiptDTO> findFireConfirmationList(CashReceiptCriteria criteria, User user) throws DAOException {
		List<CashReceiptDTO> result = null;
		String innerQuery = "SELECT SUM(p.proposedSumInsured) FROM FireProductInfo p WHERE p.buildingInfo.fireProposal.id = :id";
		result = findConfirmationList("FireProposal", criteria, user, innerQuery);
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CashReceiptDTO> findLifeConfirmationList(CashReceiptCriteria criteria, User user) throws DAOException {
		List<CashReceiptDTO> result = null;
		String innerQuery = "SELECT SUM(ip.proposedSumInsured) FROM ProposalInsuredPerson ip WHERE ip.lifeProposal.id = :id";
		result = findConfirmationList("LifeProposal", criteria, user, innerQuery);
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CashReceiptDTO> findMotorConfirmationList(CashReceiptCriteria criteria, User user) throws DAOException {
		List<CashReceiptDTO> result = null;
		String innerQuery = "SELECT SUM(v.proposedSumInsured) FROM ProposalVehicle v WHERE v.motorProposal.id = :id";
		result = findConfirmationList("MotorProposal", criteria, user, innerQuery);
		return result;
	}

	private List<CashReceiptDTO> findConfirmationList(String proposalEntity, CashReceiptCriteria criteria, User user, String innerQuery) throws DAOException {
		List<CashReceiptDTO> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT c.id, c.proposalNo, c.customer, c.organization, c.submittedDate, w.recorder.createdDate" + " FROM " + proposalEntity + " c, WorkFlow w"
					+ " WHERE c.id = w.referenceNo AND w.workflowTask = :workflowTask" + " AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId");
			if (criteria.getStartDate() != null) {
				buffer.append(" AND c.submittedDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				buffer.append(" AND c.submittedDate <= :endDate");
			}

			Query query = em.createQuery(buffer.toString());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			query.setParameter("workflowTask", WorkflowTask.CONFIRMATION);
			query.setParameter("referenceType", criteria.getReferenceType());
			query.setParameter("responsiblePersonId", user.getId());
			List<Object> objList = query.getResultList();
			result = new ArrayList<CashReceiptDTO>();

			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;

				String id = (String) objArray[0];
				String proposalNo = (String) objArray[1];
				Customer customer = (Customer) objArray[2];
				Organization organization = (Organization) objArray[3];
				String customerName = customer != null ? customer.getFullName() : organization.getName();
				Date submittedDate = (Date) objArray[4];
				Date createdDate = (Date) objArray[5];

				Query query2 = em.createQuery(innerQuery);
				query2.setParameter("id", id);
				double sumInsured = (Double) query2.getSingleResult();
				result.add(new CashReceiptDTO(id, proposalNo, customerName, submittedDate, createdDate, sumInsured));
			}
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find confirmationlist", pe);
		}
		RegNoSorter<CashReceiptDTO> regNoSorter = new RegNoSorter<CashReceiptDTO>(result);
		return regNoSorter.getSortedList();
	}
}
