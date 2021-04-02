package org.ace.insurance.report.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.common.WorkFlowStatusCriteria;
import org.ace.insurance.report.common.WorkFlowStatusReport;
import org.ace.insurance.report.common.persistence.interfaces.IWorkFlowReportDAO;
import org.ace.insurance.report.common.service.interfaces.IWorkFlowReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "WorkFlowReportService")
public class WorkFlowReportService implements IWorkFlowReportService {

	@Resource(name = "WorkFlowReportDAO")
	private IWorkFlowReportDAO workFlowReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkFlowStatusReport> findStatusReport(WorkFlowStatusCriteria criteria) {
		List<WorkFlowStatusReport> result = null;
		try {
			result = workFlowReportDAO.findStatusReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find details WorkFlow Status of " + criteria.getWorkFlowType() + " Proposal.", e);
		}
		return result;
	}
}
