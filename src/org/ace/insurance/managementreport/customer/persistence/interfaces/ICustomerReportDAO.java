package org.ace.insurance.managementreport.customer.persistence.interfaces;

import org.ace.insurance.managementreport.customer.CustomerReport;

public interface ICustomerReportDAO {
	public CustomerReport findActiveCustomerByGender();
	
	public CustomerReport findActiveCustomerByType();
	
	public CustomerReport findActiveCustomerByProduct();
	
	public CustomerReport findActiveCustomerByActivePolicies();
	
	public CustomerReport findActiveCustomerByLocation();

}
