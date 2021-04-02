package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.medical.process.Process;
import org.ace.insurance.medical.process.service.interfaces.IProcessService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ProcessDialogActionBean")
@ViewScoped
public class ProcessDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProcessService}")
	private IProcessService processService;

	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}

	private List<Process> processList;

	@PostConstruct
	public void init() {
		processList = processService.findAllProcess();
	}

	public List<Process> getProcessList() {
		return processList;
	}

	public void selectProcess(Process process) {
		RequestContext.getCurrentInstance().closeDialog(process);
	}
}
