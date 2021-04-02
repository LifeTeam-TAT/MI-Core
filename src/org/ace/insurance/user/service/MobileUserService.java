/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.user.MobileUser;
import org.ace.insurance.user.persistence.interfaces.IMobileUserDAO;
import org.ace.insurance.user.service.interfaces.IMobileUserService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.ace.java.component.service.PasswordCodeHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MobileUserService")
public class MobileUserService extends BaseService implements IMobileUserService {

	@Resource(name = "MobileUserDAO")
	private IMobileUserDAO mobileUserDAO;

	@Resource(name = "PasswordCodeHandler")
	private PasswordCodeHandler codeHandler;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewMobileUser(MobileUser mobileUser) {
		try {
			mobileUser.setChangePassword(true);
			mobileUser.setPassword(codeHandler.encode(mobileUser.getPassword()));
			mobileUserDAO.insert(mobileUser);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MobileUser", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateMobileUser(MobileUser mobileUser) {
		try {
			mobileUser.setPassword(codeHandler.encode(mobileUser.getPassword()));
			mobileUserDAO.update(mobileUser);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MobileUser", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateLockAndDisableMobileUser(MobileUser mobileUser) {
		try {
			mobileUserDAO.update(mobileUser);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MobileUser", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteMobileUser(MobileUser mobileUser) {
		try {
			mobileUserDAO.delete(mobileUser);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a MobileUser", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<MobileUser> findAllMobileUser() {
		List<MobileUser> result = null;
		try {
			result = mobileUserDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MobileUser)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public MobileUser findMobileUserById(String id) {
		MobileUser result = null;
		try {
			result = mobileUserDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a MobileUser (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MobileUser> findByCriteria(String criteria) {
		List<MobileUser> result = null;
		try {
			result = mobileUserDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find MobileUser by criteria " + criteria, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MobileUser authenticate(String usercode, String password) {
		MobileUser result = null;
		try {
			MobileUser mobileUser = mobileUserDAO.findByUserCode(usercode);
			if (mobileUser != null) {
				String encodedPassword = codeHandler.encode(password);
				if (mobileUser.getPassword().equals(encodedPassword)) {
					result = mobileUser;
				}
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to change passowrd", e);
		}
		return result;
	}

}