/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.province.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.province.PRV001;
import org.ace.insurance.system.common.province.Province;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProvinceDAO {
	public void insert(Province Province) throws DAOException;

	public void update(Province Province) throws DAOException;

	public void delete(Province Province) throws DAOException;

	public Province findById(String id) throws DAOException;

	public List<Province> findAll() throws DAOException;

	public List<PRV001> findAll_PRV001() throws DAOException;

	public List<String> findAllProvinceNo() throws DAOException;
}
