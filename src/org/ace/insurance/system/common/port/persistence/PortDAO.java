package org.ace.insurance.system.common.port.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.PortCriteria;
import org.ace.insurance.system.common.port.Port;
import org.ace.insurance.system.common.port.persistence.interfaces.IPortDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PortDAO")
public class PortDAO extends BasicDAO implements IPortDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Port port) throws DAOException {
		try {
			em.persist(port);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert port", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Port port) throws DAOException {
		try {
			em.merge(port);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update port", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Port port) throws DAOException {
		try {
			port = em.merge(port);
			em.remove(port);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update port", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Port findById(String id) throws DAOException {
		Port result = null;
		try {
			result = em.find(Port.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Port", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Port> findAll() throws DAOException {
		List<Port> result = null;
		try {
			Query q = em.createNamedQuery("Port.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Port", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Port> findByCriteria(String criteria) throws DAOException {
		List<Port> result = null;
		try {
			Query q = em.createQuery("Select p from Port p where p.name Like '" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Port.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Port> findPortByCriteria(PortCriteria criteria, int max) throws DAOException {
		List<Port> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT o FROM Port o ");
			if (criteria.getPortCriteria() != null) {
				switch (criteria.getPortCriteria()) {
					case NAME: {
						query.append("WHERE o.name like :name");
						break;
					}
					case PORT_CODE: {
						query.append("WHERE o.codeNo like :codeNo");
						break;
					}
					case REGISTRATION_NO: {
						query.append("WHERE o.regNo like :regNo");
						break;
					}
				}
			}
			query.append(" Order By o.name DESC");
			Query q = em.createQuery(query.toString());
			q.setMaxResults(max);
			if (criteria.getPortCriteria() != null) {
				switch (criteria.getPortCriteria()) {
					case NAME: {
						q.setParameter("name", criteria.getCriteriaValue() + "%");
						break;
					}
					case PORT_CODE: {
						q.setParameter("codeNo", criteria.getCriteriaValue());
						break;
					}
					case REGISTRATION_NO: {
						q.setParameter("regNo", criteria.getCriteriaValue());
						break;
					}
				}
			}
			result = q.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Organization", pe);
		}

		return result;
	}

}
