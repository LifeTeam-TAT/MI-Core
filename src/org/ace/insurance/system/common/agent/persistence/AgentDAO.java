/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.agent.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.AgentCriteria;
import org.ace.insurance.common.IdType;
import org.ace.insurance.system.common.agent.AGP001;
import org.ace.insurance.system.common.agent.AGP002;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.AgentPortfolio;
import org.ace.insurance.system.common.agent.persistence.interfaces.IAgentDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AgentDAO")
public class AgentDAO extends BasicDAO implements IAgentDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Agent agent) throws DAOException {
		try {
			em.persist(agent);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Agent", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertAgentPortfolio(AgentPortfolio agentPortfolio) {
		try {
			em.persist(agentPortfolio);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert AgentPortfolio", pe);
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AGP001> findAgentPortfolioByPolicyNo(String policyNo) {
		List<AGP001> results = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT NEW " + AGP001.class.getName());
			query.append("(a.Id, CONCAT(TRIM(a.agent.name.firstName), ' ', TRIM(a.agent.name.middleName), ' ', TRIM(a.agent.name.lastName)), a.policyNo, a.startDate, a.endDate)");
			query.append("FROM AgentPortfolio a WHERE a.policyNo LIKE :policyNo");
			Query q = em.createQuery(query.toString());
			q.setParameter("policyNo", "%" + policyNo + "%");
			results = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to Select AgentPortfolio", pe);
		}

		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Agent update(Agent agent) throws DAOException {
		try {
			agent = em.merge(agent);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Agent", pe);
		}
		return agent;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentPortfolio updateAgentPortfolio(AgentPortfolio portfolio) throws DAOException {
		try {
			portfolio = em.merge(portfolio);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Agent", pe);
		}
		return portfolio;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Agent agent) throws DAOException {
		try {
			agent = em.merge(agent);
			em.remove(agent);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete Agent", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAgentPortfolio(AgentPortfolio agentPortfolio) throws DAOException {
		try {
			agentPortfolio = em.merge(agentPortfolio);
			em.remove(agentPortfolio);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete agentPortfolio", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Agent findById(String id) throws DAOException {
		Agent result = null;
		try {
			result = em.find(Agent.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Agent", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public AgentPortfolio findAgentPortfolioById(String id) throws DAOException {
		AgentPortfolio result = null;
		try {
			result = em.find(AgentPortfolio.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AgentPortfolio", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Agent> findAll() throws DAOException {
		List<Agent> result = null;
		try {
			Query q = em.createNamedQuery("Agent.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Agent", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Agent> findByCriteria(AgentCriteria criteria) throws DAOException {
		List<Agent> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT a FROM Agent a ");
			if (criteria.getAgentCriteriaItems() != null) {
				switch (criteria.getAgentCriteriaItems()) {
					case FIRSTNAME: {
						query.append("WHERE a.firstName like :name");
						break;
					}
					case MIDDLENAME: {
						query.append("WHERE a.middleName like :name");
						break;
					}
					case LASTNAME: {
						query.append("WHERE a.lastName like :name");
						break;
					}
					case FULLNAME: {
						query.append("WHERE CONCAT(a.firstName, ' ',a.middleName, ' ',a.lastName) like :name");
						break;
					}
					case NRCNO:
					case FRCNO:
					case PASSPORTNO: {
						query.append("WHERE a.idNo = :idNo AND a.idType = :idType");
						break;
					}
				}
			}
			Query q = em.createQuery(query.toString());
			if (criteria.getAgentCriteriaItems() != null) {
				switch (criteria.getAgentCriteriaItems()) {
					case FIRSTNAME:
					case MIDDLENAME:
					case LASTNAME:
					case FULLNAME: {
						q.setParameter("name", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case NRCNO: {
						q.setParameter("idNo", criteria.getCriteriaValue());
						q.setParameter("idType", IdType.NRCNO);
						break;
					}
					case FRCNO: {
						q.setParameter("idNo", criteria.getCriteriaValue());
						q.setParameter("idType", IdType.FRCNO);
						break;
					}
					case PASSPORTNO: {
						q.setParameter("idNo", criteria.getCriteriaValue());
						q.setParameter("idType", IdType.PASSPORTNO);
						break;
					}
					default:
						break;
				}
			}
			result = q.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Agent", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AGP002> findByCriteria(AgentCriteria criteria, int max) throws DAOException {
		List<AGP002> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT New " + AGP002.class.getName());
			query.append("(a.id ,a.codeNo,a.residentAddress.residentAddress,a.liscenseNo,a.initialId, a.name, a.contentInfo.phone,");
			query.append("a.dateOfBirth, a.gender, a.fatherName, a.fullIdNo) FROM Agent a ");
			if (criteria.getAgentCriteriaItems() != null) {
				switch (criteria.getAgentCriteriaItems()) {
					case FIRSTNAME: {
						query.append(" WHERE a.name.firstName LIKE :value");
						break;
					}
					case MIDDLENAME: {
						query.append(" WHERE a.name.middleName LIKE :value");
						break;
					}
					case LASTNAME: {
						query.append(" WHERE a.name.lastName LIKE :value");
						break;
					}
					case FULLNAME: {
						query.append(" WHERE CONCAT(TRIM(a.initialId),' ' ,TRIM(a.name.firstName),' ', TRIM(CONCAT(TRIM(a.name.middleName), ' ')), TRIM(a.name.lastName))");
						query.append(" LIKE :value");
						break;
					}

					case NRCNO:
					case FRCNO:
					case PASSPORTNO: {
						query.append(" WHERE a.fullIdNo = :fullIdNo AND a.idType = :idType");
						break;
					}
					case LISCENSENO:
						query.append(" WHERE a.liscenseNo LIKE :value");
					default: {
						break;
					}
				}
			}
			Query q = em.createQuery(query.toString());
			query.append(" Order By a.firstName DESC");
			q.setMaxResults(max);

			if (criteria.getAgentCriteriaItems() != null) {
				switch (criteria.getAgentCriteriaItems()) {
					case PASSPORTNO:
						q.setParameter("type", IdType.PASSPORTNO);
						q.setParameter("value", criteria.getCriteriaValue());
						break;
					case NRCNO:
						q.setParameter("type", IdType.NRCNO);
						q.setParameter("value", criteria.getCriteriaValue());
						break;
					case FRCNO:
						q.setParameter("type", IdType.FRCNO);
						q.setParameter("value", criteria.getCriteriaValue());
						break;
					default:
						q.setParameter("value", "%" + criteria.getCriteriaValue() + "%");
						break;
				}

			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Agent", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkExistingAgent(String liscenseNo) throws DAOException {
		boolean result = false;
		try {
			Query q = em.createQuery(" SELECT a From Agent a WHERE a.liscenseNo = :liscenseNo");
			q.setParameter("liscenseNo", liscenseNo);
			Agent agent = (Agent) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return true;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Agent", pe);
		}
		return result;
	}

}
