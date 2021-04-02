package org.ace.insurance.report.life.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.life.LifeClaimStatusReport;
import org.ace.insurance.report.life.LifeClaimStatusReportCriteria;
import org.ace.insurance.report.life.persistence.interfaces.ILifeClaimStatusReportDAO;
import org.ace.insurance.report.life.service.interfaces.ILifeClaimStatusReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifeClaimStatusReportService")
public class LifeClaimStatusReportService extends BaseService implements ILifeClaimStatusReportService {
	@Resource(name = "LifeClaimStatusReportDAO")
	private ILifeClaimStatusReportDAO lifeClaimStatusReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimStatusReport> findLifeClaimStatusReport(LifeClaimStatusReportCriteria criteria) {
		List<LifeClaimStatusReport> result = null;
		try {
			result = lifeClaimStatusReportDAO.findLifeClaimStatusReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeClaimStatusReport by criteria.", e);
		}
		return result;
	}
}
