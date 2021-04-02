package org.ace.insurance.life.claim.service;

import javax.annotation.Resource;

import org.ace.insurance.life.claim.LifeDisabilityClaim;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimDAO;
import org.ace.insurance.life.claim.service.interfaces.ILifeDisabilityClaimService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Service(value = "LifeDisabilityClaimService")
public class LifeDisabilityClaimService implements ILifeDisabilityClaimService {

	@Resource(name = "LifeClaimDAO")
	private ILifeClaimDAO claimDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeDisabilityClaim findDisabilityClaimByRequestId(String claimRequestid) {
		LifeDisabilityClaim disabilityClaim;
		try {
			disabilityClaim = claimDAO.findDisabilityByRequestId(claimRequestid);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find DisabilityClaim by ClaimRequestId : " + claimRequestid, e);

		}
		return disabilityClaim;
	}
}
