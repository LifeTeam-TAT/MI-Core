package org.ace.insurance.report.common;

import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;

public class WorkFlowStatusCriteria {
	private WorkFlowType workFlowType;
	private WorkflowTask workFlowTask;

	public WorkFlowStatusCriteria(WorkFlowType workFlowType, WorkflowTask workFlowTask) {
		this.workFlowType = workFlowType;
		this.workFlowTask = workFlowTask;
	}

	public WorkFlowType getWorkFlowType() {
		return workFlowType;
	}

	public WorkflowTask getWorkFlowTask() {
		return workFlowTask;
	}
}
