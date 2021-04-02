package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.hospital.service.interfaces.IHospitalService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "HospitalDialogActionBean")
@ViewScoped
public class HospitalDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{HospitalService}")
	private IHospitalService hospitalService;

	public void setHospitalService(IHospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	private List<Hospital> hospitalList;
	private String criteria;

	@PostConstruct
	public void init() {
		hospitalList = hospitalService.findAllHospital();
	}

	public List<Hospital> getHospitalList() {
		return hospitalList;
	}

	public void selectHospital(Hospital hospital) {
		RequestContext.getCurrentInstance().closeDialog(hospital);
	}

	public void search() {
		hospitalList = hospitalService.findByCriteria(criteria);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void setHospitalList(List<Hospital> hospitalList) {
		this.hospitalList = hospitalList;
	}

}
