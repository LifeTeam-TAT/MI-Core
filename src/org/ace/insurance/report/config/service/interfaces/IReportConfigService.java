package org.ace.insurance.report.config.service.interfaces;

import java.util.List;

/**
 * This interface serves as the Service to configure report by access SQL Agent
 * Job.
 * 
 * @author HS
 * @since 1.0.0
 * @date 2015/03/18
 */
public interface IReportConfigService {
	public void configReport(List<String> reportType);
}
