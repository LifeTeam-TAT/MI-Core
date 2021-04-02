package org.ace.insurance.report.medical.service.interfaces;

import java.util.List;

import org.ace.insurance.report.medical.HealthPolicyReportDTO;
import org.ace.insurance.web.manage.report.medical.HealthPolicyReportCriteria;

public interface IHealthPolicyReportService {
	public List<HealthPolicyReportDTO> findHealthPolicyReportDTO(HealthPolicyReportCriteria criteria);
}
