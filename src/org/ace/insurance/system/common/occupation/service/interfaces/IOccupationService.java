/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.occupation.service.interfaces;

import java.util.List;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.occupation.Occupation;

public interface IOccupationService {
	
	public void addNewOccupation(Occupation Occupation);

	public void updateOccupation(Occupation Occupation);

	public void deleteOccupation(Occupation Occupation);

	public Occupation findOccupationById(String id);

	public List<Occupation> findOccupationByInsuranceType(InsuranceType insuranceType);

	public List<Occupation> findAllOccupation();

	public List<Occupation> findByCriteria(String criteria);
	
	public Occupation findOccupationByName(String name);
}
