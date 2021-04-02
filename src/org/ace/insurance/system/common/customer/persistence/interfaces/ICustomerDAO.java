/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.customer.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.CustomerCriteria;
import org.ace.insurance.common.Name;
import org.ace.insurance.system.common.customer.CUST001;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.user.User;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICustomerDAO {
	public void insert(Customer customer) throws DAOException;

	public void insert(Customer customer, User user) throws DAOException;

	public void insert(List<Customer> customerList) throws DAOException;

	public Customer update(Customer customer) throws DAOException;

	public void delete(Customer customer) throws DAOException;

	public Customer findById(String id) throws DAOException;

	public List<CUST001> findByCriteria(CustomerCriteria criteria, int max) throws DAOException;

	public void updateActivePolicy(int policyCount, String customerId) throws DAOException;

	public void updateActivedPolicyDate(Date activedDate, String customerId) throws DAOException;

	public Customer findCustomerByInsuredPerson(Name name, String idNo, String fatherName, Date dob) throws DAOException;

	public boolean isExistingCustomer(Customer customer) throws DAOException;

	public boolean checkExistingCustomer(Customer customer);
}
