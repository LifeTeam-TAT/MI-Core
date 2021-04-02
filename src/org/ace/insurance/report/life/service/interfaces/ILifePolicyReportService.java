package org.ace.insurance.report.life.service.interfaces;

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

public interface ILifePolicyReportService {

	public List<LifePolicyReport> findLifePolicyReport(LifePolicyReportCriteria lifePolicyCriteria, List<String> productIdList);

	public void generateLifePolicyReport(List<LifePolicyReport> reports, String dirPath, String fileName, String branch);

	public void generatePersonalAccidentPolicyReport(List<PersonalAccidentPolicyReport> reports, String dirPath, String fileName);

	public List<PersonalAccidentPolicyReport> findPersonalAccidentPolicyReport(LifePolicyReportCriteria criteria);

	public List<FarmerMonthlyReport> findFarmerMonthlyReport(MonthlyReportCriteria criteria);

	public List<ShortEndowLifePolicyReport> findShortEndowLifePolicyReport(LifePolicyReportCriteria lifePolicyCriteria);

	public List<ShortEndownLifeMonthlyReportDTO> findShortEndowLifePolicyMonthlyReport(MonthlyIncomeReportCriteria criteria);

	public List<ShortEndownLifeMonthlyReportDTO> findPublicLifePolicyMonthlyReport(MonthlyIncomeReportCriteria criteria);

	public List<SportManMonthlyReportDTO> findSportManMonthlyReport(MonthlyIncomeReportCriteria criteria);

}
