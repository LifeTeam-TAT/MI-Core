package org.ace.insurance.report.life.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifeDailyIncomeReportCriteria;
import org.ace.insurance.report.life.LifeDailyIncomeReportDTO;
import org.ace.insurance.report.life.LifeDailyPremiumIncomeReportDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeDailyIncomeReportDAO {
	public List<LifeDailyIncomeReportDTO> find(LifeDailyIncomeReportCriteria lifeDailyCriteria) throws DAOException;

	public List<LifeDailyPremiumIncomeReportDTO> findPremium(LifeDailyIncomeReportCriteria lifeDailyCriteria) throws DAOException;
}
