package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifeClaimNotiCriteria;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeClaimNotificationDAO {
	public void insert(LifeClaimNotification notification) throws DAOException;

	public void delete(LifeClaimNotification notification) throws DAOException;

	public void update(LifeClaimNotification notification) throws DAOException;

	public List<LifeClaimNotification> findLifeClaimNotification() throws DAOException;

	public List<LifeClaimNotification> findLifeClaimNotificationByCriteria(LifeClaimNotiCriteria criteria) throws DAOException;
}
