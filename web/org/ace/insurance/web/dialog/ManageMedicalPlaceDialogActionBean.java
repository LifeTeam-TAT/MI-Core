package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.medicalPlace.MedicalPlace;
import org.ace.insurance.system.common.medicalPlace.Service.interfaces.IMedicalPlaceService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ManageMedicalPlaceDialogActionBean")
@ViewScoped
public class ManageMedicalPlaceDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{MedicalPlaceService}")
	private IMedicalPlaceService medicalPlaceService;

	public void setMedicalPlaceService(IMedicalPlaceService medicalPlaceService) {
		this.medicalPlaceService = medicalPlaceService;
	}

	private List<MedicalPlace> medicalPlaceList;

	@PostConstruct
	public void init() {
		medicalPlaceList = medicalPlaceService.findAllMedicalPlace();
	}

	public List<MedicalPlace> getMedicalPlaceList() {
		return medicalPlaceList;
	}

	public void selectMedicalPlace(MedicalPlace medicalPlace) {
		RequestContext.getCurrentInstance().closeDialog(medicalPlace);
	}
}
