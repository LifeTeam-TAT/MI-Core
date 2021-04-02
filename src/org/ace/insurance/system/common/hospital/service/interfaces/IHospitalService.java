/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.hospital.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.hospital.Hospital;

public interface IHospitalService {
	public void addNewHospital(Hospital Hospital);

	public void updateHospital(Hospital Hospital);

	public void deleteHospital(Hospital Hospital);

	public Hospital findHospitalById(String id);

	public List<Hospital> findAllHospital();

	public List<Hospital> findByCriteria(String criteria);
}
