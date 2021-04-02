/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.accept.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.persistence.interfaces.IAcceptedInfoDAO;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.ReferenceType;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "AcceptedInfoService")
public class AcceptedInfoService extends BaseService implements IAcceptedInfoService {

	@Resource(name = "AcceptedInfoDAO")
	private IAcceptedInfoDAO acceptedInfoDAO;

	public void addNewAcceptedInfo(AcceptedInfo acceptedInfo) {
		try {
			acceptedInfoDAO.insert(acceptedInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new AcceptedInfo", e);
		}
	}

	public void updateAcceptedInfo(AcceptedInfo acceptedInfo) {
		try {
			acceptedInfoDAO.update(acceptedInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a AcceptedInfo", e);
		}
	}

	public void deleteAcceptedInfo(AcceptedInfo acceptedInfo) {
		try {
			acceptedInfoDAO.delete(acceptedInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a AcceptedInfo", e);
		}
	}

	public List<AcceptedInfo> findAllAcceptedInfo() {
		List<AcceptedInfo> result = null;
		try {
			result = acceptedInfoDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of AcceptedInfo)", e);
		}
		return result;
	}

	public AcceptedInfo findAcceptedInfoByReferenceNo(String referenceNo) {
		AcceptedInfo result = null;
		try {
			result = acceptedInfoDAO.findByReferenceNo(referenceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a AcceptedInfo (ID : " + referenceNo + ")", e);
		}
		return result;
	}
}