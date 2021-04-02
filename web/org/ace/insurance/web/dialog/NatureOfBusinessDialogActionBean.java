package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.buildingOccupation.BuildingOccupation;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.insurance.system.common.buildingOccupation.service.interfaces.IBuildingOccupationService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "NatureOfBusinessDialogActionBean")
@ViewScoped
public class NatureOfBusinessDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{BuildingOccupationService}")
	private IBuildingOccupationService buildingOccupationService;

	public void setBuildingOccupationService(IBuildingOccupationService buildingOccupationService) {
		this.buildingOccupationService = buildingOccupationService;
	}

	private List<BuildingOccupation> buildingOccupationList;
	private String searchName;
	private BuildingOccupationType buildingOccupationType;

	@PostConstruct
	public void init() {
		buildingOccupationList = buildingOccupationService.findAllBuildingOccupation();
	}

	public void search() {
		buildingOccupationList = buildingOccupationService.findByCriteria(searchName, buildingOccupationType);
	}

	public BuildingOccupationType[] getBuildingOccupationtypeList() {
		return BuildingOccupationType.values();
	}

	public void selectBuildingOccupation(BuildingOccupation busiOcc) {
		RequestContext.getCurrentInstance().closeDialog(busiOcc);
	}

	public List<BuildingOccupation> getBusiOccList() {
		return buildingOccupationList;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public List<BuildingOccupation> getBuildingOccupationList() {
		return buildingOccupationList;
	}

	public void setBuildingOccupationType(BuildingOccupationType buildingOccupationType) {
		this.buildingOccupationType = buildingOccupationType;
	}

	public BuildingOccupationType getBuildingOccupationType() {
		return buildingOccupationType;
	}

}
