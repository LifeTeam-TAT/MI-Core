package org.ace.insurance.report.stampfee.service.interfaces;

import java.util.List;

import org.ace.insurance.report.stampfee.StampFeeCriteria;
import org.ace.insurance.report.stampfee.StampFeeReport;

public interface IStampFeeService {
	public List<StampFeeReport> find(StampFeeCriteria criteria);
	/*public void generateStampFeeReport(String fullTemplateFilePath,
			StampFeeCriteria stampFeeCriteria, List<Branch> branchList, String dirPath, String fileName)throws Exception;*/
}
