package org.ace.insurance.report.life.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.farmer.FarmerMonthlyReport;
import org.ace.insurance.report.life.LifePolicyReport;
import org.ace.insurance.report.life.LifePolicyReportCriteria;
import org.ace.insurance.report.personalAccident.PersonalAccidentPolicyReport;
import org.ace.insurance.report.shortEndowLife.ShortEndowLifePolicyReport;
import org.ace.insurance.report.sportMan.SportManMonthlyReportDTO;
import org.ace.insurance.web.manage.report.shortEndowLife.ShortEndownLifeMonthlyReportDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyReportDAO {
	public List<LifePolicyReport> findLifePolicyReport(LifePolicyReportCriteria lifePolicyReportCriteria, List<String> productIdList) throws DAOException;

	public List<PersonalAccidentPolicyReport> findPersonalAccidentPolicyReport(LifePolicyReportCriteria lifePolicyReportCriteria) throws DAOException;

	public List<FarmerMonthlyReport> findFarmerMonthlyReport(MonthlyReportCriteria criteria) throws DAOException;

	public List<ShortEndowLifePolicyReport> findShortEndowLifePolicyReport(LifePolicyReportCriteria lifePolicyReportCriteria) throws DAOException;

	public List<ShortEndownLifeMonthlyReportDTO> findShortEndowMonthlyReport(MonthlyIncomeReportCriteria criteria) throws DAOException;

	public List<ShortEndownLifeMonthlyReportDTO> findPublicLifeMonthlyReport(MonthlyIncomeReportCriteria criteria) throws DAOException;

	public List<SportManMonthlyReportDTO> findSportManMonthlyReport(MonthlyIncomeReportCriteria criteria) throws DAOException;
}
