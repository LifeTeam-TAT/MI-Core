package org.ace.insurance.accept;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class QueryTest {
	EntityManager em=Persistence.createEntityManagerFactory("JPA").createEntityManager();
	
	public double findActiveRate(){
		StringBuffer query = new StringBuffer();
		double rate = 0.0;
		query.append("Select rate from RateInfo where rateType = 'CS' and lastModify = 1 And Cur = 'USD'");
		Query q = em.createNativeQuery(query.toString());
		Number result = (Number)q.getSingleResult();
		rate = result.doubleValue();
		return rate;
	}
	
	public static void main(String[] args) {
		QueryTest  t = new QueryTest();
		t.findActiveRate();
	}
}
