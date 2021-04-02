package org.ace.insurance.report.life.service.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifeClaimStatusReport;
import org.ace.insurance.report.life.LifeClaimStatusReportCriteria;

public interface ILifeClaimStatusReportService {

	public List<LifeClaimStatusReport> findLifeClaimStatusReport(LifeClaimStatusReportCriteria criteria);

}
