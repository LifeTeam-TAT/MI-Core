package org.ace.insurance.web.dialog;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.insurance.disabilitypart.service.IDisabilityPartService;
import org.ace.insurance.web.Param;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;



@ManagedBean(name = "DisabilityPartDialogActionBean")
@ViewScoped
public class DisabilityPartDialogActionBean extends BaseBean {

	@ManagedProperty(value = "#{DisabilityPartService}")
	private IDisabilityPartService disabilityPartService;

	public void setDisabilityPartService(IDisabilityPartService disabilityPartService) {
		this.disabilityPartService = disabilityPartService;
	}

	private List<DisabilityPart> disabilityPartList;
	private List<DisabilityPart> selectedDisabilityPartList;

	@PostConstruct
	public void init() {
		disabilityPartList = disabilityPartService.findAllDisabilityPart();
		selectedDisabilityPartList = (List<DisabilityPart>) getParam(Param.DISABILITY_PART_LIST);
		if (selectedDisabilityPartList == null) {
			selectedDisabilityPartList = new ArrayList<DisabilityPart>();
		}
	}

	public List<DisabilityPart> getDisabilityPartList() {
		return disabilityPartList;
	}

	public List<DisabilityPart> getSelectedDisabilityPartList() {
		return selectedDisabilityPartList;
	}

	public void setSelectedDisabilityPartList(List<DisabilityPart> selectedDisabilityPartList) {
		this.selectedDisabilityPartList = selectedDisabilityPartList;
	}

	public void saveDisabilityPart() {
		PrimeFaces.current().dialog().closeDynamic(selectedDisabilityPartList);
	}

}

