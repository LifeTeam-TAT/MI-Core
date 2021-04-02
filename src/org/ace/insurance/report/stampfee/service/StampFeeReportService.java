package org.ace.insurance.report.stampfee.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.stampfee.StampFeeCriteria;
import org.ace.insurance.report.stampfee.StampFeeReport;
import org.ace.insurance.report.stampfee.persistence.interfaces.IStampFeeReportDAO;
import org.ace.insurance.report.stampfee.service.interfaces.IStampFeeService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "StampFeeReportService")
public class StampFeeReportService implements IStampFeeService {
	@Resource(name = "StampFeeReportDAO")
	private IStampFeeReportDAO stampFeeReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<StampFeeReport> find(StampFeeCriteria criteria) {
		List<StampFeeReport> result = null;
		try {
			result = stampFeeReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find StampFee by criteria.", e);
		}
		return result;
	}
}
