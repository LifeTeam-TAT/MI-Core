package org.ace.insurance.report.ibrb.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.common.ReportCriteria;
import org.ace.insurance.report.ibrb.CriticalIBRBMonthlyReport;
import org.ace.insurance.report.ibrb.HealthIBRBMonthlyReport;
import org.ace.insurance.report.ibrb.MicroHealthIBRBMonthlyReport;
import org.ace.insurance.report.ibrb.persistence.interfaces.IIBRBReportDAO;
import org.ace.insurance.report.ibrb.service.interfaces.IIBRBReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "IBRBReportService")
public class IBRBReportService extends BaseService implements IIBRBReportService {

	@Resource(name = "IBRBReportDAO")
	private IIBRBReportDAO IBRBReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthIBRBMonthlyReport> findHealthIBRBMonthlyReports(ReportCriteria criteria) {
		List<HealthIBRBMonthlyReport> resultList = new ArrayList<HealthIBRBMonthlyReport>();
		try {
			resultList = IBRBReportDAO.findHealthIBRBMonthlyReports(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find HealthIBRBReport", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CriticalIBRBMonthlyReport> findCriticalIBRBMonthlyReports(ReportCriteria criteria) {
		List<CriticalIBRBMonthlyReport> resultList = new ArrayList<CriticalIBRBMonthlyReport>();
		try {
			resultList = IBRBReportDAO.findCriticalIBRBMonthlyReports(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find CriticalIBRBMonthlyReport", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MicroHealthIBRBMonthlyReport> findMicroHealthIBRBMonthlyReports(ReportCriteria criteria) {
		List<MicroHealthIBRBMonthlyReport> resultList = new ArrayList<MicroHealthIBRBMonthlyReport>();
		try {
			resultList = IBRBReportDAO.findMicroHealthIBRBMonthlyReports(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find MicroHealthIBRBMonthlyReport", e);
		}
		return resultList;
	}

}
