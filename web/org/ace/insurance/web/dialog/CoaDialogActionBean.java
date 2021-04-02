package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.bank.COA001;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "CoaDialogActionBean")
@ViewScoped
public class CoaDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{BankService}")
	private IBankService bankService;

	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	private List<COA001> coaList;

	@PostConstruct
	public void init() {
		coaList = bankService.findAllCOAByAType();
	}

	public void selectCoa(COA001 coa001) {
		PrimeFaces.current().dialog().closeDynamic(coa001.getAcode());
	}

	public List<COA001> getCoaList() {
		return coaList;
	}
}
