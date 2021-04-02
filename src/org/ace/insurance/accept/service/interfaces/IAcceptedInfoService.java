/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.accept.service.interfaces;

import java.util.List;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.common.ReferenceType;

public interface IAcceptedInfoService {
	public void addNewAcceptedInfo(AcceptedInfo acceptedInfo);

	public void updateAcceptedInfo(AcceptedInfo acceptedInfo);

	public void deleteAcceptedInfo(AcceptedInfo acceptedInfo);

	public AcceptedInfo findAcceptedInfoByReferenceNo(String referenceNo);

	public List<AcceptedInfo> findAllAcceptedInfo();
}
