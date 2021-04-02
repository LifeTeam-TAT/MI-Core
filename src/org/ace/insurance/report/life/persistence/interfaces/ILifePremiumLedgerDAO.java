package org.ace.insurance.report.life.persistence.interfaces;

import org.ace.insurance.report.life.LifePremiumLedgerReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePremiumLedgerDAO {
	public LifePremiumLedgerReport findLifePremiumLedgerReport(String lifePolicyId) throws DAOException;

}
