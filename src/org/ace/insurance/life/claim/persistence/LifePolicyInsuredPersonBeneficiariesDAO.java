package org.ace.insurance.life.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.claim.persistence.interfaces.ILifePolicyInsuredPersonBeneficiariesDAO;
import org.ace.insurance.life.policy.BeneficiaryStatus;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Repository("PolicyInsuredPersonBeneficiariesDAO")
public class LifePolicyInsuredPersonBeneficiariesDAO extends BasicDAO implements ILifePolicyInsuredPersonBeneficiariesDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) throws DAOException {
		try {
			em.persist(policyInsuredPersonBeneficiaries);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert policyInsuredPersonBeneficiaries", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) throws DAOException {
		try {
			em.merge(policyInsuredPersonBeneficiaries);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update policyInsuredPersonBeneficiaries", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) throws DAOException {
		try {
			policyInsuredPersonBeneficiaries = em.merge(policyInsuredPersonBeneficiaries);
			em.remove(policyInsuredPersonBeneficiaries);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update policyInsuredPersonBeneficiaries", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonBeneficiaries findById(String id) throws DAOException {
		PolicyInsuredPersonBeneficiaries result = null;
		try {
			result = em.find(PolicyInsuredPersonBeneficiaries.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find policyInsuredPersonBeneficiaries", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPersonBeneficiaries> findAll() throws DAOException {
		List<PolicyInsuredPersonBeneficiaries> result = null;
		try {
			Query q = em.createNamedQuery("PolicyInsuredPersonBeneficiaries.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of policyInsuredPersonBeneficiaries", pe);
		}
		return result;
	}

	@Override
	public void updateStatus(BeneficiaryStatus beneficiarystatus, String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("PolicyInsuredPersonBeneficiaries.UpdateStatus");
			q.setParameter("beneficiaryStatus", beneficiarystatus);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update status : ", pe);
		}

	}

}
