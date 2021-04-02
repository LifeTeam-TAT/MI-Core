package org.ace.insurance.payment.service.interfaces;

import java.util.List;

import org.ace.insurance.report.TLF.AccountAndLifeCancelReportDTO;
import org.ace.insurance.report.TLF.AccountManualReceiptDTO;
import org.ace.insurance.report.TLF.CeoShortTermLifeDTO;
import org.ace.insurance.report.TLF.DailyIncomeReportDTO;
import org.ace.insurance.report.TLF.FarmerMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.GroupLifeMonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.GroupLifeMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.MonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.PAMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.SalePointIncomeCriteria;
import org.ace.insurance.report.TLF.SalePointIncomeReportDTO;
import org.ace.insurance.report.TLF.SnakeBiteMonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.SnakeBiteMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.StudentMontlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.StudentMontlyIncomeReportDTO;
import org.ace.insurance.report.TLF.TlfCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;

public interface ITLFService {
	// public void addNewTlf(TLF tlf) throws SystemException;
	public List<FarmerMonthlyIncomeReportDTO> findFarmerMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws SystemException;

	public List<PAMonthlyIncomeReportDTO> findPAMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws SystemException;

	public List<SalePointIncomeReportDTO> findSalePointIncomeByCriteria(SalePointIncomeCriteria sPIncomeCriteria) throws SystemException;

	public List<DailyIncomeReportDTO> findDailyIncomeReportBySalePointCriteria(TlfCriteria tlfCriteria) throws SystemException;

	public List<AccountAndLifeCancelReportDTO> findAccountAndLifeCancelReportDTO(TlfCriteria criteria) throws SystemException;

	public List<AccountManualReceiptDTO> findAccountManualReceiptListByCriteria(TlfCriteria criteria) throws SystemException;

	public List<MonthlyIncomeReportDTO> findMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws SystemException;

	public List<MonthlyIncomeReportDTO> findQuantityAndTotalSIDetails(MonthlyIncomeReportCriteria criteria) throws SystemException;

	public List<GroupLifeMonthlyIncomeReportDTO> findGLMonthlyIncomeReport(GroupLifeMonthlyIncomeReportCriteria criteria) throws SystemException;

	public List<SnakeBiteMonthlyIncomeReportDTO> findSNBMonthlyIncomeReport(SnakeBiteMonthlyIncomeReportCriteria criteria) throws SystemException;

	public List<StudentMontlyIncomeReportDTO> findStudentMontlyIncomeReport(StudentMontlyIncomeReportCriteria criteria) throws DAOException;

	public List<CeoShortTermLifeDTO> findCeoShortEndowLifePolicyMonthlyReport(MonthlyIncomeReportCriteria criteria) throws SystemException;
}
