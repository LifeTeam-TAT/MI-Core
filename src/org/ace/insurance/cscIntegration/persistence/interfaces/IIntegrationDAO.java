package org.ace.insurance.cscIntegration.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.cscIntegration.Integration;
import org.ace.java.component.persistence.exception.DAOException;

public interface IIntegrationDAO {
	public List<Integration> findByAcePolicyNo(String policyNo, InsuranceType type) throws DAOException;
}
