/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.customer.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.CustomerCriteria;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.system.common.customer.CUST001;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.persistence.interfaces.ICustomerDAO;
import org.ace.insurance.user.User;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CustomerDAO")
public class CustomerDAO extends BasicDAO implements ICustomerDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Customer customer) throws DAOException {
		try {
			em.persist(customer);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Customer", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Customer customer, User user) throws DAOException {
		try {
			em.persist(customer);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Customer with process user", pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(List<Customer> customerList) throws DAOException {
		try {
			for (Customer customer : customerList) {
				em.persist(customer);
				em.flush();
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Customer", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer update(Customer customer) throws DAOException {
		try {
			customer = em.merge(customer);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Customer", pe);
		}
		return customer;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Customer customer) throws DAOException {
		try {
			customer = em.merge(customer);
			em.remove(customer);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Customer", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer findById(String id) throws DAOException {
		Customer result = null;
		try {
			result = em.find(Customer.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Customer", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Customer> findAll() throws DAOException {
		List<Customer> result = null;
		try {
			Query q = em.createNamedQuery("Customer.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Customer", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CUST001> findByCriteria(CustomerCriteria criteria, int max) throws DAOException {
		List<CUST001> results = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + CUST001.class.getName());
			buffer.append("(c.id , c.initialId, c.name, c.contentInfo.mobile, c.contentInfo.email, c.contentInfo.phone,");
			buffer.append(" c.bankAccountNo, c.dateOfBirth, c.gender, c.fatherName, c.fullIdNo) FROM Customer c ");
			switch (criteria.getCustomerCriteria()) {
				case FULLNAME: {
					buffer.append(" WHERE CONCAT(TRIM(c.initialId),' ' ,TRIM(c.name.firstName),' ', TRIM(CONCAT(TRIM(c.name.middleName), ' ')), TRIM(c.name.lastName))");
					buffer.append(" LIKE :value");
					break;
				}
				case FIRSTNAME: {
					buffer.append("WHERE c.name.firstName LIKE :value");
					break;
				}
				case MIDDLENAME: {
					buffer.append("WHERE c.name.middleName LIKE :value");
					break;
				}
				case LASTNAME: {
					buffer.append("WHERE c.name.lastName LIKE :value");
					break;
				}
				case NRCNO:
				case FRCNO:
				case PASSPORTNO: {
					buffer.append("WHERE c.fullIdNo = :value AND c.idType = :type");
					break;
				}

			}

			buffer.append(" ORDER BY c.recorder.createdDate, c.recorder.updatedDate desc ");
			Query query = em.createQuery(buffer.toString());
			query.setMaxResults(max);
			switch (criteria.getCustomerCriteria()) {
				case PASSPORTNO:
					query.setParameter("type", IdType.PASSPORTNO);
					query.setParameter("value", criteria.getCriteriaValue());
					break;
				case NRCNO:
					query.setParameter("type", IdType.NRCNO);
					query.setParameter("value", criteria.getCriteriaValue());
					break;
				case FRCNO:
					query.setParameter("type", IdType.FRCNO);
					query.setParameter("value", criteria.getCriteriaValue());
					break;
				default:
					query.setParameter("value", "%" + criteria.getCriteriaValue() + "%");
					break;
			}

			results = query.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Customer", pe);
		}
		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivePolicy(int policyCount, String customerId) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE Customer c SET c.activePolicy = :activePolicy WHERE c.id = :id");
			q.setParameter("activePolicy", policyCount);
			q.setParameter("id", customerId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update active policy", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivedPolicyDate(Date activedDate, String customerId) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE Customer c SET c.activedDate = :activedDate WHERE c.id = :id");
			q.setParameter("activedDate", activedDate);
			q.setParameter("id", customerId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update active policy date", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer findCustomerByInsuredPerson(Name name, String idNo, String fatherName, Date dob) throws DAOException {
		Customer result = null;
		try {
			Query q = em.createQuery("SELECT c From Customer c WHERE c.name = :name " + " AND c.fatherName = :fatherName AND c.dateOfBirth = :dob AND c.fullIdNo = :idNo");
			q.setParameter("name", name);
			q.setParameter("fatherName", fatherName);
			q.setParameter("dob", dob);
			q.setParameter("idNo", idNo);
			List<Customer> customerList = q.getResultList();
			em.flush();
			if (customerList.size() == 0) {
				return null;
			} else {
				result = customerList.get(0);
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Customer", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isExistingCustomer(Customer customer) throws DAOException {
		boolean exist = false;
		try {
			StringBuffer buffer = new StringBuffer();
			Query query = null;

			if (!customer.getIdType().equals(IdType.STILL_APPLYING)) {
				buffer = new StringBuffer("SELECT CASE WHEN (COUNT(c.fullIdNo) > 0) THEN TRUE ELSE FALSE END FROM Customer c");
				buffer.append(" WHERE c.fullIdNo = :fullIdNo ");
				buffer.append(customer.getId() != null ? "AND c.id != :id" : "");
				query = em.createQuery(buffer.toString());
				if (customer.getId() != null)
					query.setParameter("id", customer.getId());
				query.setParameter("fullIdNo", customer.getFullIdNo());
				exist = (Boolean) query.getSingleResult();
			}

			if (!exist) {
				buffer = new StringBuffer("SELECT CASE WHEN (COUNT(c.id) > 0) THEN TRUE ELSE FALSE END FROM Customer c");
				buffer.append(" WHERE c.id != :id AND c.initialId = :initialId AND c.name = :name AND c.fatherName = :fatherName ");
				buffer.append(" AND c.dateOfBirth = :dateOfBirth ");
				query = em.createQuery(buffer.toString());
				query.setParameter("id", customer.getId());
				query.setParameter("initialId", customer.getInitialId());
				query.setParameter("name", customer.getName());
				query.setParameter("fatherName", customer.getFatherName());
				query.setParameter("dateOfBirth", customer.getDateOfBirth());
				exist = (Boolean) query.getSingleResult();
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Existing NRC No.", pe);
		}

		return exist;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean checkExistingCustomer(Customer customer) throws DAOException {
		boolean exist = false;
		try {
			StringBuffer buffer = new StringBuffer();
			Query query = null;

			if (!customer.getIdType().equals(IdType.STILL_APPLYING)) {
				buffer = new StringBuffer("SELECT CASE WHEN (COUNT(c.fullIdNo) > 0) THEN TRUE ELSE FALSE END FROM Customer c");
				buffer.append(" WHERE c.fullIdNo = :fullIdNo ");
				buffer.append(customer.getId() != null ? "AND c.id != :id" : "");
				query = em.createQuery(buffer.toString());
				if (customer.getId() != null)
					query.setParameter("id", customer.getId());
				query.setParameter("fullIdNo", customer.getFullIdNo());
				exist = (Boolean) query.getSingleResult();
			}

			if (!exist) {
				buffer = new StringBuffer("SELECT CASE WHEN (COUNT(c.id) > 0) THEN TRUE ELSE FALSE END FROM Customer c");
				buffer.append(" WHERE c.id != :id AND c.initialId = :initialId AND c.name = :name AND c.fatherName = :fatherName ");
				buffer.append(" AND c.dateOfBirth = :dateOfBirth ");
				query = em.createQuery(buffer.toString());
				query.setParameter("id", customer.getId());
				query.setParameter("initialId", customer.getInitialId());
				query.setParameter("name", customer.getName());
				query.setParameter("fatherName", customer.getFatherName());
				query.setParameter("dateOfBirth", customer.getDateOfBirth());
				exist = (Boolean) query.getSingleResult();
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Existing NRC No.", pe);
		}

		return exist;
	}

}
