/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.persistence.interfaces;

import java.util.List;

import org.ace.insurance.user.MobileUser;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMobileUserDAO {

	public void insert(MobileUser mobileUser) throws DAOException;

	public void update(MobileUser mobileUser) throws DAOException;

	public void delete(MobileUser MobileUser) throws DAOException;

	public MobileUser findById(String id) throws DAOException;

	public List<MobileUser> findAll() throws DAOException;

	public List<MobileUser> findByCriteria(String criteria) throws DAOException;

	public MobileUser findByUserCode(String usercode) throws DAOException;
}
