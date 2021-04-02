/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.ace.insurance.common.CustomerCriteria;
import org.ace.insurance.system.common.customer.CUST001;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;

@ViewScoped
@ManagedBean(name = "ManageCustomerActionBean")
public class ManageCustomerActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CustomerService}")
	private ICustomerService customerService;

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	private CustomerCriteria customerCriteria;
	private List<CUST001> customerList;

	@PostConstruct
	public void init() {
		resetCustomer();
	}

	public void resetCustomer() {
		customerCriteria = new CustomerCriteria();
		customerList = new ArrayList<>();
	}

	public void searchCustomer() {
		customerList = customerService.findCustomerByCriteria(customerCriteria, 30);
	}

	public void outjectCustomer(Customer customer) {
		putParam("customer", customer);

	}

	public String updateCustomer(CUST001 cust001) {
		String result = null;
		try {
			Customer customer = customerService.findCustomerById(cust001.getId());
			outjectCustomer(customer);
			result = "addNewCustomer";

		} catch (SystemException ex) {
			addErrorMessage(null, MessageId.CHILD_RECORD_FOUND, ex);
		}

		return result;
	}

	public String creatNewCustomer() {
		return "addNewCustomer";
	}

	public void deleteCustomer(CUST001 cust001) {
		try {
			Customer customer = customerService.findCustomerById(cust001.getId());
			customerService.deleteCustomer(customer);
			customerList.remove(cust001);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, cust001.getFullName());
		} catch (SystemException ex) {
			String response = (String) ex.getResponse();
			addErrorMessage(null, MessageId.CHILD_RECORD_FOUND, response);
		}
	}

	private final String reportName = "CustomerInfo";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport(CUST001 cust001) {
		Customer customer = customerService.findCustomerById(cust001.getId());
		DocumentBuilder.generateCustomerInfo(customer, dirPath, fileName);

	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomerCriteria getCustomerCriteria() {
		return customerCriteria;
	}

	public List<CUST001> getCustomerList() {
		return customerList;
	}

	public void checkMessage(ComponentSystemEvent event) {
		String messageId = (String) getParam(Constants.MESSAGE_ID);
		String proposalNo = (String) getParam(Constants.PROPOSAL_NO);
		if (messageId != null && proposalNo != null) {
			addInfoMessage(null, messageId, proposalNo);
			removeParam(Constants.MESSAGE_ID);
			removeParam(Constants.PROPOSAL_NO);
		}
	}

}
