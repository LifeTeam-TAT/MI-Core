package org.ace.insurance.report.common.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.MonthType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.ProposalPremiumSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.common.persistence.interfaces.IProposalPremiumSummaryReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ProposalPremiumSummaryReportDAO")
public class ProposalPremiumSummaryReportDAO extends BasicDAO implements IProposalPremiumSummaryReportDAO {

	public Date getStartDate(SummaryReportCriteria criteria) {
		Calendar cal = Calendar.getInstance();
		Date startDate = new Date();

		if (criteria.getReportType().equalsIgnoreCase("Weekly Report")) {
			startDate = criteria.getStartDate();
		}

		if (criteria.getReportType().equalsIgnoreCase("Monthly Report")) {
			int year = criteria.getYear();
			int month = criteria.getMonth();
			cal.set(year, month, 1);
			startDate = cal.getTime();
		}

		if (criteria.getReportType().equalsIgnoreCase("Yearly Report")) {
			int year = cal.get(Calendar.YEAR);
			year = criteria.getYear();
			cal.set(year, 0, 1);
			startDate = cal.getTime();
		}

		criteria.setStartDate(Utils.resetStartDate(startDate));
		return criteria.getStartDate();
	}

	public Date getEndDate(SummaryReportCriteria criteria) {
		Calendar cal = Calendar.getInstance();
		Date endDate = new Date();

		if (criteria.getReportType().equalsIgnoreCase("Weekly Report")) {
			cal.setTime(criteria.getStartDate());
			cal.add(Calendar.DAY_OF_MONTH, +6);
			endDate = cal.getTime();
		}

		if (criteria.getReportType().equalsIgnoreCase("Monthly Report")) {
			cal.set(Calendar.MONTH, criteria.getMonth());
			cal.set(Calendar.YEAR, criteria.getYear());
			DateTime dateTime = new DateTime(cal.getTime());
			DateTime lastTime = dateTime.dayOfMonth().withMaximumValue();
			endDate = new Date(lastTime.getMillis());
		}

		if (criteria.getReportType().equalsIgnoreCase("Yearly Report")) {
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.YEAR, criteria.getYear());
			DateTime dateTime = new DateTime(cal.getTime());
			DateTime lastTime = dateTime.dayOfMonth().withMaximumValue();
			endDate = new Date(lastTime.getMillis());
		}
		criteria.setEndDate(Utils.resetEndDate(endDate));
		return criteria.getEndDate();
	}

	public int getMonthNumber(MonthType month) {
		int result = 0;
		switch (month) {
			case JAN:
				result = 0;
				break;
			case FEB:
				result = 1;
				break;
			case MAR:
				result = 2;
				break;
			case APR:
				result = 3;
				break;
			case MAY:
				result = 4;
				break;
			case JUN:
				result = 5;
				break;
			case JUL:
				result = 6;
				break;
			case AUG:
				result = 7;
				break;
			case SEP:
				result = 8;
				break;
			case OCT:
				result = 9;
				break;
			case NOV:
				result = 10;
				break;
			case DEC:
				result = 11;
				break;
			default:
				break;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProposalPremiumSummaryReport> find(SummaryReportCriteria criteria) throws DAOException {
		List<ProposalPremiumSummaryReport> result = new ArrayList<ProposalPremiumSummaryReport>();

		List<String> motorProductList = new ArrayList<String>();
		List<String> lifeProductList = new ArrayList<String>();
		List<String> fireProductList = new ArrayList<String>();

		try {

			/*
			 * product
			 */

			StringBuffer life = new StringBuffer();
			life.append("SELECT p.name FROM Product p WHERE p.insuranceType = :insuranceType");
			Query l = em.createQuery(life.toString());
			l.setParameter("insuranceType", InsuranceType.LIFE);
			lifeProductList = l.getResultList();

			/*
			 * Life Proposal
			 */
			List<Object> agentLifeList = new ArrayList<Object>();
			StringBuffer laq = new StringBuffer();
			laq.append(" SELECT SUM(a.proposedPremium),a.name FROM LifeProposalSummary a ");
			laq.append(" WHERE a.agentId IS NOT NULL ");
			laq.append(" AND a.submittedDate >= ?1 AND a.submittedDate <= ?2 ");
			laq.append(" GROUP BY a.name");
			Query lifeAgentQuery = em.createQuery(laq.toString());
			lifeAgentQuery.setParameter(1, getStartDate(criteria));
			lifeAgentQuery.setParameter(2, getEndDate(criteria));
			agentLifeList = lifeAgentQuery.getResultList();

			List<Object> salemanLifeList = new ArrayList<Object>();
			StringBuffer lsq = new StringBuffer();
			lsq.append(" SELECT SUM(s.proposedPremium),s.name FROM LifeProposalSummary s ");
			lsq.append(" WHERE s.saleManId IS NOT NULL ");
			lsq.append(" AND s.submittedDate >= ?1 AND s.submittedDate <= ?2 ");
			lsq.append(" GROUP BY s.name");
			Query lifeSalemanQuery = em.createQuery(lsq.toString());
			lifeSalemanQuery.setParameter(1, getStartDate(criteria));
			lifeSalemanQuery.setParameter(2, getEndDate(criteria));
			salemanLifeList = lifeSalemanQuery.getResultList();

			/*
			 * Fire Proposal
			 */
			List<Object> agentFireList = null;
			StringBuffer faq = new StringBuffer();
			faq.append(" SELECT SUM(a.proposedPremium),a.name FROM FireProposalSummary a ");
			faq.append(" WHERE a.agentId IS NOT NULL ");
			faq.append(" AND a.submittedDate >= ?1 AND a.submittedDate <= ?2 ");
			faq.append(" GROUP BY a.name");
			Query fireAgentQuery = em.createQuery(faq.toString());
			fireAgentQuery.setParameter(1, getStartDate(criteria));
			fireAgentQuery.setParameter(2, getEndDate(criteria));
			agentFireList = fireAgentQuery.getResultList();

			List<Object> salemanFireList = null;
			StringBuffer fsq = new StringBuffer();
			fsq.append(" SELECT SUM( s.proposedPremium ),s.name FROM FireProposalSummary s ");
			fsq.append(" WHERE s.saleManId IS NOT NULL ");
			fsq.append(" AND s.submittedDate >= ?1 AND s.submittedDate <= ?2 ");
			fsq.append(" GROUP BY s.name");
			Query fireSalemanQuery = em.createQuery(fsq.toString());
			fireSalemanQuery.setParameter(1, getStartDate(criteria));
			fireSalemanQuery.setParameter(2, getEndDate(criteria));
			salemanFireList = fireSalemanQuery.getResultList();

			/*
			 * Motor Proposal
			 */
			List<Object> agentMotorList = new ArrayList<Object>();
			StringBuffer maq = new StringBuffer();
			maq.append(" SELECT SUM( a.proposedPremium ),a.name FROM MotorProposalSummary a ");
			maq.append(" WHERE a.agentId IS NOT NULL ");
			maq.append(" AND a.submittedDate >= ?1 AND a.submittedDate <= ?2 ");
			maq.append(" GROUP BY a.name");
			Query motorAgentQuery = em.createQuery(maq.toString());
			motorAgentQuery.setParameter(1, getStartDate(criteria));
			motorAgentQuery.setParameter(2, getEndDate(criteria));
			agentMotorList = motorAgentQuery.getResultList();

			List<Object> salemanMotorList = new ArrayList<Object>();
			StringBuffer msq = new StringBuffer();
			msq.append(" SELECT SUM( s.proposedPremium ),s.name FROM MotorProposalSummary s ");
			msq.append(" WHERE s.saleManId IS NOT NULL ");
			msq.append(" AND s.submittedDate >= ?1 AND s.submittedDate <= ?2 ");
			msq.append(" GROUP BY s.name");
			Query motorSalemanQuery = em.createQuery(msq.toString());
			motorSalemanQuery.setParameter(1, getStartDate(criteria));
			motorSalemanQuery.setParameter(2, getEndDate(criteria));
			salemanMotorList = motorSalemanQuery.getResultList();

			result.add(new ProposalPremiumSummaryReport(salemanLifeList, agentLifeList, lifeProductList, "Life Insurance"));
			result.add(new ProposalPremiumSummaryReport(salemanMotorList, agentMotorList, motorProductList, "Motor Insurance"));
			result.add(new ProposalPremiumSummaryReport(salemanFireList, agentFireList, fireProductList, "Fire Insurance"));

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of FireProposal by criteria.", pe);
		}
		return result;
	}
}
