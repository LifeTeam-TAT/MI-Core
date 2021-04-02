package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyClaimDAO {

	List<LifePolicyClaim> findLifepolicyClaimByPolicyNo(String policyNo) throws DAOException;

	void insert(LifePolicyClaim lifePolicyClaim);

	void update(LifePolicyClaim lifePolicyClaim);

	int findCountByPolicyNo(String policyNo) throws DAOException;

}