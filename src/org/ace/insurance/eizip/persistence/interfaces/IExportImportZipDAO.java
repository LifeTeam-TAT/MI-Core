package org.ace.insurance.eizip.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.java.component.persistence.exception.DAOException;

public interface IExportImportZipDAO {

	public List<Object> findObjectsByDate(Date startDate, Date endDate, String tableName) throws DAOException;

	public void insertObjectByTableName(Object obj, String tableName) throws DAOException;

	public void deleteTablesByZipFileName(String zipFileName) throws DAOException;

}
