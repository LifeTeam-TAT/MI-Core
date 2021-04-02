package org.ace.insurance.medical.claim.frontService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Test {

	public static void main(String[] args) {
		EntityManager em = Persistence.createEntityManagerFactory("JPA").createEntityManager();
		em.getTransaction().begin();
		StringBuffer policyNo = new StringBuffer("SELECT m.policyNo FROM MotorPolicy m WHERE m.motorProposal.proposalNo = :proposalNo");
		policyNo.append(" UNION ALL ");
		policyNo.append("SELECT m.motorPolicy.policyNo FROM MotorProposal m WHERE m.proposalNo = :proposalNo");
		StringBuffer buffer = new StringBuffer("SELECT MAX(m.endorsementDate) FROM MotorEndorseInfo m WHERE m.policyNo = (" + policyNo.toString() + ")");
		Query query = em.createQuery(buffer.toString());
		query.setParameter("proposalNo", "FNI-HO/CM/ED/0000000070/4-2019");
		List<Object> results = query.getResultList();
		em.flush();
		em.getTransaction().commit();
		System.out.println(results);
	}

}