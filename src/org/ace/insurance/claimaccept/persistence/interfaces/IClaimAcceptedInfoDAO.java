/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimaccept.persistence.interfaces;

import java.util.List;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.ReferenceType;
import org.ace.java.component.persistence.exception.DAOException;

public interface IClaimAcceptedInfoDAO {
	public void insert(ClaimAcceptedInfo claimAcceptedInfo) throws DAOException;

	public void update(ClaimAcceptedInfo claimAcceptedInfo) throws DAOException;

	public void delete(ClaimAcceptedInfo claimAcceptedInfo) throws DAOException;

	public ClaimAcceptedInfo findByReferenceNo(String referenceNo, ReferenceType referenceType) throws DAOException;

	public List<ClaimAcceptedInfo> findAll() throws DAOException;
}
