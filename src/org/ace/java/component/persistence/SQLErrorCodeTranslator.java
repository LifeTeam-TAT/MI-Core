package org.ace.java.component.persistence;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;

import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;

/**
 * @author Zaw Than Oo
 */
@Service(value = "SQLErrorCodeTranslator")
public class SQLErrorCodeTranslator {

	@Resource(name = "SQL_ERROR_CODE")
	private Properties properties;

	public DAOException translate(String message, SQLException sqlex) {
		String errorCode = properties.getProperty(sqlex.getErrorCode() + "");
		DAOException daoException = new DAOException(errorCode, message, sqlex);
		return daoException;
	}
}
