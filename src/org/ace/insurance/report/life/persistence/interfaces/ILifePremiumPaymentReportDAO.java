package org.ace.insurance.report.life.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifePremiumPaymentCriteria;
import org.ace.insurance.report.life.LifePremiumPaymentReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePremiumPaymentReportDAO {
	public List<LifePremiumPaymentReport> find(LifePremiumPaymentCriteria premiumPaymentCriteria, List<String> productIdList) throws DAOException;

}
