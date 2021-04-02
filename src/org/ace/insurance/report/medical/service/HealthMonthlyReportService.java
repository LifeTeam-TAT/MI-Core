package org.ace.insurance.report.medical.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthMonthlyReport;
import org.ace.insurance.report.medical.HealthMonthlyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthMonthlyReportDAO;
import org.ace.insurance.report.medical.service.interfaces.IHealthMonthlyReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "HealthMonthlyReportService")
public class HealthMonthlyReportService extends BaseService implements IHealthMonthlyReportService {

	@Resource(name = "HealthMonthlyReportDAO")
	private IHealthMonthlyReportDAO healthMonthlyReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthMonthlyReport> findHealthMonthlyReport(MonthlyReportCriteria criteria) {
		List<HealthMonthlyReport> resultList = new ArrayList<HealthMonthlyReport>();
		try {
			resultList = healthMonthlyReportDAO.findHealthMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthMonthlyReportDTO", e);
		}
		return resultList;
	}
}
