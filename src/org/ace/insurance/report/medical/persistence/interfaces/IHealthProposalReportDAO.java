package org.ace.insurance.report.medical.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.medical.HealthProposalReportDTO;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IHealthProposalReportDAO {
	public List<HealthProposalReportDTO> findHealthProposalReportDTO(HealthProposalReportCriteria criteria) throws DAOException;
}
