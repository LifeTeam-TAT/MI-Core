package org.ace.insurance.system.common.gradeinfo.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.gradeinfo.GradeInfo;

public interface IGradeInfoService {

	public void addNewGradeInfo(GradeInfo gradeInfo);

	public void deleteGradeInfo(GradeInfo grdeInfo);

	public void updateGradeInfo(GradeInfo grdeInfo);

	public GradeInfo findById(String gradeInfoId);;

	public List<GradeInfo> findAllGradeInfo();

	public boolean checkExistGradeInfo(GradeInfo gradeInfo);
}
