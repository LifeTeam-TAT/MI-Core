package org.ace.insurance.payment.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.payment.AC001;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.persistence.interfacs.IAgentCommissionDAO;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.web.manage.agent.AgentEnquiryCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AgentCommissionDAO")
public class AgentCommissionDAO extends BasicDAO implements IAgentCommissionDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Agent> findAgentByCommissionCriteria(AgentEnquiryCriteria agentEnquiryCriteria) throws DAOException {
		List<Agent> agentList = null;
		try {
			StringBuffer filterString = new StringBuffer();

			if (agentEnquiryCriteria.getStartDate() != null) {
				filterString.append(" ac.commissionStartDate >= :startDate ");
			}

			if (agentEnquiryCriteria.getEndDate() != null) {
				if (filterString.length() > 0) {
					filterString.append("AND");
				}
				filterString.append(" ac.commissionStartDate <= :endDate ");
			}

			if (agentEnquiryCriteria.getAgent() != null) {
				if (filterString.length() > 0) {
					filterString.append("AND");
				}
				filterString.append(" ac.agent.id = :id ");
			}

			/* Executed query */
			Query query = em.createQuery("Select distinct(ac.agent) from AgentCommission ac Where ac.commissionStartDate is not null AND " + filterString.toString());

			if (agentEnquiryCriteria.getStartDate() != null) {
				query.setParameter("startDate", agentEnquiryCriteria.getStartDate());
			}

			if (agentEnquiryCriteria.getEndDate() != null) {
				query.setParameter("endDate", agentEnquiryCriteria.getEndDate());
			}

			if (agentEnquiryCriteria.getAgent() != null) {
				query.setParameter("id", agentEnquiryCriteria.getAgent().getId());
			}

			agentList = query.getResultList();
			em.flush();
		} catch (NoResultException nre) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Agent Commission by Criteria : ", pe);
		}
		return agentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AC001> findAgentCommissionByAgent(AgentEnquiryCriteria agentEnquiryCriteria) throws DAOException {
		List<AC001> resultList = null;
		try {
			StringBuffer filterString = new StringBuffer();

			if (agentEnquiryCriteria.getStartDate() != null) {
				filterString.append(" ac.commissionStartDate >= :startDate ");
			}

			if (agentEnquiryCriteria.getEndDate() != null) {
				if (filterString.length() > 0) {
					filterString.append("AND");
				}
				filterString.append(" ac.commissionStartDate <= :endDate ");
			}

			if (agentEnquiryCriteria.getSelectedAgent() != null) {
				if (filterString.length() > 0) {
					filterString.append("AND");
				}
				filterString.append(" ac.agent.id = :id ");
			}

			/* Executed query */
			Query query = em.createQuery(
					"Select NEW org.ace.insurance.payment.AC001(mp.customer.name.firstName,mp.policyNo,ac.chalanNo,ac.commission) FROM AgentCommission ac JOIN Payment p on ac.chalanNo=p.chalanNo JOIN MedicalPolicy mp on p.referenceNo=mp.id Where ac.commissionStartDate is not null AND "
							+ filterString.toString());

			if (agentEnquiryCriteria.getStartDate() != null) {
				query.setParameter("startDate", agentEnquiryCriteria.getStartDate());
			}

			if (agentEnquiryCriteria.getEndDate() != null) {
				query.setParameter("endDate", agentEnquiryCriteria.getEndDate());
			}

			if (agentEnquiryCriteria.getSelectedAgent() != null) {
				query.setParameter("id", agentEnquiryCriteria.getSelectedAgent().getId());
			}

			resultList = query.getResultList();
			em.flush();
		} catch (NoResultException nre) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Agent Commission by agent; ", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentCommission findAgentCommissionByChalanNo(String challanNo) throws DAOException {
		AgentCommission result = null;
		try {
			Query q = em.createNamedQuery("AgentCommission.findByChallanNo");
			q.setParameter("chalanNo", challanNo);
			result = (AgentCommission) q.getSingleResult();
			em.flush();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AgentCommission", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentCommission findAgentCommissionByPolicyId(String policyId) throws DAOException {
		AgentCommission result = null;
		try {
			Query q = em.createNamedQuery("AgentCommission.findByReferenceNo");
			q.setParameter("referenceNo", policyId);
			result = (AgentCommission) q.getSingleResult();
			em.flush();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AgentCommission", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentCommission findAgentCommissionByReferenceNo(String referenceNo) throws DAOException {
		AgentCommission result = null;
		try {
			Query q = em.createNamedQuery("AgentCommission.findByReferenceNo");
			q.setParameter("referenceNo", referenceNo);
			result = (AgentCommission) q.getSingleResult();
			em.flush();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AgentCommission", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewAgentCommisssion(List<AgentCommission> agentCommissions) throws DAOException {
		try {
			for (AgentCommission agentCommission : agentCommissions) {
				em.persist(agentCommission);
				em.flush();
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to insert AgentCommission", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewAgentCommisssion(AgentCommission agentCommission) throws DAOException {
		try {
			em.persist(agentCommission);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert AgentCommission", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentCommission> findAgentCommissionBySanctionNo(String sanctionNo) throws DAOException {
		List<AgentCommission> results = null;
		try {
			Query query = em.createNamedQuery("AgentCommission.findBySanctionNo");
			query.setParameter("sanctionNo", sanctionNo);
			results = query.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of agent sanction", pe);
		}
		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(AgentCommission agentCommission) throws DAOException {
		try {
			em.merge(agentCommission);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Agent Commission", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentCommission> findAgentCommissionByInvoiceNo(String invoiceNo) throws DAOException {
		List<AgentCommission> result = null;
		try {
			Query q = em.createQuery("SELECT ac FROM AgentCommission ac WHERE ac.invoiceDate IS NOT NULL AND ac.invoiceNo = :invoiceNo");
			q.setParameter("invoiceNo", invoiceNo);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Agent Commission For Payment By Agent", pe);
		}
		return result;
	}

}
