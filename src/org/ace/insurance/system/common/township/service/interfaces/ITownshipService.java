/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.township.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.township.TSP001;
import org.ace.insurance.system.common.township.TSP002;
import org.ace.insurance.system.common.township.Township;

public interface ITownshipService {
	public void addNewTownship(Township Township);

	public void updateTownship(Township Township);

	public void deleteTownship(Township Township);

	public Township findTownshipById(String id);

	public List<Township> findTownshipByProvince(Province province);

	public List<Township> findAllTownship();

	public List<TSP001> findAll_TSP001();

	public List<TSP002> findAll_TSP002();

	public List<String> findTspShortNameByProvinceNo(String provinceNo);
	
	public Township findTownshipByName(String name);
}
