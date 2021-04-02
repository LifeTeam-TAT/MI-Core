package org.ace.insurance.system.common.classofinsurance.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.classofinsurance.ClassOfInsurance;

public interface IClassOfInsuranceService {
	public void addNewClassOfInsurance(ClassOfInsurance classofinsurance);

	public void updateClassOfInsurance(ClassOfInsurance classofinsurance);

	public void deleteClassOfInsurance(ClassOfInsurance classofinsurance);

	public ClassOfInsurance findClassOfInsuranceById(String id);

	public List<ClassOfInsurance> findAllClassOfInsurance();
	
	public List<ClassOfInsurance> findByCriteria(String criteria);

}
