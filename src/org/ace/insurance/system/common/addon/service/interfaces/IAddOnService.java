/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.addon.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.addon.AddOn;

public interface IAddOnService {
	public void addNewAddOn(AddOn AddOn);

	public void updateAddOn(AddOn AddOn);

	public void deleteAddOn(AddOn AddOn);

	public AddOn findAddOnById(String id);

	public List<AddOn> findAllAddOn();

	public List<AddOn> findByCriteria(String criteria);

	public List<AddOn> findPremiumRate();

	public List<AddOn> findPremiumRateByProductId(String productId);
}
