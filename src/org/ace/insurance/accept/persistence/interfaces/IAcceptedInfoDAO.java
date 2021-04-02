/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.accept.persistence.interfaces;

import java.util.List;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAcceptedInfoDAO {
	public void insert(AcceptedInfo AcceptedInfo) throws DAOException;

	public void update(AcceptedInfo AcceptedInfo) throws DAOException;

	public void delete(AcceptedInfo AcceptedInfo) throws DAOException;

	public AcceptedInfo findByReferenceNo(String referenceNo) throws DAOException;

	public List<AcceptedInfo> findAll() throws DAOException;
}
