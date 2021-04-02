/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.township.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.township.TSP001;
import org.ace.insurance.system.common.township.TSP002;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.persistence.exception.DAOException;

public interface ITownshipDAO {
	public void insert(Township Township) throws DAOException;

	public void update(Township Township) throws DAOException;

	public void delete(Township Township) throws DAOException;

	public Township findById(String id) throws DAOException;

	public List<Township> findByProvince(Province province) throws DAOException;

	public List<Township> findAll() throws DAOException;

	public List<TSP001> findAll_TSP001() throws DAOException;

	public List<TSP002> findAll_TSP002() throws DAOException;

	public String findNameById(String id) throws DAOException;

	public List<String> findTspShortNameByProvinceNo(String provinceNo) throws DAOException;
	
	public Township findTownshipByName(String name);
}
