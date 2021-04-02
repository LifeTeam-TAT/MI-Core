package org.ace.insurance.report.common.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.FinancialReportCriteria;
import org.ace.insurance.report.common.SummaryReportType;
import org.ace.insurance.report.common.persistence.interfaces.IFinancialReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository("FinancialReportDAO")
public class FinancialReportDAO extends BasicDAO implements IFinancialReportDAO {

	private class ReportItemType {

		public static final String LIFE = "LIFE";
	}

	private class Queries {
		public static final String ALL_PROPOSAL_LIFE = "SELECT COUNT(x.id), FUNC('MONTH', x.submittedDate) AS month, FUNC('YEAR', x.submittedDate) AS year "
				+ "FROM LifeProposal x WHERE :fromDate < x.submittedDate  AND x.submittedDate < :toDate GROUP BY year,month";
		public static final String LIFE_PROPOSAL = "SELECT COUNT(x.id), FUNC('MONTH', x.submittedDate) AS month, FUNC('YEAR', x.submittedDate) AS year "
				+ "FROM LifeProposal x WHERE :fromDate < x.submittedDate  AND x.submittedDate < :toDate GROUP BY year,month";

		public static final String ALL_POLICY_LIFE = "SELECT COUNT(x.id), FUNC('MONTH', x.commenmanceDate) AS month, FUNC('YEAR', x.commenmanceDate) AS year "
				+ "FROM LifePolicy x WHERE :fromDate < x.commenmanceDate  AND x.commenmanceDate < :toDate GROUP BY year,month";
		public static final String LIFE_POLICY = "SELECT COUNT(x.id), FUNC('MONTH', x.commenmanceDate) AS month, FUNC('YEAR', x.commenmanceDate) AS year "
				+ "FROM LifePolicy x WHERE :fromDate < x.commenmanceDate  AND x.commenmanceDate < :toDate GROUP BY year,month";

		public static final String ALL_SUMINSURE_LIFE = "SELECT SUM(x.sumInsured)), FUNC('MONTH', x.startDate) AS month, FUNC('YEAR', x.startDate) AS year "
				+ "FROM PolicyInsuredPerson x WHERE :fromDate < x.startDate  AND x.startDate < :toDate GROUP BY year,month";
		public static final String LIFE_SUMINSURE = "SELECT SUM(x.sumInsured), FUNC('MONTH', x.startDate) AS month, FUNC('YEAR', x.startDate) AS year "
				+ "FROM PolicyInsuredPerson x WHERE :fromDate < x.startDate  AND x.startDate < :toDate GROUP BY year,month";

		public static final String ALL_PREMIUN_LIFE = "SELECT SUM(x.premium), FUNC('MONTH', x.startDate) AS month, FUNC('YEAR', x.startDate) AS year "
				+ "FROM PolicyInsuredPerson x WHERE :fromDate < x.startDate  AND x.startDate < :toDate GROUP BY year,month";
		public static final String LIFE_PREMIUN = "SELECT SUM(x.premium), FUNC('MONTH', x.startDate) AS month, FUNC('YEAR', x.startDate) AS year "
				+ "FROM PolicyInsuredPerson x WHERE :fromDate < x.startDate  AND x.startDate < :toDate GROUP BY year,month";

		public static final String ALL_COMMISSION = "SELECT SUM(x.commission), FUNC('MONTH', x.commissionStartDate) AS month, FUNC('YEAR', x.commissionStartDate) AS year "
				+ "FROM AgentCommission x WHERE :fromDate < x.commissionStartDate  AND x.commissionStartDate < :toDate GROUP BY year,month";
		public static final String LIFE_AGENT_COMMISSION = "SELECT SUM(x.commission), FUNC('MONTH', x.commissionStartDate) AS month, FUNC('YEAR', x.commissionStartDate) AS year "
				+ "FROM AgentCommission x WHERE :fromDate < x.commissionStartDate  AND x.commissionStartDate < :toDate AND x.referenceType = :lifePolicy GROUP BY year,month ";
	}

	private int year;
	private Date startDate;
	private Date endDate;

	private void loadPeriod(int year) {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.set(Calendar.YEAR, year);
		cal_1.set(Calendar.MONTH, Calendar.APRIL);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		cal_1.set(Calendar.HOUR_OF_DAY, 0);
		cal_1.set(Calendar.MINUTE, 0);
		cal_1.set(Calendar.SECOND, 0);
		startDate = cal_1.getTime();

		Calendar cal_2 = Calendar.getInstance();
		cal_2.set(Calendar.YEAR, year + 1);
		cal_2.set(Calendar.MONTH, Calendar.MARCH);
		cal_2.set(Calendar.DAY_OF_MONTH, 31);
		cal_2.set(Calendar.HOUR_OF_DAY, 23);
		cal_2.set(Calendar.MINUTE, 59);
		cal_2.set(Calendar.SECOND, 59);
		endDate = cal_2.getTime();
	}

	@Override
	public FinancialReport findFinancialReport(FinancialReportCriteria criteria) {
		FinancialReport result = null;
		try {
			// Financial
			SummaryReportType summaryReportType = criteria.getSummaryReportType();
			year = criteria.getFinancialYear();
			loadPeriod(year);
			switch (summaryReportType) {
				case ALL_PROPOSAL: {
					result = findAllProposal(summaryReportType);
				}
					break;

				case LIFE_PROPOSAL: {
					result = findLifeProposal(summaryReportType);
				}
					break;
				case ALL_POLICY: {
					result = findAllPolicy(summaryReportType);
				}
					break;

				case LIFE_POLICY: {
					result = findLifePolicy(summaryReportType);
				}
					break;
				case ALL_PREMIUN: {
					result = findAllPremium(summaryReportType);
				}
					break;

				case LIFE_PREMIUM: {
					result = findLifePremium(summaryReportType);
				}
					break;
				case ALL_SUMINSURE: {
					result = findAllSumInsured(summaryReportType);
				}
					break;

				case LIFE_SUMINSURE: {
					result = findLifeSumInsured(summaryReportType);
				}
					break;
				case ALL_COMMISSION: {
					result = findAllAgentCommission(summaryReportType);
				}
					break;

				case LIFE_AGENT_COMMISSION: {
					result = findLifeAgentCommission(summaryReportType);
				}
					break;
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of SummaryReport by criteria.", pe);
		}
		return result;
	}

	private String getMonthString(int month) {
		String result = null;
		switch (month) {
			case 1:
				result = "JAN-";
				break;
			case 2:
				result = "FEB-";
				break;
			case 3:
				result = "MAR-";
				break;
			case 4:
				result = "APR-";
				break;
			case 5:
				result = "MAY-";
				break;
			case 6:
				result = "JUN-";
				break;
			case 7:
				result = "JUL-";
				break;
			case 8:
				result = "AUG-";
				break;
			case 9:
				result = "SEP-";
				break;
			case 10:
				result = "OCT-";
				break;
			case 11:
				result = "NOV-";
				break;
			case 12:
				result = "DEC-";
				break;
		}
		return result;
	}

	private void setNullValueOfMonth(Map<String, Double> tmpMap, int currentMonth) {
		for (; currentMonth < 12; currentMonth++) {
			tmpMap.put(getMonthString(currentMonth), null);
		}
	}

	private void setDefaultValueOfMonth(Map<String, Double> tmpMap, int currentMonth) {
		for (int i = 0; i <= currentMonth; i++) {
			tmpMap.put(getMonthString(currentMonth), 0.0);
		}
	}

	private void populateData(FinancialReport summaryReport, String reportItemType, List<Object> objList) {
		Map<String, Double> tmpMap = new HashMap<String, Double>();

		int endYear = year + 1;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		if (currentYear < year) {
			tmpMap.put("APR-" + year, 0.0);
			tmpMap.put("MAY-" + year, 0.0);
			tmpMap.put("JUN-" + year, 0.0);
			tmpMap.put("JUL-" + year, 0.0);
			tmpMap.put("AUG-" + year, 0.0);
			tmpMap.put("SEP-" + year, 0.0);
			tmpMap.put("OCT-" + year, 0.0);
			tmpMap.put("NOV-" + year, 0.0);
			tmpMap.put("DEC-" + year, 0.0);
			tmpMap.put("JAN-" + endYear, 0.0);
			tmpMap.put("FEB-" + endYear, 0.0);
			tmpMap.put("MAR-" + endYear, 0.0);
		} else {
			tmpMap.put("APR-" + year, 3 <= currentMonth ? 0.0 : null);
			tmpMap.put("MAY-" + year, 4 <= currentMonth ? 0.0 : null);
			tmpMap.put("JUN-" + year, 5 <= currentMonth ? 0.0 : null);
			tmpMap.put("JUL-" + year, 6 <= currentMonth ? 0.0 : null);
			tmpMap.put("AUG-" + year, 7 <= currentMonth ? 0.0 : null);
			tmpMap.put("SEP-" + year, 8 <= currentMonth ? 0.0 : null);
			tmpMap.put("OCT-" + year, 9 <= currentMonth ? 0.0 : null);
			tmpMap.put("NOV-" + year, 10 <= currentMonth ? 0.0 : null);
			tmpMap.put("DEC-" + year, 11 <= currentMonth ? 0.0 : null);
			tmpMap.put("JAN-" + endYear, null);
			tmpMap.put("FEB-" + endYear, null);
			tmpMap.put("MAR-" + endYear, null);
		}
		List<String> xSeriesList = new ArrayList<String>();
		xSeriesList.add("APR-" + year);
		xSeriesList.add("MAY-" + year);
		xSeriesList.add("JUN-" + year);
		xSeriesList.add("JUL-" + year);
		xSeriesList.add("AUG-" + year);
		xSeriesList.add("SEP-" + year);
		xSeriesList.add("OCT-" + year);
		xSeriesList.add("NOV-" + year);
		xSeriesList.add("DEC-" + year);
		xSeriesList.add("JAN-" + endYear);
		xSeriesList.add("FEB-" + endYear);
		xSeriesList.add("MAR-" + endYear);

		for (Object obj : objList) {
			Object[] objArray = (Object[]) obj;
			double rate = Double.parseDouble(objArray[0].toString());
			int month = (Integer) objArray[1];
			int year = (Integer) objArray[2];
			Integer m = (int) month;
			String xSeries = getMonthString(m) + year;
			tmpMap.put(xSeries, rate);
		}
		for (String xSeries : xSeriesList) {
			summaryReport.addReportItem(reportItemType, xSeries, tmpMap.get(xSeries));
		}
	}

	private FinancialReport findAllProposal(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {

			Query lifeQuery = em.createQuery(Queries.ALL_PROPOSAL_LIFE);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());

		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of All Proposal.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findAllPolicy(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {

			Query lifeQuery = em.createQuery(Queries.ALL_PROPOSAL_LIFE);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of All Policy.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findLifeProposal(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {
			Query lifeQuery = em.createQuery(Queries.LIFE_PROPOSAL);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of Life Proposal.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findLifePolicy(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {
			Query lifeQuery = em.createQuery(Queries.LIFE_POLICY);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of Life Policy.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findAllSumInsured(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {

			Query lifeQuery = em.createQuery(Queries.LIFE_SUMINSURE);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());

		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of All Premium.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findLifeSumInsured(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {
			Query lifeQuery = em.createQuery(Queries.LIFE_SUMINSURE);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of Life Sum Insured.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findAllPremium(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {

			Query lifeQuery = em.createQuery(Queries.LIFE_POLICY);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of All Premium.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findLifePremium(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {
			Query lifeQuery = em.createQuery(Queries.LIFE_PREMIUN);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of Life Premium.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findAllAgentCommission(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {

			Query lifeQuery = em.createQuery(Queries.LIFE_AGENT_COMMISSION);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			// FIXME CHECK REFTYPE
			lifeQuery.setParameter("lifePolicy", PolicyReferenceType.ENDOWNMENT_LIFE_POLICY);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());

		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of All AgentCommission.", pe);
		}
		return summaryReport;
	}

	private FinancialReport findLifeAgentCommission(SummaryReportType type) {
		FinancialReport summaryReport = new FinancialReport();
		try {
			Query lifeQuery = em.createQuery(Queries.LIFE_AGENT_COMMISSION);
			lifeQuery.setParameter("fromDate", startDate);
			lifeQuery.setParameter("toDate", endDate);
			// FIXME CHECK REFTYPE
			lifeQuery.setParameter("lifePolicy", PolicyReferenceType.ENDOWNMENT_LIFE_POLICY);
			populateData(summaryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find SummaryReport of Life AgentCommission.", pe);
		}
		return summaryReport;
	}

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		String motorQueryString = "SELECT COUNT(x.id), FUNC('MONTH', x.submittedDate) AS month, FUNC('YEAR', x.submittedDate) AS year FROM MotorProposal x WHERE :fromDate < x.submittedDate  AND x.submittedDate < :toDate GROUP BY year,month";
		Query q = em.createQuery(motorQueryString);
		List<Object> result = q.getResultList();
		for (Object obj : result) {
			Object[] objArray = (Object[]) obj;
			System.out.println(objArray[0] + " : " + objArray[1] + " : " + objArray[2]);
		}
		em.close();
	}
}
