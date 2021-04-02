/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.religion.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.religion.Religion;

public interface IReligionService {
	public void addNewReligion(Religion religion);

	public void updateReligion(Religion religion);

	public void deleteReligion(Religion religion);

	public Religion findReligionById(String id);

	public List<Religion> findAllReligion();

	public List<Religion> findByCriteria(String criteria);
}
