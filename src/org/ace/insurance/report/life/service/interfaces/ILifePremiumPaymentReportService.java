package org.ace.insurance.report.life.service.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifePremiumPaymentCriteria;
import org.ace.insurance.report.life.LifePremiumPaymentReport;

public interface ILifePremiumPaymentReportService {
	public List<LifePremiumPaymentReport> findPremiumPayment(LifePremiumPaymentCriteria premiumPaymentCriteria, List<String> productIdList);

	public void generateLifePremiumPaymentReport(List<LifePremiumPaymentReport> premiumPaymentList, String dirPath, String fileName, String branchName) throws Exception;

}
