package org.ace.insurance.life.claim.service.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifeClaimNotiCriteria;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.java.component.SystemException;

public interface ILifeClaimNotificationService {
	public void addNewLifeClaimNotification(LifeClaimNotification notification);

	public void deleteLifeClaimNotification(LifeClaimNotification notification);

	public void updateLifeClaimNotification(LifeClaimNotification notification);

	public List<LifeClaimNotification> findLifeClaimNotification();

	public List<LifeClaimNotification> findLifeClaimNotificationByCriteria(LifeClaimNotiCriteria criteria) throws SystemException;

}
