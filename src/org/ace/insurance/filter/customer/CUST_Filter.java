package org.ace.insurance.filter.customer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.filter.cirteria.CRIA001;
import org.ace.insurance.filter.customer.interfaces.ICUST_Filter;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value = "CUST_Filter")
public class CUST_Filter extends BasicDAO implements ICUST_Filter {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CUST001> find(CRIA001 item, String value) {
		List<CUST001> result = new ArrayList<CUST001>();
		StringBuffer bufferer = new StringBuffer("SELECT ");
		bufferer.append("c.id, ");
		bufferer.append("c.name, ");
		bufferer.append("c.fullIdNo, ");
		bufferer.append("c.residentAddress , c.initialId ");
		bufferer.append("FROM Customer c ");
		switch (item) {
			case FULLNAME: {
				bufferer.append(" WHERE FUNCTION('REPLACE',CONCAT(c.initialId,c.name.firstName,c.name.middleName,c.name.lastName),' ','')");
				bufferer.append(" LIKE '%" + value + "%'");
			}
				break;
			case FIRSTNAME: {
				bufferer.append("WHERE c.name.firstName like '%" + value + "%'");
			}
				break;
			case MIDDLENAME: {
				bufferer.append("WHERE c.name.middleName like '%" + value + "%'");
			}
				break;
			case LASTNAME: {
				bufferer.append("WHERE c.name.lastName like '%" + value + "%'");
			}
				break;
			case NRCNO: {
				bufferer.append("WHERE c.fullIdNo like '%" + value + "%' AND c.idType = org.ace.insurance.common.IdType.NRCNO");
			}
				break;
			case FRCNO: {
				bufferer.append("WHERE c.fullIdNo like '%" + value + "%' AND c.idType = org.ace.insurance.common.IdType.FRCNO");
			}
				break;
			case PASSPORTNO: {
				bufferer.append("WHERE c.fullIdNo like '%" + value + "%' AND c.idType = org.ace.insurance.common.IdType.PASSPORTNO");
			}
				break;
			default: {
				bufferer.append(" WHERE CONCAT(c.initialId,' ',c.name.firstName, ' ', c.name.middleName, ' ', c.name.lastName) like '%" + value + "%' "
						+ "OR CONCAT(c.initialId,' ',c.name.firstName, ' ', c.name.middleName, c.name.lastName) like '%" + value + "%'"
						+ " OR CONCAT(c.initialId,' ',c.name.firstName) like '%" + value + "%'");
			}
		}
		Query query = em.createQuery(bufferer.toString());
		List<Object[]> objectList = query.getResultList();
		System.out.println("ResultList : " + objectList.size());
		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			Name name = (Name) obj[1];
			String fullIdNo = (String) obj[2];
			ResidentAddress address = (ResidentAddress) obj[3];
			String initialid = (String) obj[4];
			result.add(new CUST001(id, name.getFullName(), fullIdNo, address.getFullResidentAddress(), initialid));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CUST001> find(int maxResult) {
		List<CUST001> result = new ArrayList<CUST001>();
		StringBuffer bufferer = new StringBuffer(
				"SELECT NEW org.ace.insurance.filter.customer.CUST001(c.id, c.name, c.fullIdNo ,  c.residentAddress , c.initialId)  FROM Customer c ORDER BY c.id DESC");
		Query query = em.createQuery(bufferer.toString());
		query.setMaxResults(maxResult);
		result = query.getResultList();
		return result;
	}
}