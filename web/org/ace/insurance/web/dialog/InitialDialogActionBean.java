package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.initial.Initial;
import org.ace.insurance.system.common.initial.service.interfaces.IInitialService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "InitialDialogActionBean")
@ViewScoped
public class InitialDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Initial> initialList;

	@ManagedProperty(value = "#{InitialService}")
	private IInitialService initialService;

	public void setInitialService(IInitialService initialService) {
		this.initialService = initialService;
	}

	@PostConstruct
	public void init() {
		initialList = initialService.findAllInitial();
	}

	public List<Initial> getInitialList() {
		return initialList;
	}

	public void selectInitial(Initial initial) {
		RequestContext.getCurrentInstance().closeDialog(initial);
	}

}
