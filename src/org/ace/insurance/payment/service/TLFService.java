/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.payment.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.payment.TLF;
import org.ace.insurance.payment.persistence.interfacs.ITLFDAO;
import org.ace.insurance.payment.service.interfaces.ITLFService;
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
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "TLFService")
public class TLFService extends BaseService implements ITLFService {

	@Resource(name = "TLFDAO")
	private ITLFDAO tlfDAO;

	public void addNewTlf(TLF tlf) throws SystemException {
		try {
			// tlfDAO.insert(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SalePointIncomeReportDTO> findSalePointIncomeByCriteria(SalePointIncomeCriteria sPIncomeCriteria) throws SystemException {
		List<SalePointIncomeReportDTO> result = null;
		try {
			result = tlfDAO.findSalePointIncomeByCriteria(sPIncomeCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Search SalePoint Income By Criterial", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DailyIncomeReportDTO> findDailyIncomeReportBySalePointCriteria(TlfCriteria tlfCriteria) throws SystemException {
		List<DailyIncomeReportDTO> resultList = new ArrayList<DailyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findDailyReportDTO(tlfCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find daily income report by criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AccountAndLifeCancelReportDTO> findAccountAndLifeCancelReportDTO(TlfCriteria criteria) throws SystemException {
		List<AccountAndLifeCancelReportDTO> resultList = new ArrayList<AccountAndLifeCancelReportDTO>();
		try {
			resultList = tlfDAO.findCancelReportDTO(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Account and Life Dept Cancel Report", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AccountManualReceiptDTO> findAccountManualReceiptListByCriteria(TlfCriteria tlfCriteria) throws SystemException {
		List<AccountManualReceiptDTO> resultList = new ArrayList<AccountManualReceiptDTO>();
		try {
			resultList = tlfDAO.findAccountManualReceiptDTO(tlfCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Account Manual Receipt Report by Criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MonthlyIncomeReportDTO> findMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws SystemException {
		List<MonthlyIncomeReportDTO> resultList = new ArrayList<MonthlyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findMonthlyIncomeReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Monthly Reprot Receipt Report by Criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<FarmerMonthlyIncomeReportDTO> findFarmerMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws SystemException {
		List<FarmerMonthlyIncomeReportDTO> resultList = new ArrayList<FarmerMonthlyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findFarmerMonthlyIncomeReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Farmer Monthly Report by Criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<PAMonthlyIncomeReportDTO> findPAMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws SystemException {
		List<PAMonthlyIncomeReportDTO> resultList = new ArrayList<PAMonthlyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findPAMonthlyIncomeReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find PA Monthly Reprot Report by Criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MonthlyIncomeReportDTO> findQuantityAndTotalSIDetails(MonthlyIncomeReportCriteria criteria) throws SystemException {
		List<MonthlyIncomeReportDTO> resultList = new ArrayList<MonthlyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findQuantityAndTotalSIDetails(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Monthly Details Report by Criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<GroupLifeMonthlyIncomeReportDTO> findGLMonthlyIncomeReport(GroupLifeMonthlyIncomeReportCriteria criteria) throws SystemException {
		List<GroupLifeMonthlyIncomeReportDTO> resultList = new ArrayList<GroupLifeMonthlyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findGLMonthlyIncomeReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Group Life Monthly Details Report by Criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SnakeBiteMonthlyIncomeReportDTO> findSNBMonthlyIncomeReport(SnakeBiteMonthlyIncomeReportCriteria criteria) throws SystemException {
		List<SnakeBiteMonthlyIncomeReportDTO> resultList = new ArrayList<SnakeBiteMonthlyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findSNBMonthlyIncomeReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Snake Bite Monthly Details Report by Criteria", e);
		}
		return resultList;
	}

	@Override
	public List<StudentMontlyIncomeReportDTO> findStudentMontlyIncomeReport(StudentMontlyIncomeReportCriteria criteria) throws DAOException {
		List<StudentMontlyIncomeReportDTO> resultList = new ArrayList<StudentMontlyIncomeReportDTO>();
		try {
			resultList = tlfDAO.findStudentMontlyIncomeReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Monthly Reprot Receipt Report by Criteria", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CeoShortTermLifeDTO> findCeoShortEndowLifePolicyMonthlyReport(MonthlyIncomeReportCriteria criteria) throws SystemException {
		List<CeoShortTermLifeDTO> resultList = new ArrayList<CeoShortTermLifeDTO>();
		try {
			resultList = tlfDAO.findCeoShortEndowLifePolicyMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Group Life Monthly Details Report by Criteria", e);
		}
		return resultList;
	}

}