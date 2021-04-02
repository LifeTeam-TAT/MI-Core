/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.country.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.country.CTY001;
import org.ace.insurance.system.common.country.Country;

public interface ICountryService {
	public void addNewCountry(Country Country);

	public void updateCountry(Country Country);

	public void deleteCountry(Country Country);

	public Country findCountryById(String id);

	public List<Country> findAllCountry();

	public List<CTY001> findAll_CTY001();
	
	public Country findCountryByName(String name);
}
