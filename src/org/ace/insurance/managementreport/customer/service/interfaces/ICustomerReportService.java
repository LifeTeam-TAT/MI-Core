package org.ace.insurance.managementreport.customer.service.interfaces;

import org.ace.insurance.managementreport.customer.CustomerReport;

public interface ICustomerReportService {
	public CustomerReport findActiveCustomerByGender();

	public CustomerReport findActiveCustomerByType();

	public CustomerReport findActiveCustomerByProduct();
	
	public CustomerReport findActiveCustomerByActivePolicies();
	
	public CustomerReport findActiveCustomerByLocation();
}
