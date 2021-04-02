package org.ace.insurance.system.common.school.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.system.common.school.School;
import org.ace.insurance.system.common.school.pesistance.interfaces.ISchoolDAO;
import org.ace.insurance.system.common.school.service.interfaces.ISchoolService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SchoolService")
public class SchoolService implements ISchoolService {

	@Resource(name = "SchoolDAO")
	private ISchoolDAO schoolDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSchool(School school) {
		try {
			setSchoolNo(school);
			schoolDAO.insert(school);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new school", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSchool(School school) {
		try {
			schoolDAO.update(school);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a new school", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSchool(School school) {
		try {
			schoolDAO.delete(school);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete  school", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public School findBySchoolId(String schoolId) {
		School result = null;
		try {
			result = schoolDAO.findById(schoolId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find school", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<School> findAllSchool() {
		List<School> result = null;
		try {
			result = schoolDAO.findAllSchool();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find All school", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public School findBySchoolCode(String schoolCodeNo) {
		School result = null;
		try {
			result = schoolDAO.findBySchoolCode(schoolCodeNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find school", e);
		}
		return result;
	}

	private void setSchoolNo(School school) {
		String schoolNo = null;
		schoolNo = customIDGenerator.getNextId(SystemConstants.SCHOOL_CODE_NO, null);
		school.setSchoolCodeNo(schoolNo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkExistingSchool(School school) {

		return schoolDAO.checkExistingSchool(school);
	}

}
