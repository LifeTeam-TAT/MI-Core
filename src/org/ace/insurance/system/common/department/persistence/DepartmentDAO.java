package org.ace.insurance.system.common.department.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.department.DEP001;
import org.ace.insurance.system.common.department.Department;
import org.ace.insurance.system.common.department.persistence.interfaces.IDepartmentDAO;
import org.ace.insurance.system.common.organization.ORG001;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("DepartmentDAO")
@SuppressWarnings("unchecked")
public class DepartmentDAO extends BasicDAO implements IDepartmentDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Department department) throws DAOException {
		try {
			em.persist(department);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Department", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Department department) throws DAOException {
		try {
			em.merge(department);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Department", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Department department) throws DAOException {
		try {
			department = em.merge(department);
			em.remove(department);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Department", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Department findById(String id) throws DAOException {
		Department result = null;
		try {
			result = em.find(Department.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Department", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Department> findAll() throws DAOException {
		List<Department> result = null;
		try {
			Query q = em.createNamedQuery("Department.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Department", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DEP001> findAll_DEP001() throws DAOException {
		List<DEP001> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + DEP001.class.getName() + "(");
			buffer.append("c.id, c.name, c.OwnerName, c.regNo, c.address.permanentAddress, c.address.township.name, ");
			buffer.append("c.contentInfo.phone, c.contentInfo.mobile, c.contentInfo.email) FROM Department c ORDER BY c.name ASC");
			Query q = em.createQuery(buffer.toString());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all DEP001.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivePolicy(int policyCount, String departmentId) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE Department o SET o.activePolicy = :activePolicy WHERE o.id = :id");
			q.setParameter("activePolicy", policyCount);
			q.setParameter("id", departmentId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update active policy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivedPolicyDate(Date activedDate, String departmentId) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE Department o SET o.activedDate = :activedDate WHERE o.id = :id");
			q.setParameter("activedDate", activedDate);
			q.setParameter("id", departmentId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update active policy date", pe);
		}
	}
	
	

}
