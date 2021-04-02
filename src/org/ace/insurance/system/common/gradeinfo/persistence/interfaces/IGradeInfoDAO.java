package org.ace.insurance.system.common.gradeinfo.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.gradeinfo.GradeInfo;
import org.ace.java.component.persistence.exception.DAOException;

public interface IGradeInfoDAO {

	public void insert(GradeInfo gradeInfo) throws DAOException;

	public void delete(GradeInfo grdeInfo) throws DAOException;

	public void update(GradeInfo grdeInfo) throws DAOException;

	public GradeInfo findById(String gradeInfoId) throws DAOException;

	public List<GradeInfo> findAllGradeInfo() throws DAOException;

	public boolean checkExistingGradeInfo(GradeInfo grade) throws DAOException;

}
