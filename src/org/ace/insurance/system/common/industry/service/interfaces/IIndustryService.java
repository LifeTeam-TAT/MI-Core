/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.industry.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.industry.Industry;

public interface IIndustryService {
	public void addNewIndustry(Industry Industry);

	public void updateIndustry(Industry Industry);

	public void deleteIndustry(Industry Industry);

	public Industry findIndustryById(String id);

	public List<Industry> findAllIndustry();

	public List<Industry> findByCriteria(String criteria);
}
