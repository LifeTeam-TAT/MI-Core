/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.qualification.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.qualification.Qualification;

public interface IQualificationService {
	public void addNewQualification(Qualification qualification);

	public void updateQualification(Qualification qualification);

	public void deleteQualification(Qualification qualification);

	public Qualification findQualificationById(String id);

	public List<Qualification> findAllQualification();

	public List<Qualification> findByCriteria(String criteria);
}
