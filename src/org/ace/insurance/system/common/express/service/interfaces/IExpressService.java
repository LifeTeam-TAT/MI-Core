/***************************************************************************************
 * @author <<Myo Thiha Kyaw>>
 * @Date 2014-06-18
 * @Version 1.0
 * @Purpose <<For Travel Insurance>>
 * 
 *    
 ***************************************************************************************/

package org.ace.insurance.system.common.express.service.interfaces;

import java.util.List;

import org.ace.insurance.common.ExpressCriteria;
import org.ace.insurance.system.common.express.Express;

public interface IExpressService {
	public void addNewExpress(Express Express);

	public void updateExpress(Express Express);

	public void deleteExpress(Express Express);

	public Express findExpressById(String id);

	public List<Express> findAllExpress();

	public List<Express> findExpressByCriteria(ExpressCriteria criteria, int max);

}
