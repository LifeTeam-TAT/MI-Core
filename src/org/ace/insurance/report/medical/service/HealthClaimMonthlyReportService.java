package org.ace.insurance.report.medical.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.HealthClaimMonthlyReport;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthClaimMonthlyReportDAO;
import org.ace.insurance.report.medical.service.interfaces.IHealthClaimMonthlyReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "HealthClaimMonthlyReportService")
public class HealthClaimMonthlyReportService extends BaseService implements IHealthClaimMonthlyReportService {

	@Resource(name = "HealthClaimMonthlyReportDAO")
	private IHealthClaimMonthlyReportDAO healthClaimMonthlyReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthClaimMonthlyReport> findHealthClaimMonthlyReport(MonthlyReportCriteria criteria) {
		List<HealthClaimMonthlyReport> result = null;
		try {
			result = healthClaimMonthlyReportDAO.findHealthClaimMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthClaimMonthlyReport", e);
		}
		return result;
	}
}
