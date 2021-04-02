package org.ace.insurance.life.claim.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.life.claim.LifeClaimNotiCriteria;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimNotificationDAO;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimNotificationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifeClaimNotificationService")
public class LifeClaimNotificationService implements ILifeClaimNotificationService {

	@Resource(name = "LifeClaimNotificationDAO")
	private ILifeClaimNotificationDAO notificationDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifeClaimNotification(LifeClaimNotification notification) {
		try {
			// String productCode =
			// notification.getProduct().getProductGroup().getProposalNoPrefix();
			String notificationNo = null;
			notificationNo = customIDGenerator.getNextId(SystemConstants.LIFE_CLAIM_NOTIFICATION_NO, null);
			notification.setNotificationNo(notificationNo);
			notificationDAO.insert(notification);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add LifeClaimNotification", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifeClaimNotification(LifeClaimNotification notification) {
		try {

			notificationDAO.delete(notification);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete LifeClaimNotification", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifeClaimNotification(LifeClaimNotification notification) {
		try {
			notificationDAO.update(notification);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update LifeClaimNotification", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimNotification> findLifeClaimNotification() {
		List<LifeClaimNotification> result = null;
		try {
			result = notificationDAO.findLifeClaimNotification();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find LifeClaimNotification", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimNotification> findLifeClaimNotificationByCriteria(LifeClaimNotiCriteria criteria) throws SystemException {
		List<LifeClaimNotification> result = null;
		try {
			result = notificationDAO.findLifeClaimNotificationByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find LifeClaimNotification", e);
		}
		return result;
	}
}
