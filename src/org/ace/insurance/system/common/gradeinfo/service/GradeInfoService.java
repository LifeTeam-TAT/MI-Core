package org.ace.insurance.system.common.gradeinfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.gradeinfo.GradeInfo;
import org.ace.insurance.system.common.gradeinfo.persistence.interfaces.IGradeInfoDAO;
import org.ace.insurance.system.common.gradeinfo.service.interfaces.IGradeInfoService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "GradeInfoService")
public class GradeInfoService implements IGradeInfoService {

	@Resource(name = "GradeInfoDAO")
	private IGradeInfoDAO gradeInfoDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewGradeInfo(GradeInfo gradeInfo) {
		try {
			gradeInfoDAO.insert(gradeInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new grade", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteGradeInfo(GradeInfo grdeInfo) {
		try {
			gradeInfoDAO.delete(grdeInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new grade", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateGradeInfo(GradeInfo grdeInfo) {
		try {
			gradeInfoDAO.update(grdeInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new grade", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GradeInfo findById(String gradeInfoId) {
		GradeInfo result = null;
		try {
			result = gradeInfoDAO.findById(gradeInfoId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new grade", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<GradeInfo> findAllGradeInfo() {
		List<GradeInfo> result = null;
		try {
			result = gradeInfoDAO.findAllGradeInfo();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find All GradeInfo", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkExistGradeInfo(GradeInfo gradeInfo) {

		return gradeInfoDAO.checkExistingGradeInfo(gradeInfo);
	}
}
