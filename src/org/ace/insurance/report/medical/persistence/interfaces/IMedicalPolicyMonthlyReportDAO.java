package org.ace.insurance.report.medical.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalPolicyMonthlyReportDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalPolicyMonthlyReportDAO {

	public List<MedicalPolicyMonthlyReportDTO> find(MonthlyReportCriteria criteria) throws DAOException;

}
