package org.ace.insurance.system.common.buildingOccupation.service.interfaces;

import java.util.List;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupation;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;

public interface IBuildingOccupationService {
	public void addNewBuildingOccupation(BuildingOccupation buildingOccupation);

	public void updateBuildingOccupation(BuildingOccupation buildingOccupation);

	public void deleteBuildingOccupation(BuildingOccupation buildingOccupation);

	public BuildingOccupation findBuildingOccupationById(String id);

	public List<BuildingOccupation> findBuildingOccupationByInsuranceType(InsuranceType insuranceType);

	public List<BuildingOccupation> findAllBuildingOccupation();

	public List<BuildingOccupation> findByCriteria(String searchName, BuildingOccupationType buildingOccupationType);
}
