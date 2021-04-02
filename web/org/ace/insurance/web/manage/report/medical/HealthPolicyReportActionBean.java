package org.ace.insurance.web.manage.report.medical;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.report.medical.HealthPolicyReportDTO;
import org.ace.insurance.report.medical.service.interfaces.IHealthPolicyReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "HealthPolicyReportActionBean")
public class HealthPolicyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{HealthPolicyReportService}")
	private IHealthPolicyReportService healthPolicyReportService;

	public void setHealthPolicyReportService(IHealthPolicyReportService healthPolicyReportService) {
		this.healthPolicyReportService = healthPolicyReportService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private HealthPolicyReportCriteria criteria;
	private List<HealthPolicyReportDTO> healthPolicyReportList;
	private List<Branch> branchList;
	private boolean accessBranches;
	private User user;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		branchList = branchService.findAllBranch();
		// }
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new HealthPolicyReportCriteria();
		if (!accessBranches) {
			criteria.setBranch(user.getBranch());
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setPaymentStartDate(cal.getTime());
		criteria.setCommenceStartDate(cal.getTime());
		Date todayDate = new Date();
		criteria.setPaymentEndDate(todayDate);
		criteria.setCommenceEndDate(todayDate);
		filter();
	}

	public void filter() {
		boolean valid = true;
		String formID = "healthProposalListForm";
		if (criteria.getPaymentStartDate() != null && criteria.getPaymentEndDate() != null) {
			if (criteria.getPaymentStartDate().after(criteria.getPaymentEndDate())) {
				addErrorMessage(formID + ":paymentStartDate", MessageId.STARTDATE_MUSTBE_LESSTHAN_ENDDATE);
				valid = false;
			}
		}
		if (criteria.getCommenceStartDate() != null && criteria.getCommenceEndDate() != null) {
			if (criteria.getCommenceStartDate().after(criteria.getCommenceEndDate())) {
				addErrorMessage(formID + ":commenceStartDate", MessageId.STARTDATE_MUSTBE_LESSTHAN_ENDDATE);
				valid = false;
			}
		}
		if (valid) {
			healthPolicyReportList = healthPolicyReportService.findHealthPolicyReportDTO(criteria);
		}
	}

	public void exportHealthPolicyReport() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "HealthPolicyReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			HealthPolicyReportExcel exportExcel = new HealthPolicyReportExcel();
			exportExcel.generate(op, healthPolicyReportList);
			getFacesContext().responseComplete();

		} catch (IOException e) {
			throw new SystemException(null, "Failed to export HealthPolicyReport.xlsx", e);
		}
	}

	public void returnCustomer(SelectEvent event) {
		criteria.setCustomer((Customer) event.getObject());
	}

	public void returnAgent(SelectEvent event) {
		criteria.setAgent((Agent) event.getObject());
	}

	public HealthPolicyReportCriteria getCriteria() {
		return criteria;
	}

	public List<HealthPolicyReportDTO> getHealthPolicyReportList() {
		return healthPolicyReportList;
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}
}
