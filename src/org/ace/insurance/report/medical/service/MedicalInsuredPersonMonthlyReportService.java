package org.ace.insurance.report.medical.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalInusuredPersonMonthlyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IMedicalInsuredPersonMonthlyReportDAO;
import org.ace.insurance.report.medical.service.interfaces.IMedicalInsuredPersonMonthlyReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalInsuredPersonMonthlyReportService")
public class MedicalInsuredPersonMonthlyReportService extends BaseService implements IMedicalInsuredPersonMonthlyReportService {
	@Resource(name = "MedicalInsuredPersonMonthlyReportDAO")
	private IMedicalInsuredPersonMonthlyReportDAO healthMonthlyReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalInusuredPersonMonthlyReportDTO> find(MonthlyReportCriteria criteria) {
		List<MedicalInusuredPersonMonthlyReportDTO> resultList = new ArrayList<MedicalInusuredPersonMonthlyReportDTO>();
		try {
			resultList = healthMonthlyReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthMonthlyReportDTO", e);
		}
		return resultList;
	}

}
