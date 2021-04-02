/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.typesOfSport.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.typesOfSport.TypesOfSport;

public interface ITypesOfSportService {
	public void addNewTypesOfSport(TypesOfSport typesOfSport);

	public void updateTypesOfSport(TypesOfSport typesOfSport);

	public void deleteTypesOfSport(TypesOfSport typesOfSport);

	public TypesOfSport findTypesOfSportById(String id);

	public List<TypesOfSport> findAllTypesOfSport();

	public List<TypesOfSport> findByCriteria(String criteria, int max);
}
