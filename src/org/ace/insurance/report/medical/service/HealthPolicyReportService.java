package org.ace.insurance.report.medical.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.report.medical.HealthPolicyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthPolicyReportDAO;
import org.ace.insurance.report.medical.service.interfaces.IHealthPolicyReportService;
import org.ace.insurance.web.manage.report.medical.HealthPolicyReportCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "HealthPolicyReportService")
public class HealthPolicyReportService extends BaseService implements IHealthPolicyReportService {

	@Resource(name = "HealthPolicyReportDAO")
	private IHealthPolicyReportDAO healthPolicyReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthPolicyReportDTO> findHealthPolicyReportDTO(HealthPolicyReportCriteria criteria) {
		List<HealthPolicyReportDTO> resultList = null;
		try {
			resultList = healthPolicyReportDAO.findHealthPolicyReportDTO(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthPolicyReportDTO", e);
		}
		return resultList;
	}
}