package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifeClaimAmount;
import org.ace.java.component.persistence.exception.DAOException;
/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

public interface ILifeClaimAmountDAO {
	public void insert(LifeClaimAmount ClaimAmount) throws DAOException;

	public void update(LifeClaimAmount ClaimAmount) throws DAOException;

	public void delete(LifeClaimAmount ClaimAmount) throws DAOException;

	public LifeClaimAmount findById(String id) throws DAOException;

	public List<LifeClaimAmount> findAll() throws DAOException;
}
