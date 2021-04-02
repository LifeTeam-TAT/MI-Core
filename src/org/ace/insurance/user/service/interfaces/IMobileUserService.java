/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.service.interfaces;

import java.util.List;

import org.ace.insurance.user.MobileUser;

public interface IMobileUserService {
	public void addNewMobileUser(MobileUser mobileUser);

	public void updateMobileUser(MobileUser mobileUser);

	public void deleteMobileUser(MobileUser mobileUser);

	public List<MobileUser> findAllMobileUser();

	public List<MobileUser> findByCriteria(String criteria);

	public MobileUser authenticate(String usercode, String password);

	public MobileUser findMobileUserById(String id);
}
