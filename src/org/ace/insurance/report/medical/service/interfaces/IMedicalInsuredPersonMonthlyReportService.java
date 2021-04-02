package org.ace.insurance.report.medical.service.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalInusuredPersonMonthlyReportDTO;

public interface IMedicalInsuredPersonMonthlyReportService {
	public List<MedicalInusuredPersonMonthlyReportDTO> find(MonthlyReportCriteria criteria);

}
