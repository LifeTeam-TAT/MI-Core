package org.ace.insurance.report.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.config.persistence.interfaces.IReportConfigDAO;
import org.ace.insurance.report.config.service.interfaces.IReportConfigService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This interface serves as the Service to configure report by access SQL Agent
 * Job.
 * 
 * @author HS
 * @since 1.0.0
 * @date 2015/03/18
 */

@Service(value = "ReportConfigService")
public class ReportConfigService implements IReportConfigService {
	@Resource(name = "ReportConfigDAO")
	private IReportConfigDAO reportConfigDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void configReport(List<String> reportType) {
		try {
			reportConfigDAO.configReport(reportType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to configReport.", e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
