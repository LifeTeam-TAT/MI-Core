package org.ace.insurance.managementreport.lifepolicyreport.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Gender;
import org.ace.insurance.common.Utils;
import org.ace.insurance.managementreport.lifepolicyreport.LifeProductOverview;
import org.ace.insurance.managementreport.lifepolicyreport.persistence.interfaces.ILifeProductOverviewDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifeProductOverviewDAO")
public class LifeProductOverviewDAO extends BasicDAO implements ILifeProductOverviewDAO {

	private Logger logger = LogManager.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByTownship() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append(
					"Select i.residentAddress.township.name, Count(f.id) From LifePolicy f inner join f.policyInsuredPersonList i  Group By i.residentAddress.township.name");
			Query q = em.createQuery(lifeQuery.toString());
			List<Object[]> raws = q.getResultList();
			for (Object[] a : raws) {
				String tname = (String) a[0];
				Number fcount = (Number) a[1];
				pieChartMap.put(tname, fcount);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByProductType() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		List<Object[]> raws = new ArrayList<Object[]>();
		String tname;
		Number fcount;
		try {
			logger.debug("find() method has been started.");
			StringBuffer query = new StringBuffer();
			query.append(
					"Select pro.name, count(Distinct(p.id)) from LifePolicy p inner join p.policyInsuredPersonList pb , Product pro  where p.policyNo is not null and pb.product.id = pro.id group by pro.name");
			Query q1 = em.createQuery(query.toString());
			raws = q1.getResultList();
			for (Object[] a : raws) {
				tname = (String) a[0];
				fcount = (Number) a[1];
				if (pieChartMap.get(tname) == null) {
					pieChartMap.put(tname, fcount);
				} else {
					Number oldCount = pieChartMap.get(tname);
					pieChartMap.put(tname, (Number) (Integer.parseInt(fcount.toString()) + Integer.parseInt(oldCount.toString())));
				}
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByGender() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append(
					"Select pi.gender, count(l.id) from LifePolicy l inner join l.policyInsuredPersonList pi where l.id = pi.lifePolicy.id and l.policyNo is not null group by pi.gender");
			Query q = em.createQuery(lifeQuery.toString());
			List<Object[]> raws = q.getResultList();
			for (Object[] a : raws) {
				Gender tname = (Gender) a[0];
				Number fcount = (Number) a[1];
				pieChartMap.put(tname.getLabel(), fcount);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByMonth(int year) {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		// int year = Calendar.getInstance().get(Calendar.YEAR);
		try {
			logger.debug("find() method has been started.");
			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append("Select count(f.id) from LifePolicy f where f.policyNo is not null AND f.commenmanceDate > :startDate " + "AND f.commenmanceDate < :endDate");
			for (int i = 1; i < 13; i++) {
				Query q = em.createQuery(lifeQuery.toString());
				q.setParameter("startDate", Utils.getStartDate(year, i - 1));
				q.setParameter("endDate", Utils.getEndDate(year, i - 1));
				Number fcount = (Number) q.getSingleResult();
				pieChartMap.put(year + "/" + i, fcount);
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByPaymentType() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append("Select l.paymentType.name , count(l.id) from LifePolicy l where l.policyNo is not null group by l.paymentType");
			Query q = em.createQuery(lifeQuery.toString());
			List<Object[]> raws = q.getResultList();
			for (Object[] a : raws) {
				String tname = (String) a[0];
				Number fcount = (Number) a[1];
				pieChartMap.put(tname, fcount);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByChannel() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer fireQuery = new StringBuffer();
			fireQuery.append("Select count(Distinct(f.id)) from LifePolicy f where f.agent is not null and f.policyNo is not null");
			Query q = em.createQuery(fireQuery.toString());
			Number fcount = (Number) q.getSingleResult();
			pieChartMap.put("AGENTS", fcount);

			StringBuffer query = new StringBuffer();
			query.append("Select count(f.id) from LifePolicy f where f.saleMan is not null and f.policyNo is not null");
			Query q1 = em.createQuery(query.toString());
			Number count = (Number) q1.getSingleResult();
			pieChartMap.put("SALES", count);
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByAge() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append("Select count(l.id) from LifePolicy l inner join l.policyInsuredPersonList pi where l.id = pi.lifePolicy.id and l.policyNo is not null "
					+ "and pi.age >= :ageOne and pi.age <= :ageTwo");

			for (int i = 10; i < 66; i += 10) {
				Query q = em.createQuery(lifeQuery.toString());
				int x = i;
				int y = i + 10;
				q.setParameter("ageOne", x);
				q.setParameter("ageTwo", y);
				Number fcount;
				try {
					fcount = (Number) q.getSingleResult();
				} catch (NoResultException e) {
					fcount = 0;
				}

				pieChartMap.put(x + "_" + y, fcount);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyBySIAge() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			logger.debug("find() method has been started.");
			StringBuffer lifeQuery = new StringBuffer();
			lifeQuery.append("Select pi.age, Sum(pi.sumInsured) From PolicyInsuredPerson pi Group By pi.age");
			Query q = em.createQuery(lifeQuery.toString());
			List<Object[]> raws = q.getResultList();
			for (Object[] a : raws) {
				int tname = (Integer) a[0];
				Number fcount = (Number) a[1];
				pieChartMap.put(tname + " years", fcount);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MotorProposal by criteria.", pe);
		}
		LifeProductOverview ap = new LifeProductOverview(pieChartMap);
		return ap;
	}

}
