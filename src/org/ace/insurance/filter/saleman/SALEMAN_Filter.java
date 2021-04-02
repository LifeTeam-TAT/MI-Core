package org.ace.insurance.filter.saleman;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.PermanentAddress;
import org.ace.insurance.filter.cirteria.CRIA001;
import org.ace.insurance.filter.saleman.interfaces.ISALEMAN_Filter;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value = "SALEMAN_Filter")
public class SALEMAN_Filter extends BasicDAO implements ISALEMAN_Filter {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SAMN001> find(CRIA001 item, String value) {
		List<SAMN001> result = new ArrayList<SAMN001>();
		StringBuffer bufferer = new StringBuffer("SELECT ");
		bufferer.append("c.id, ");
		bufferer.append("c.codeNo, ");
		bufferer.append("c.licenseNo, ");
		bufferer.append("c.name, ");
		bufferer.append("c.idNo, ");
		bufferer.append("c.address, ");
		bufferer.append("c.branch.name ");
		bufferer.append("FROM SaleMan c ");
		switch (item) {
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
				bufferer.append("WHERE c.idNo = '" + value + "' AND c.idType = org.ace.insurance.common.IdType.NRCNO");
			}
				break;
			case FRCNO: {
				bufferer.append("WHERE c.idNo = '" + value + "' AND c.idType = org.ace.insurance.common.IdType.FRCNO");
			}
				break;
			case PASSPORTNO: {
				bufferer.append("WHERE c.idNo = '" + value + "' AND c.idType = org.ace.insurance.common.IdType.PASSPORTNO");
			}
				break;
			default: {
				bufferer.append("WHERE CONCAT(c.name.firstName, ' ', c.name.middleName, ' ', c.name.lastName) like '%" + value + "%' "
						+ "OR CONCAT(c.name.firstName, ' ', c.name.middleName, c.name.lastName) like '%" + value + "%'");
			}
		}
		Query query = em.createQuery(bufferer.toString());
		query.setMaxResults(30);

		List<Object[]> objectList = query.getResultList();

		System.out.println("ResultList : " + objectList.size());
		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			String agentCode = (String) obj[1];
			String licenseNo = (String) obj[2];
			Name name = (Name) obj[3];
			String idNo = (String) obj[4];
			PermanentAddress address = (PermanentAddress) obj[5];
			String branch = (String) obj[6];
			result.add(new SAMN001(id, agentCode, licenseNo, name.getFullName(), idNo, address.getFullAddress(), branch));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SAMN001> find(int maxResult) {
		List<SAMN001> result = new ArrayList<SAMN001>();
		StringBuffer bufferer = new StringBuffer("SELECT c.id, c.codeNo, c.licenseNo, c.name, c.idNo, c.address, c.branch.name FROM SaleMan c ORDER BY c.id DESC");
		Query query = em.createQuery(bufferer.toString());
		query.setMaxResults(maxResult);
		List<Object[]> objectList = query.getResultList();

		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			String agentCode = (String) obj[1];
			String licenseNo = (String) obj[2];
			Name name = (Name) obj[3];
			String idNo = (String) obj[4];
			PermanentAddress address = (PermanentAddress) obj[5];
			String branch = (String) obj[6];
			result.add(new SAMN001(id, agentCode, licenseNo, name.getFullName(), idNo, address.getFullAddress(), branch));
		}
		return result;
	}

}