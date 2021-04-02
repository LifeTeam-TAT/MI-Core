package org.ace.insurance.claimaccept.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.persistence.interfaces.IClaimAcceptedInfoDAO;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.ReferenceType;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "ClaimAcceptedInfoService")
public class ClaimAcceptedInfoService extends BaseService implements IClaimAcceptedInfoService {

	@Resource(name = "ClaimAcceptedInfoDAO")
	private IClaimAcceptedInfoDAO claimAcceptedInfoDAO;

	public void addNewClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo) {
		try {
			claimAcceptedInfoDAO.insert(claimAcceptedInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new ClaimAcceptedInfo", e);
		}
	}

	public void updateClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo) {
		try {
			claimAcceptedInfoDAO.update(claimAcceptedInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a ClaimAcceptedInfo", e);
		}
	}

	public void deleteClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo) {
		try {
			claimAcceptedInfoDAO.delete(claimAcceptedInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a ClaimAcceptedInfo", e);
		}
	}

	public List<ClaimAcceptedInfo> findAllClaimAcceptedInfo() {
		List<ClaimAcceptedInfo> result = null;
		try {
			result = claimAcceptedInfoDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ClaimAcceptedInfo)", e);
		}
		return result;
	}

	public ClaimAcceptedInfo findClaimAcceptedInfoByReferenceNo(String referenceNo, ReferenceType referenceType) {
		ClaimAcceptedInfo result = null;
		try {
			result = claimAcceptedInfoDAO.findByReferenceNo(referenceNo, referenceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a AcceptedInfo (ID : " + referenceNo + ", ReferenceType :" + referenceType + ")", e);
		}
		return result;
	}
}