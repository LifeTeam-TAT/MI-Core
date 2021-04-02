/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.relationship.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.relationship.RelationShip;

public interface IRelationShipService {
	public void addNewRelationShip(RelationShip RelationShip);

	public void updateRelationShip(RelationShip RelationShip);

	public void deleteRelationShip(RelationShip RelationShip);

	public RelationShip findRelationShipById(String id);

	public List<RelationShip> findAllRelationShip();

	public List<RelationShip> findByCriteria(String criteria);
	
	public RelationShip findRelationShipByName(String name);
}
