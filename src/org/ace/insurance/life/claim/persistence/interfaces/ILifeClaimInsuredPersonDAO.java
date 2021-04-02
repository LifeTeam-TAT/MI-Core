/***************************************************************************************
 * @author <<浦和ポォー>>
 * @Date 2013-06-14
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.java.component.persistence.exception.DAOException;
/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

public interface ILifeClaimInsuredPersonDAO {
	public void insert(LifeClaimInsuredPerson lifeClaimInsuredPerson) throws DAOException;

	public void update(LifeClaimInsuredPerson lifeClaimInsuredPerson) throws DAOException;

	public void delete(LifeClaimInsuredPerson lifeClaimInsuredPerson) throws DAOException;

	public LifeClaimInsuredPerson findById(String id) throws DAOException;

	public List<LifeClaimInsuredPerson> findAll() throws DAOException;
}
