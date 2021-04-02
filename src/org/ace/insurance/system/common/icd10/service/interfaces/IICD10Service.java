package org.ace.insurance.system.common.icd10.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.icd10.ICD10;

public interface IICD10Service {
	public void addNewICD10(ICD10 idIcd10);

	public void updateICD10(ICD10 idIcd10);

	public void deleteICD10(ICD10 idIcd10);

	public List<ICD10> findAllICD10();

	public ICD10 findbyId(String id);

	public List<ICD10> findByCriteria(String criteria);

}
