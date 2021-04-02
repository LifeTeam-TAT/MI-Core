package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.LifeDisabilityClaim;
import org.ace.java.component.persistence.exception.DAOException;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

public interface ILifeClaimDAO {
	public void insert(LifeClaim claim) throws DAOException;

	public void update(LifeClaim claim) throws DAOException;

	public void delete(LifeClaim claim) throws DAOException;

	public LifeClaim findById(String id) throws DAOException;

	public List<LifeClaim> findAll() throws DAOException;

	public void addAttachment(LifeClaim claim) throws DAOException;

	public void updateServiceCharges(int serviceCharges, String id) throws DAOException;

	public LifeClaim findByClaimRequestId(String id) throws DAOException;

	public LifeDisabilityClaim findDisabilityByRequestId(String claimRequestId) throws DAOException;

	public LifeClaimInsuredPerson findLifeClaimInsuredPersonByPolicyInsuredPersonId(String policyInsuredPersonId) throws DAOException;

	// public List<LCL001> findByEnquiryCriteria(LCL001 criteria) throws
	// DAOException;
	public String findStatusById(String id) throws DAOException;

	public LifeClaim findLifeClaimPortalById(String id) throws DAOException;
}
