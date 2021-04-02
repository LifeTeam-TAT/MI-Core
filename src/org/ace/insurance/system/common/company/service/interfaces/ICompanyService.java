/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.company.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.company.CMY001;
import org.ace.insurance.system.common.company.Company;

public interface ICompanyService {
	public void addNewCompany(Company Company);

	public void updateCompany(Company Company);

	public void deleteCompany(Company Company);

	public Company findCompanyById(String id);

	public List<Company> findAllCompany();

	public List<CMY001> findAll_CMY001();
}
