package org.ace.insurance.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.ace.insurance.user.User;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;

@RequestScoped
@ManagedBean(name = "NitificationActionBean")
public class NitificationActionBean {
	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private User user;

	public long getRequestCount() {
		return workFlowService.findRequestCountByUser(user);
	}
}
