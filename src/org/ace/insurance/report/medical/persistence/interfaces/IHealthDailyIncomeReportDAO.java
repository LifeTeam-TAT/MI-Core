package org.ace.insurance.report.medical.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.medical.HealthDailyIncomeReportDTO;
import org.ace.insurance.report.medical.HealthDailyPremiumReportDTO;
import org.ace.insurance.report.medical.HealthDailyReportDTO;
import org.ace.insurance.web.manage.report.medical.HealthDailyIncomeReportCriteria;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IHealthDailyIncomeReportDAO {
	public List<HealthDailyReportDTO> findHealthDailyReportDTO(HealthDailyIncomeReportCriteria criteria) throws DAOException;

	public List<HealthDailyPremiumReportDTO> findHealthDailyPremiumReportDTO(HealthDailyIncomeReportCriteria criteria) throws DAOException;

	public List<HealthDailyIncomeReportDTO> findMedicalClaimRequest(HealthProposalReportCriteria criteria) throws DAOException;

	public List<HealthDailyIncomeReportDTO> findMedicalClaimPaymentReport(HealthProposalReportCriteria criteria) throws DAOException;
}
