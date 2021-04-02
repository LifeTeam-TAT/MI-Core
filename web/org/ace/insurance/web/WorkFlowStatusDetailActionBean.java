package org.ace.insurance.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.report.common.WorkFlowStatusCriteria;
import org.ace.insurance.report.common.WorkFlowStatusReport;
import org.ace.insurance.report.common.service.interfaces.IWorkFlowReportService;
import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "WorkFlowStatusDetailActionBean")
public class WorkFlowStatusDetailActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{WorkFlowReportService}")
	private IWorkFlowReportService workFlowReportService;

	public void setWorkFlowReportService(IWorkFlowReportService workFlowReportService) {
		this.workFlowReportService = workFlowReportService;
	}

	private WorkFlowType workFlowType;
	private WorkflowTask workFlowTask;
	private List<WorkFlowStatusReport> workFlowStatusReportList;

	@PostConstruct
	public void init() {
		workFlowStatusReportList = workFlowReportService.findStatusReport(new WorkFlowStatusCriteria(workFlowType, workFlowTask));
	}

	public List<WorkFlowStatusReport> getWorkFlowList() {
		return workFlowStatusReportList;
	}
}
