package org.ace.insurance.report.life.service.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifeClaimRegisterReport;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;

public interface ILifeClaimRegisterReportService {
	public List<LifeClaimRegisterReport> findLifeClaimRegisterReports(EnquiryCriteria criteria, List<String> productIdList);

	public void generateReport(List<LifeClaimRegisterReport> reports, String fullPath, String fileName);

	public void generateLifeClaimRegisterReport(String fullTemplateFilePath, EnquiryCriteria criteria, List<String> productIdList, List<Branch> branchList, String dirPath,
			String fileName);
}
