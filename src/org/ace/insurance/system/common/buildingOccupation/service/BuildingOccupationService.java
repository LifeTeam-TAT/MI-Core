package org.ace.insurance.system.common.buildingOccupation.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupation;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.insurance.system.common.buildingOccupation.persistence.interfaces.IBuildingOccupationDAO;
import org.ace.insurance.system.common.buildingOccupation.service.interfaces.IBuildingOccupationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "BuildingOccupationService")
public class BuildingOccupationService extends BaseService implements IBuildingOccupationService {

	@Resource(name = "BuildingOccupationDAO")
	private IBuildingOccupationDAO buildingOccupationDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewBuildingOccupation(BuildingOccupation buildingOccupation) {
		try {
			buildingOccupationDAO.insert(buildingOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new BuildingOccupation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBuildingOccupation(BuildingOccupation buildingOccupation) {
		try {
			buildingOccupationDAO.update(buildingOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a BuildingOccupation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteBuildingOccupation(BuildingOccupation buildingOccupation) {
		try {
			buildingOccupationDAO.delete(buildingOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a BuildingOccupation", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BuildingOccupation> findAllBuildingOccupation() {
		List<BuildingOccupation> result = null;
		try {
			result = buildingOccupationDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of BuildingOccupation)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public BuildingOccupation findBuildingOccupationById(String id) {
		BuildingOccupation result = null;
		try {
			result = buildingOccupationDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a BuildingOccupation (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BuildingOccupation> findBuildingOccupationByInsuranceType(InsuranceType insuranceType) {
		List<BuildingOccupation> result = null;
		try {
			result = buildingOccupationDAO.findByInsuranceType(insuranceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a BuildingOccupation by Insurance Type ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BuildingOccupation> findByCriteria(String searchName, BuildingOccupationType buildingOccupationType) {
		List<BuildingOccupation> result = null;
		try {
			result = buildingOccupationDAO.findByCriteria(searchName, buildingOccupationType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find BuildingOccupation by criteria " + searchName, e);
		}
		return result;
	}

}