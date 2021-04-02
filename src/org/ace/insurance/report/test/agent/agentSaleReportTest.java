package org.ace.insurance.report.test.agent;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.ace.insurance.report.agent.AgentSaleReport;
import org.ace.insurance.system.common.agent.Agent;

public class agentSaleReportTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) throws ParseException {
		List<Object[]> o = new ArrayList<Object[]>();
		emf = Persistence.createEntityManagerFactory("JPA");
		em = emf.createEntityManager();

		// for life
		StringBuffer query = new StringBuffer();

		// query.append("Select a, Count(lp.id),  Sum (pip.basicTermPremium + pip.addOnTermPremium), Sum (ac.commission) "
		// +
		// "From AgentCommission ac " +
		// " INNER JOIN LifePolicy lp ON ac.referenceNo = lp.id " +
		// " INNER JOIN Agent a ON a.id = ac.agent.id " +
		// " INNER JOIN PolicyInsuredPerson pip ON  pip.lifePolicy.id = lp.id  "
		// +
		// " WHERE ac.commissionStartDate >= :startDate AND ac.commissionStartDate <= :endDate "
		// +
		// " GROUP BY a ");
		query.append("Select a, Count(distinct lp.id),  Sum (pip.basicTermPremium + pip.addOnTermPremium), Sum (ac.commission), pip.product.name "
				+ "From AgentCommission ac "
				+ " INNER JOIN LifePolicy lp ON ac.referenceNo = lp.id "
				+ " INNER JOIN Agent a ON a.id = ac.agent.id "
				+ " INNER JOIN PolicyInsuredPerson pip ON  pip.lifePolicy.id = lp.id  "
				+ " WHERE ac.commissionStartDate >= :startDate AND ac.commissionStartDate <= :endDate "
				+ " GROUP BY a, pip.product.name  ");

		// for fire
		// Query
		// query=em.createQuery(" Select a, Count(fp.id), Sum (fppi.basicTermPremium + fppi.addOnTermPremium ), Sum (ac.commission)  From AgentCommission ac "+
		// " INNER JOIN FirePolicy fp ON ac.referenceNo = fp.id " +
		// " INNER JOIN Agent a ON a.id = ac.agent.id " +
		// " INNER JOIN PolicyBuildingInfo pbi ON fp.id = pbi.firePolicy.id  " +
		// " INNER JOIN FirePolicyProductInfo fppi ON pbi.id = fppi.policyBuildingInfo.id "
		// +
		// " where ac.commissionStartDate >= :startDate AND ac.commissionStartDate <= :endDate  GROUP BY  a ");

		// Query
		// query=em.createQuery(" select Count(a.id),Count(a.codeNo) from Agent a group by a.id");
		// o = query.getResultList();

		// Query query =
		// em.createQuery(" Select a, Count(mp.id), Sum (pv.basicTermPremium + pv.addOnTermPremium ), Sum (ac.commission)  From AgentCommission ac "
		// +
		// " INNER JOIN MotorPolicy mp ON ac.referenceNo = mp.id " +
		// " INNER JOIN Agent a ON a.id = ac.agent.id " +
		// " INNER JOIN PolicyVehicle pv ON mp.id = pv.motorPolicy.id  " +
		// " where ac.commissionStartDate >= :startDate AND ac.commissionStartDate <= :endDate  GROUP BY  a ");
		// "   GROUP BY  a ");

		// AgentSaleCriteria criteria = new AgentSaleCriteria();
		// Branch b = new Branch();
		// b.setId("BANCH00000000000000129032013");
		// criteria.setBranch(b);
		//
		// StringBuffer query = new StringBuffer();
		// query.append(" Select a, Count(mp.id), Sum (pv.basicTermPremium + pv.addOnTermPremium), Sum (ac.commission)  From AgentCommission ac ");
		// query.append(" INNER JOIN MotorPolicy mp ON ac.referenceNo = mp.id ");
		// query.append(" INNER JOIN Agent a ON a.id = ac.agent.id ");
		// query.append(" INNER JOIN PolicyVehicle pv ON mp.id = pv.motorPolicy.id  ");
		//
		// if (criteria.getStartDate() != null) {
		// criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
		// query.append(" AND ac.commissionStartDate >= :startDate");
		// }
		// if (criteria.getEndDate() != null) {
		// criteria.setEndDate(Utils.resetStartDate(criteria.getEndDate()));
		// query.append(" AND ac.commissionStartDate <= :endDate");
		// }
		// if (criteria.getAgent() != null) {
		// query.append(" AND ac.agent.id = :agentID");
		// }
		// if (criteria.getBranch() != null) {
		// query.append(" AND a.branch.id = :branchID");
		// }
		// query.append(" GROUP BY  a ");
		//
		Query q = em.createQuery(query.toString());
		// if (criteria.getStartDate() != null) {
		// q.setParameter("startDate", criteria.getStartDate());
		// }
		// if (criteria.getEndDate() != null) {
		// q.setParameter("endDate", criteria.getEndDate());
		// }
		// if (criteria.getAgent() != null) {
		// q.setParameter("agentID", criteria.getAgent().getId());
		// }
		// if (criteria.getBranch() != null) {
		// q.setParameter("branchID", criteria.getBranch().getId());
		// }
		//
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Date startDate= Utils.createDate(2013, 12, 30);
		// Date endDate = Utils.createDate(2014, 2, 5);

		// q.setParameter("startDate", startDate);
		// q.setParameter("endDate", endDate);
		o = q.getResultList();

		HashMap<String, List<AgentSaleReport>> hm = new HashMap<String, List<AgentSaleReport>>();
		List<AgentSaleReport> asr = null;
		for (Object[] raw : o) {
			Agent agent = (Agent) raw[0];
			int count = ((Number) raw[1]).intValue();
			double premium = ((Double) raw[2]).doubleValue();
			double comission = ((Double) raw[3]).doubleValue();
			String name = ((String) raw[4]).toString();
			System.out.println("Product Name=>" + name);
			System.out.println(" count = > " + count);
			System.out.println("premium => " + premium + "," + "commission => "
					+ comission);
			System.out.println("Agent Name =>" + agent.getName());
			String id = ((Agent) raw[0]).getId();
			System.out.println();
			// int count = ((Number)raw[1]).intValue();
			AgentSaleReport agentSaleReport = new AgentSaleReport(agent, count);
			agentSaleReport.setLifePolicyCount(count);
			agentSaleReport.setLifePolicyTotalPremium(premium);
			agentSaleReport.setLifePolicyTotalCommission(comission);

			if (hm.get(agent.getCodeNo()) == null) {
				asr = new ArrayList<AgentSaleReport>();
			}
			asr.add(agentSaleReport);
			hm.put(name, asr);

		}

		System.out.println(o.size());
	}

}
