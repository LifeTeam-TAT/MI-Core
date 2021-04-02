package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.policy.BeneficiaryStatus;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.java.component.persistence.exception.DAOException;
/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

public interface ILifePolicyInsuredPersonBeneficiariesDAO {
	public void insert(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) throws DAOException;
	public void update(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) throws DAOException;
	public void delete(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) throws DAOException;
	public PolicyInsuredPersonBeneficiaries findById(String id) throws DAOException;
	public List<PolicyInsuredPersonBeneficiaries> findAll() throws DAOException;
	public void updateStatus(BeneficiaryStatus beneficiarystatus, String id) throws DAOException;

}
