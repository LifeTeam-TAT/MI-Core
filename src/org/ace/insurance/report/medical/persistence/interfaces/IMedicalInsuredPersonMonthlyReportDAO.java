package org.ace.insurance.report.medical.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalInusuredPersonMonthlyReportDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalInsuredPersonMonthlyReportDAO {
	public List<MedicalInusuredPersonMonthlyReportDTO> find(MonthlyReportCriteria criteria) throws DAOException;
}
