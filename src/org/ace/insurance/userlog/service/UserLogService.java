package org.ace.insurance.userlog.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.userlog.UserLog;
import org.ace.insurance.userlog.persistences.interfaces.IUserLogDAO;
import org.ace.insurance.userlog.service.interfaces.IUserLogService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("UserLogService")
public class UserLogService extends BaseService implements IUserLogService{

	@Resource(name="UserLogDAO")
	private IUserLogDAO userLogDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertUserLog(UserLog userLog) throws SystemException{
		try {
			userLogDAO.insertUserLost(userLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(),"Fail to insert User log",e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserLog> findAllUserLog() {
		List<UserLog> result = null;
		try {
			result = userLogDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all user", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserLog(UserLog userLog) throws SystemException {
		try {
			userLogDAO.updateUserLog(userLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(),"Fail to update User log",e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public UserLog findUserLogById(String id) {
		UserLog result=null;
		try {
			result=userLogDAO.findUserLogById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(),"Fail to find UserLog By Id",e);
		}
		return result;
	}
	
	

}
