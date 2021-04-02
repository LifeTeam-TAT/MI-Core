/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimaccept.service.interfaces;

import java.util.List;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.ReferenceType;


public interface IClaimAcceptedInfoService {
	public void addNewClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo);

	public void updateClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo);

	public void deleteClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo) ;

	public ClaimAcceptedInfo findClaimAcceptedInfoByReferenceNo(String referenceNo, ReferenceType referenceType);

	public List<ClaimAcceptedInfo> findAllClaimAcceptedInfo() ;
}
