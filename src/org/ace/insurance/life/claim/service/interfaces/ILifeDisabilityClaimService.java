package org.ace.insurance.life.claim.service.interfaces;

import org.ace.insurance.life.claim.LifeDisabilityClaim;
/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */


public interface ILifeDisabilityClaimService {
	public LifeDisabilityClaim findDisabilityClaimByRequestId(String claimRequestid);

}
