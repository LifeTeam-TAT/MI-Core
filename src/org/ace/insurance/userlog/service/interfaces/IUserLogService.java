package org.ace.insurance.userlog.service.interfaces;

import java.util.List;

import org.ace.insurance.userlog.UserLog;
import org.ace.java.component.SystemException;

public interface IUserLogService {

	void insertUserLog(UserLog userLog) throws SystemException;
	
	void updateUserLog(UserLog userLog) throws SystemException;
	
	public List<UserLog> findAllUserLog() throws SystemException;

	UserLog findUserLogById(String id);

}
