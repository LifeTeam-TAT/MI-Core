package org.ace.insurance.report.life.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.report.life.LifePremiumLedgerReport;
import org.ace.insurance.report.life.persistence.interfaces.ILifePremiumLedgerDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePremiumLedgerDAO")
public class LifePremiumLedgerDAO extends BasicDAO implements ILifePremiumLedgerDAO {
	@Transactional(propagation = Propagation.REQUIRED)
	public LifePremiumLedgerReport findLifePremiumLedgerReport(String lifePolicyId) throws DAOException {
		LifePolicy lifePolicy;
		List<Payment> paymentList;
		Date surveyDate;
		try {
			lifePolicy = em.find(LifePolicy.class, lifePolicyId);

			StringBuffer stBuffer = new StringBuffer();
			stBuffer.append("SELECT p FROM Payment p WHERE  p.referenceNo = :referenceNo AND p.complete = true ORDER BY p.paymentDate");
			Query q = em.createQuery(stBuffer.toString());
			q.setParameter("referenceNo", lifePolicyId);
			paymentList = q.getResultList();

			Query query = em.createQuery("SELECT l.date FROM LifeSurvey l WHERE l.lifeProposal.id = :id");
			query.setParameter("id", lifePolicy.getLifeProposal().getId());
			surveyDate = (Date) query.getSingleResult();

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to findfindLifePremiumLedgerReport by policy Id.", pe);
		}
		return new LifePremiumLedgerReport(lifePolicy, paymentList, surveyDate);
	}

}
