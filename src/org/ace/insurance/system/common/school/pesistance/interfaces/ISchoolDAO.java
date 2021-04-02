package org.ace.insurance.system.common.school.pesistance.interfaces;

import java.util.List;

import org.ace.insurance.system.common.school.School;
import org.ace.java.component.persistence.exception.DAOException;

public interface ISchoolDAO {

	public void insert(School school) throws DAOException;

	public void delete(School school) throws DAOException;

	public void update(School school) throws DAOException;

	public School findById(String SchoolId) throws DAOException;

	public List<School> findAllSchool() throws DAOException;

	public School findBySchoolCode(String schoolCode) throws DAOException;

	public boolean checkExistingSchool(School school) throws DAOException;

}
