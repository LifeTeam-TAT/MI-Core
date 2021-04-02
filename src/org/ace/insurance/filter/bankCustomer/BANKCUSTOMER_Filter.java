package org.ace.insurance.filter.bankCustomer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.ace.insurance.filter.bankCustomer.interfaces.IBANKCUSTOMER_Filter;
import org.ace.insurance.filter.cirteria.BANKCUST001;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value = "BANKCUSTOMER_Filter")
public class BANKCUSTOMER_Filter extends BasicDAO implements IBANKCUSTOMER_Filter {
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BNK001> find(BANKCUST001 item, String value) {
		List<BNK001> result = new ArrayList<BNK001>();
		StringBuffer bufferer = new StringBuffer("SELECT ");
		bufferer.append("c.id, ");
		bufferer.append("c.name, ");
		bufferer.append("c.description, ");
		bufferer.append("c.acode ");
		bufferer.append("FROM Bank c ");
		switch (item) {
			case NAME: {
				bufferer.append("WHERE c.name  like '%" + value + "%'");
			}
				break;
			case DESCRIPTION: {
				bufferer.append("WHERE c.description  like '%" + value + "%'");
			}
				break;
			case CODE: {
				bufferer.append("WHERE c.code like '%" + value + "%'");
			}
				break;
		}
		Query query = em.createQuery(bufferer.toString());
		List<Object[]> objectList = query.getResultList();
		System.out.println("ResultList : " + objectList.size());
		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			String name = (String) obj[1];
			String description = (String) obj[2];
			String code = (String) obj[3];
			result.add(new BNK001(id, name, description, code));
		}
		return result;
	}
}
