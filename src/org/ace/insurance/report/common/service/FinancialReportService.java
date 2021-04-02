package org.ace.insurance.report.common.service;

import javax.annotation.Resource;

import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.FinancialReportCriteria;
import org.ace.insurance.report.common.persistence.interfaces.IFinancialReportDAO;
import org.ace.insurance.report.common.persistence.interfaces.IWorkFlowReportDAO;
import org.ace.insurance.report.common.service.interfaces.IFinancialReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "FinancialReportService")
public class FinancialReportService implements IFinancialReportService {

	@Resource(name = "FinancialReportDAO")
	private IFinancialReportDAO financialReportDAO;
	@Resource(name = "WorkFlowReportDAO")
	private IWorkFlowReportDAO workFlowReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public FinancialReport findFinancialReport(FinancialReportCriteria criteria) {
		FinancialReport result = null;
		try {
			result = financialReportDAO.findFinancialReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public FinancialReport findAllWorkFlowStatus() {
		FinancialReport result = null;
		try {
			result = workFlowReportDAO.findAllWorkFlowStatus();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find WorkFlowStatus of All Proposal Status Report.", e);
		}
		return result;
	}
}
