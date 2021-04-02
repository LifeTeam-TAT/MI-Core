/***************************************************************************************
 * @author YYK
 * @Date 2016-May-6
 * @Version 1.0
 * @Purpose This interface serves as the Service Layer to manipulate the <code>District</code> object.
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.district.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.district.DIS001;
import org.ace.insurance.system.common.district.District;
import org.ace.insurance.system.common.district.DistrictCriteria;

public interface IDistrictService {

	/**
	 * This method serves as the service method to insert district instance into
	 * database.
	 * 
	 * @param District
	 */
	public void addNewDistrict(District District);

	/**
	 * This method serves as the service method to update district instance into
	 * database.
	 * 
	 * @param District
	 */
	public void updateDistrict(District District);

	/**
	 * This method serves as the service method to delete district instance into
	 * database.
	 * 
	 * @param District
	 */
	public void deleteDistrict(District District);

	/**
	 * This method serves as the service method to find district instance by ID
	 * from database.
	 * 
	 * @param id
	 * @return District
	 */
	public District findDistrictById(String id);

	/**
	 * This method serves as the service method to find all district instances
	 * from database.
	 * 
	 * @return List<District>
	 */
	public List<District> findAllDistrict();

	/**
	 * This method serves as the service method to find all District001
	 * instances by criteria from database.
	 * 
	 * @return List<District001>
	 */
	public List<DIS001> findByCriteria(DistrictCriteria criteria);

	/**
	 * This method serves as the service method to find all District001
	 * instances from database.
	 * 
	 * @return List<District001>
	 */
	public List<DIS001> findAll_DIS001();

	/**
	 * This method serves as the service method to delete district instances by
	 * id from database.
	 * 
	 * @return List<District001>
	 */
	public void deleteDistrictById(String districtId);
}
