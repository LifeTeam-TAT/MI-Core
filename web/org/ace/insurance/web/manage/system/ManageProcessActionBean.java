/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.medical.process.Process;
import org.ace.insurance.medical.process.service.interfaces.IProcessService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageProcessActionBean")
public class ManageProcessActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProcessService}")
	private IProcessService processService;

	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}

	private Process process;
	private List<Process> processes;
	private boolean createNew;

	@PostConstruct
	public void init() {
		createNewProcess();
		loadProcess();
	}

	public void createNewProcess() {
		createNew = true;
		process = new Process();
	}

	public void loadProcess() {
		processes = processService.findAllProcess();
	}

	public void prepareUpdateProcess(Process process) {
		createNew = false;
		this.process = process;
	}

	public void addNewProcess() {
		try {
			processService.addNewProcess(process);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, process.getName());
			createNewProcess();
			loadProcess();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateProcess() {
		try {
			processService.updateProcess(process);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, process.getName());
			createNewProcess();
			loadProcess();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteProcess() {
		try {
			processService.deleteProcess(process);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, process.getName());
			createNewProcess();
			loadProcess();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

}
