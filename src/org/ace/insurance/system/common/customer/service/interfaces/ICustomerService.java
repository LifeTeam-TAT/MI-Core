/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.customer.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.CustomerCriteria;
import org.ace.insurance.common.Name;
import org.ace.insurance.system.common.customer.CUST001;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICustomerService {
	public Customer addNewCustomer(Customer Customer);

	public void addNewCustomerByList(List<Customer> customerList);

	public Customer updateCustomer(Customer Customer);

	public void deleteCustomer(Customer Customer);

	public Customer findCustomerById(String id);

	public List<CUST001> findCustomerByCriteria(CustomerCriteria criteria, int max);

	public void updateActivePolicy(int policyCount, String customerId);

	public Customer findCustomerByInsuredPerson(Name name, String idNo, String fatherName, Date dob);

	public boolean isExistingCustomer(Customer customer) throws DAOException;

	public boolean checkExistingCustomer(Customer customer);

}
