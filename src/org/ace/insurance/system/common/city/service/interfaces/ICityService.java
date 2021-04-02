/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.city.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.city.City;

public interface ICityService {
	public void addNewCity(City City);

	public void updateCity(City City);

	public void deleteCity(City City);

	public City findCityById(String id);

	public List<City> findAllCity();

	public List<City> findByCriteria(String criteria);

	public City findCityByName(String name);
}
