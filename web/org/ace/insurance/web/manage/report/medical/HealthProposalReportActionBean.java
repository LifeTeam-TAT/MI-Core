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

import org.ace.insurance.report.medical.HealthProposalReportDTO;
import org.ace.insurance.report.medical.service.interfaces.IHealthProposalReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "HealthProposalReportActionBean")
public class HealthProposalReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{HealthProposalReportService}")
	private IHealthProposalReportService healthProposalReportService;

	public void setHealthProposalReportService(IHealthProposalReportService healthProposalReportService) {
		this.healthProposalReportService = healthProposalReportService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private HealthProposalReportCriteria criteria;
	private List<HealthProposalReportDTO> healthProposalReportList;
	private List<Branch> branchList;
	private boolean accessBranches;
	private User user;

	/* pdf export */
	private String branchName;
	private final String reportName = "HealthProposalReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

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
		criteria = new HealthProposalReportCriteria();
		if (!accessBranches) {
			criteria.setBranch(user.getBranch());
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		healthProposalReportList = healthProposalReportService.findHealthProposalReportDTO(criteria);
	}

	public void filter() {
		healthProposalReportList = healthProposalReportService.findHealthProposalReportDTO(criteria);
	}

	public void exportHealthProposalReport() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "HealthProposalReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			HealthProposalReportExcel exportExcel = new HealthProposalReportExcel();
			exportExcel.generate(op, healthProposalReportList);
			getFacesContext().responseComplete();

		} catch (IOException e) {
			throw new SystemException(null, "Failed to export HealthProposalReport.xlsx", e);
		}
	}

	public void generatePDFReport() throws Exception {
		try {
			if (criteria.getBranch() == null) {
				branchName = "All";
			} else {
				branchName = criteria.getBranch().getName();
			}
			healthProposalReportService.generateHealthProposalReport(healthProposalReportList, dirPath, fileName, branchName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void returnCustomer(SelectEvent event) {
		criteria.setCustomer((Customer) event.getObject());
	}

	public void returnAgent(SelectEvent event) {
		criteria.setAgent((Agent) event.getObject());
	}

	public HealthProposalReportCriteria getCriteria() {
		return criteria;
	}

	public List<HealthProposalReportDTO> getHealthProposalReportList() {
		return healthProposalReportList;
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public void setAccessBranches(boolean accessBranches) {
		this.accessBranches = accessBranches;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public String getStream() {
		return pdfDirPath + fileName;

	}

}