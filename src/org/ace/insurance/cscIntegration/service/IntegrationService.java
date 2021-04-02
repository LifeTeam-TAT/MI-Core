package org.ace.insurance.cscIntegration.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.cscIntegration.Integration;
import org.ace.insurance.cscIntegration.persistence.interfaces.IIntegrationDAO;
import org.ace.insurance.cscIntegration.service.interfaces.IIntegrationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;

@Service(value = "IntegrationService")
public class IntegrationService implements IIntegrationService {
	@Resource(name = "IntegrationDAO")
	private IIntegrationDAO integrationDAO;

	public List<Integration> findCSCPolicyByAcePolicyNo(String policyNo, InsuranceType type) {
		List<Integration> result = null;
		try {
			result = integrationDAO.findByAcePolicyNo(policyNo, type);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find CSC policy by AcePolicyNo" + policyNo, e);
		}
		return result;
	}

}
