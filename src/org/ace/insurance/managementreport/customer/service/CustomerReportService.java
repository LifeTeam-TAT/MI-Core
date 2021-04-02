package org.ace.insurance.managementreport.customer.service;

import javax.annotation.Resource;

import org.ace.insurance.managementreport.customer.CustomerReport;
import org.ace.insurance.managementreport.customer.persistence.interfaces.ICustomerReportDAO;
import org.ace.insurance.managementreport.customer.service.interfaces.ICustomerReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "CustomerReportService")
public class CustomerReportService implements ICustomerReportService {
	@Resource(name = "CustomerReportDAO")
	private ICustomerReportDAO customerReportDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByActivePolicies() {
		CustomerReport result = null;
		try {
			result = customerReportDao.findActiveCustomerByActivePolicies();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Customer by active policies.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByGender() {
		CustomerReport result = null;
		try {
			result = customerReportDao.findActiveCustomerByGender();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Customer by Gender.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByType() {
		CustomerReport result = null;
		try {
			result = customerReportDao.findActiveCustomerByType();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Customer by Type.", e);
		}
		return result;
	}

	public CustomerReport findActiveCustomerByProduct() {
		CustomerReport result = null;
		try {
			result = customerReportDao.findActiveCustomerByProduct();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Customer by Product.", e);
		}
		return result;
	}

	public CustomerReport findActiveCustomerByLocation() {
		CustomerReport result = null;
		try {
			result = customerReportDao.findActiveCustomerByLocation();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Customer by Product.", e);
		}
		return result;
	}
}
