/***************************************************************************************
 * @author <<Myo Thiha Kyaw>>
 * @Date 2014-06-18
 * @Version 1.0
 * @Purpose <<For Travel Insurance>>
 * 
 *    
 ***************************************************************************************/

package org.ace.insurance.system.common.express.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.ExpressCriteria;
import org.ace.insurance.system.common.express.Express;
import org.ace.java.component.persistence.exception.DAOException;

public interface IExpressDAO {
	public void insert(Express express) throws DAOException;

	public void update(Express express) throws DAOException;

	public void delete(Express express) throws DAOException;

	public Express findById(String id) throws DAOException;

	public List<Express> findAll() throws DAOException;

	public List<Express> findByCriteria(ExpressCriteria criteria, int max) throws DAOException;
}
