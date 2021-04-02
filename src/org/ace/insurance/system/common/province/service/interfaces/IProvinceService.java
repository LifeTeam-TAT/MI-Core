/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.province.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.province.PRV001;
import org.ace.insurance.system.common.province.Province;

public interface IProvinceService {
	public void addNewProvince(Province Province);

	public void updateProvince(Province Province);

	public void deleteProvince(Province Province);

	public Province findProvinceById(String id);

	public List<Province> findAllProvince();

	public List<PRV001> findAll_PRV001();

	public List<String> findAllProvinceNo();
}
