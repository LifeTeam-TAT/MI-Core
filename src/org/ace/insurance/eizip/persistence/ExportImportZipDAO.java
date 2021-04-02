package org.ace.insurance.eizip.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.eizip.ZipFileName;
import org.ace.insurance.eizip.persistence.interfaces.IExportImportZipDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ExportImportZipDAO")
public class ExportImportZipDAO extends BasicDAO implements IExportImportZipDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object> findObjectsByDate(Date startDate, Date endDate, String tableName) throws DAOException {
		List<Object> resultList = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT obj FROM " + tableName + " obj");
			if (!"SettingVariable".equals(tableName)) {
				buffer.append(" WHERE obj.id is not null");
			}
			if (startDate != null) {
				buffer.append(" AND obj.settlementDate >= :startDate");
			}
			if (endDate != null) {
				buffer.append(" AND obj.settlementDate <= :endDate");
			}
			Query q = em.createQuery(buffer.toString());
			if (startDate != null) {
				q.setParameter("startDate", Utils.resetStartDate(startDate));
			}
			if (endDate != null) {
				q.setParameter("endDate", Utils.resetEndDate(endDate));
			}
			resultList = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to findTableNameByDate", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertObjectByTableName(Object obj, String tableName) throws DAOException {
		try {
			em.persist(obj);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to insertObjectByTableName", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTablesByZipFileName(String zipFileName) throws DAOException {
		Query query = null;
		try {
			if (ZipFileName.SYSTEM.equals(zipFileName)) {
				query = em.createQuery("DELETE FROM CoinsuredProductGroup");
				query.executeUpdate();
				query = em.createQuery("DELETE FROM ProductGroup");
				query.executeUpdate();
			} else if (ZipFileName.USERAUTHORITY.equals(zipFileName)) {
				query = em.createQuery("DELETE FROM Authority");
				query.executeUpdate();
				query = em.createNativeQuery("DELETE FROM USER_PERMISSION");
				query.executeUpdate();
			} else if (ZipFileName.FIRE.equals(zipFileName)) {
				query = em.createQuery("DELETE FROM ClassValue");
				query.executeUpdate();
			} else if (ZipFileName.PRODUCT.equals(zipFileName)) {
				query = em.createNativeQuery("DELETE FROM PRODUCTGROUP_NCBRATE_LINK");
				query.executeUpdate();
				query = em.createNativeQuery("DELETE FROM PRODUCT_PAYMENTTYPE_LINK");
				query.executeUpdate();
				query = em.createNativeQuery("DELETE FROM PRODUCT_ADDON_LINK");
				query.executeUpdate();
				query = em.createNativeQuery("DELETE FROM PRODUCT_KEYFACTOR_LINK");
				query.executeUpdate();

			}
			for (String tableName : Utils.getTableNameList(zipFileName)) {
				query = em.createQuery("DELETE FROM " + tableName);
				query.executeUpdate();
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to deleteTablesByZipFileName", pe);
		}
	}
}
