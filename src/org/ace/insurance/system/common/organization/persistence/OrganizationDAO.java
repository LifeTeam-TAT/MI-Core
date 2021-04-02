/***************************************************************************************
] * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.organization.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.organization.ORG001;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.organization.persistence.interfaces.IOrganizationDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("OrganizationDAO")
@SuppressWarnings("unchecked")
public class OrganizationDAO extends BasicDAO implements IOrganizationDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Organization organization) throws DAOException {
		try {
			em.persist(organization);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Organization", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Organization organization) throws DAOException {
		try {
			em.merge(organization);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Organization", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Organization organization) throws DAOException {
		try {
			organization = em.merge(organization);
			em.remove(organization);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Organization", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Organization findById(String id) throws DAOException {
		Organization result = null;
		try {
			result = em.find(Organization.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Organization", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Organization> findAll() throws DAOException {
		List<Organization> result = null;
		try {
			Query q = em.createNamedQuery("Organization.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Organization", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ORG001> findAll_ORG001() throws DAOException {
		List<ORG001> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + ORG001.class.getName() + "(");
			buffer.append("c.id, c.name, c.OwnerName, c.regNo, c.address.permanentAddress, c.address.township.name, ");
			buffer.append("c.contentInfo.phone, c.contentInfo.mobile, c.contentInfo.email) FROM Organization c ORDER BY c.name ASC");
			Query q = em.createQuery(buffer.toString());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all ORG001.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivePolicy(int policyCount, String organizationId) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE Organization o SET o.activePolicy = :activePolicy WHERE o.id = :id");
			q.setParameter("activePolicy", policyCount);
			q.setParameter("id", organizationId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update active policy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivedPolicyDate(Date activedDate, String organizationId) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE Organization o SET o.activedDate = :activedDate WHERE o.id = :id");
			q.setParameter("activedDate", activedDate);
			q.setParameter("id", organizationId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update active policy date", pe);
		}
	}

}
