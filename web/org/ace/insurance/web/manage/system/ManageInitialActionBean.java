package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.initial.Initial;
import org.ace.insurance.system.common.initial.service.interfaces.IInitialService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageInitialActionBean")
public class ManageInitialActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{InitialService}")
	private IInitialService initialService;

	public void setInitialService(IInitialService initialService) {
		this.initialService = initialService;
	}

	private Initial initial;
	private boolean createNew;

	@PostConstruct
	public void init() {
		createNewInitial();
	}

	public void createNewInitial() {
		createNew = true;
		initial = new Initial();
	}

	public void prepareUpdateInitial(Initial initial) {
		createNew = false;
		this.initial = initial;
	}

	public void addNewInitial() {
		try {
			initialService.addNewInitial(initial);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, initial.getId());
			createNewInitial();

		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateInitial() {
		try {
			initialService.updateInitial(initial);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, initial.getId());
			createNewInitial();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteInitial() {
		try {
			initialService.deleteInitial(initial);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, initial.getId());
			createNewInitial();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<Initial> getInitialList() {
		return initialService.findAllInitial();
	}

	public Initial getInitial() {
		return initial;
	}

	public void setInitial(Initial initial) {
		this.initial = initial;
	}
}
