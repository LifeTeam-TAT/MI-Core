/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.customer.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.CustomerCriteria;
import org.ace.insurance.common.Name;
import org.ace.insurance.system.common.customer.CUST001;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.persistence.interfaces.ICustomerDAO;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "CustomerService")
public class CustomerService extends BaseService implements ICustomerService {

	@Resource(name = "CustomerDAO")
	private ICustomerDAO customerDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer addNewCustomer(Customer customer) {
		try {
			customer.setFullIdNo();
			customerDAO.insert(customer);
			customer = customerDAO.findById(customer.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Customer", e);
		}
		return customer;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewCustomerByList(List<Customer> customerList) {
		try {
			customerDAO.insert(customerList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Customer", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer updateCustomer(Customer customer) {
		try {
			customer.setFullIdNo();
			return customerDAO.update(customer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Customer", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCustomer(Customer customer) {
		try {
			customerDAO.delete(customer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Customer", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer findCustomerById(String id) {
		Customer result = null;
		try {
			result = customerDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Customer (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CUST001> findCustomerByCriteria(CustomerCriteria criteria, int max) {
		List<CUST001> result = null;
		try {
			result = customerDAO.findByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Customer (criteriaValue : " + criteria.getCriteriaValue() + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivePolicy(int policyCount, String customerId) {
		try {
			customerDAO.updateActivePolicy(policyCount, customerId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update activePolicy", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer findCustomerByInsuredPerson(Name name, String idNo, String fatherName, Date dob) {
		Customer result = null;
		try {
			result = customerDAO.findCustomerByInsuredPerson(name, idNo, fatherName, dob);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find  Customer by CustomerInformationCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isExistingCustomer(Customer customer) throws DAOException {
		boolean isExist = false;
		try {
			isExist = customerDAO.isExistingCustomer(customer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find existing NRC No.", e);
		}
		return isExist;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean checkExistingCustomer(Customer customer) {

		boolean result = false;
		try {
			result = customerDAO.checkExistingCustomer(customer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to check a Customer (ID : " + customer.getFullName() + ")", e);
		}
		return result;

	}
}