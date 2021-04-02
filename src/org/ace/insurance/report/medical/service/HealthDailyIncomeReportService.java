package org.ace.insurance.report.medical.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.medical.HealthDailyIncomeReportDTO;
import org.ace.insurance.report.medical.HealthDailyPremiumReportDTO;
import org.ace.insurance.report.medical.HealthDailyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthDailyIncomeReportDAO;
import org.ace.insurance.report.medical.service.interfaces.IHealthDailyIncomeReportService;
import org.ace.insurance.web.manage.report.medical.HealthDailyIncomeReportCriteria;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "HealthDailyIncomeReportService")
public class HealthDailyIncomeReportService extends BaseService implements IHealthDailyIncomeReportService {

	@Resource(name = "HealthDailyIncomeReportDAO")
	private IHealthDailyIncomeReportDAO healthDailyIncomeReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyReportDTO> findHealthDailyReportDTO(HealthDailyIncomeReportCriteria criteria) {
		List<HealthDailyReportDTO> resultList = new ArrayList<HealthDailyReportDTO>();
		try {
			resultList = healthDailyIncomeReportDAO.findHealthDailyReportDTO(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthDailyIncomeReportDTO", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyPremiumReportDTO> findHealthDailyPremiumReportDTO(HealthDailyIncomeReportCriteria criteria) {
		List<HealthDailyPremiumReportDTO> resultList = new ArrayList<HealthDailyPremiumReportDTO>();
		try {
			resultList = healthDailyIncomeReportDAO.findHealthDailyPremiumReportDTO(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthDailyIncomeReportDTO", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyIncomeReportDTO> findMedicalClaimRequest(HealthProposalReportCriteria criteria) {
		List<HealthDailyIncomeReportDTO> resultList = new ArrayList<HealthDailyIncomeReportDTO>();
		try {
			resultList = healthDailyIncomeReportDAO.findMedicalClaimRequest(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthDailyIncomeReportDTO", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthDailyIncomeReportDTO> findMedicalClaimPaymentReport(HealthProposalReportCriteria criteria) {
		List<HealthDailyIncomeReportDTO> resultList = new ArrayList<HealthDailyIncomeReportDTO>();
		try {
			resultList = healthDailyIncomeReportDAO.findMedicalClaimPaymentReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthDailyIncomeReportDTO", e);
		}
		return resultList;
	}

}
