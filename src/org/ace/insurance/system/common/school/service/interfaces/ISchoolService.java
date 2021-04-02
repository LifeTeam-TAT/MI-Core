package org.ace.insurance.system.common.school.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.school.School;

public interface ISchoolService {

	public void addNewSchool(School school);

	public void updateSchool(School school);

	public void deleteSchool(School school);

	public School findBySchoolId(String schoolId);

	public List<School> findAllSchool();

	public School findBySchoolCode(String schoolCode);

	public boolean checkExistingSchool(School school);

}
