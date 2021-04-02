package org.ace.insurance.userlog.persistences.interfaces;

import java.util.List;

import org.ace.insurance.userlog.UserLog;
import org.ace.java.component.persistence.exception.DAOException;

public interface IUserLogDAO {

	public void insertUserLost(UserLog userlog) throws DAOException;
	
	public List<UserLog> findAll() throws DAOException;

	void updateUserLog(UserLog userLog);

	UserLog findUserLogById(String id);
}
