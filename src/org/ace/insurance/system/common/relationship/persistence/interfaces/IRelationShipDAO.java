/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.relationship.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.java.component.persistence.exception.DAOException;

public interface IRelationShipDAO {
	public void insert(RelationShip RelationShip) throws DAOException;

	public void update(RelationShip RelationShip) throws DAOException;

	public void delete(RelationShip RelationShip) throws DAOException;

	public RelationShip findById(String id) throws DAOException;

	public List<RelationShip> findAll() throws DAOException;

	public List<RelationShip> findByCriteria(String criteria) throws DAOException;
	
	public RelationShip findRelationShipByName(String name);
}
