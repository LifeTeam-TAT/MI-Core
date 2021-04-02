package org.ace.insurance.system.common.medicalPlace.Service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.medicalPlace.MedicalPlace;

public interface IMedicalPlaceService {
	public void addNewMedicalPlace(MedicalPlace medicalPlace);

	public void updateMedicalPlace(MedicalPlace medicalPlace);

	public void deleteMedicalPlace(MedicalPlace medicalPlace);

	public List<MedicalPlace> findAllMedicalPlace();

	public MedicalPlace findbyId(String id);

	public List<MedicalPlace> findByCriteria(String criteria);

}
