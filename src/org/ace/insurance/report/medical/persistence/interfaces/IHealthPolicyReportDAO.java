package org.ace.insurance.report.medical.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.medical.HealthPolicyReportDTO;
import org.ace.insurance.web.manage.report.medical.HealthPolicyReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IHealthPolicyReportDAO {
	public List<HealthPolicyReportDTO> findHealthPolicyReportDTO(HealthPolicyReportCriteria criteria) throws DAOException;
}
