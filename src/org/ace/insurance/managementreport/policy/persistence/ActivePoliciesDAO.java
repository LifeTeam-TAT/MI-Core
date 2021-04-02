package org.ace.insurance.managementreport.policy.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.managementreport.policy.ActivePolicies;
import org.ace.insurance.managementreport.policy.persistence.interfaces.IActivePoliciesDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ActivePoliciesDAO")
public class ActivePoliciesDAO extends BasicDAO implements IActivePoliciesDAO {
	private Logger logger = LogManager.getLogger(this.getClass());

	// To FIXME by THK
	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findActivePoliciesByProducts() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer motorQuery = new StringBuffer();
			motorQuery.append("SELECT COUNT(m.id) FROM MotorPolicy m WHERE m.policyNo IS NOT NULL");
			Query mQuery = em.createQuery(motorQuery.toString());
			Number mcount = Integer.parseInt(mQuery.getSingleResult().toString());
			pieChartMap.put("Total_Motor_Policies", mcount);

			StringBuffer fireQuery = new StringBuffer();
			fireQuery.append("SELECT COUNT(m.id)  FROM FirePolicy m WHERE m.policyNo IS NOT NULL");
			Query fQuery = em.createQuery(fireQuery.toString());
			Number fcount = Integer.parseInt(fQuery.getSingleResult().toString());
			pieChartMap.put("Total_Fire_Policies", fcount);

			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append("SELECT COUNT(m.id)  FROM LifePolicy m WHERE m.policyNo IS NOT NULL");
			Query lQuery = em.createQuery(lifeQuery.toString());
			Number lcount = Integer.parseInt(lQuery.getSingleResult().toString());
			pieChartMap.put("Total_Life_Policies", lcount);
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		ActivePolicies ap = new ActivePolicies(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findTotalSumInsuredByProducts() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer motorQuery = new StringBuffer();
			motorQuery.append("SELECT SUM(m.sumInsured) FROM PolicyVehicle m ");
			Query mQuery = em.createQuery(motorQuery.toString());
			Number mSumInsured = Double.parseDouble(mQuery.getSingleResult().toString());
			pieChartMap.put("Motor", mSumInsured);

			StringBuffer fireQuery = new StringBuffer();
			fireQuery.append("SELECT SUM(m.sumInsured)  FROM FirePolicyProductInfo m ");
			Query fQuery = em.createQuery(fireQuery.toString());
			Number fSumInsured = Double.parseDouble(fQuery.getSingleResult().toString());
			pieChartMap.put("Fire", fSumInsured);

			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append("SELECT SUM(m.sumInsured)  FROM PolicyInsuredPerson m");
			Query lQuery = em.createQuery(lifeQuery.toString());
			Number lSumInsured = Double.parseDouble(lQuery.getSingleResult().toString());
			pieChartMap.put("Life", lSumInsured);
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		ActivePolicies ap = new ActivePolicies(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findTotalPremiumByProducts() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer motorQuery = new StringBuffer();
			motorQuery.append("SELECT SUM(m.premium) FROM PolicyVehicle m ");
			Query mQuery = em.createQuery(motorQuery.toString());
			Number mpremium = Double.parseDouble(mQuery.getSingleResult().toString());
			pieChartMap.put("Motor", mpremium);

			StringBuffer fireQuery = new StringBuffer();
			fireQuery.append("SELECT SUM(m.premium)  FROM FirePolicyProductInfo m ");
			Query fQuery = em.createQuery(fireQuery.toString());
			Number fpremium = Double.parseDouble(fQuery.getSingleResult().toString());
			pieChartMap.put("Fire", fpremium);

			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append("SELECT SUM(m.premium)  FROM PolicyInsuredPerson m");
			Query lQuery = em.createQuery(lifeQuery.toString());
			Number lpremium = Double.parseDouble(lQuery.getSingleResult().toString());
			pieChartMap.put("Life", lpremium);
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		ActivePolicies ap = new ActivePolicies(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findLifePolicyByTimeLine(int year) {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		// int year = Calendar.getInstance().get(Calendar.YEAR);
		try {
			logger.debug("find() method has been started.");
			StringBuffer query = new StringBuffer();
			query.append("Select sum(f.basicTermPremium) from LifePolicy l inner join l.policyInsuredPersonList f where  l.activedPolicyStartDate > :startDate "
					+ "AND l.activedPolicyStartDate < :endDate");
			for (int i = 1; i < 13; i++) {
				Query q = em.createQuery(query.toString());
				q.setParameter("startDate", Utils.getStartDate(year, i - 1));
				q.setParameter("endDate", Utils.getEndDate(year, i - 1));
				Number fcount = (Number) q.getSingleResult();
				if (fcount == null) {
					fcount = 0.0;
				}
				pieChartMap.put(year + "/" + i, fcount);
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		ActivePolicies ap = new ActivePolicies(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findFirePolicyByTimeLine(int year) {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		// int year = Calendar.getInstance().get(Calendar.YEAR);
		try {
			logger.debug("find() method has been started.");
			StringBuffer query = new StringBuffer();
			query.append(
					"Select sum(fpp.basicTermPremium) from FirePolicy l inner join l.policyBuildingInfoList f inner join f.firePolicyProductInfoList fpp where l.activedPolicyStartDate > :startDate "
							+ "AND l.activedPolicyStartDate < :endDate");
			for (int i = 1; i < 13; i++) {
				Query q = em.createQuery(query.toString());
				q.setParameter("startDate", Utils.getStartDate(year, i - 1));
				q.setParameter("endDate", Utils.getEndDate(year, i - 1));
				Number fcount = (Number) q.getSingleResult();
				if (fcount == null) {
					fcount = 0.0;
				}
				pieChartMap.put(year + "/" + i, fcount);
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		ActivePolicies ap = new ActivePolicies(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findMotorPolicyByTimeLine(int year) {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		// int year = Calendar.getInstance().get(Calendar.YEAR);
		try {
			logger.debug("find() method has been started.");
			StringBuffer query = new StringBuffer();
			query.append("Select sum(f.basicTermPremium) from MotorPolicy l inner join l.policyVehicleList f where  l.activedPolicyStartDate > :startDate "
					+ "AND l.activedPolicyStartDate < :endDate");
			for (int i = 1; i < 13; i++) {
				Query q = em.createQuery(query.toString());
				q.setParameter("startDate", Utils.getStartDate(year, i - 1));
				q.setParameter("endDate", Utils.getEndDate(year, i - 1));
				Number fcount = (Number) q.getSingleResult();
				if (fcount == null) {
					fcount = 0.0;
				}
				pieChartMap.put(year + "/" + i, fcount);
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		ActivePolicies ap = new ActivePolicies(pieChartMap);
		return ap;
	}

}
