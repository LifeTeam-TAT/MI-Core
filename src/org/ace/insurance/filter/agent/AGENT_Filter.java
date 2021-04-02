package org.ace.insurance.filter.agent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.Utils;
import org.ace.insurance.filter.agent.interfaces.IAGENT_Filter;
import org.ace.insurance.filter.cirteria.CRIA001;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value = "AGENT_Filter")
public class AGENT_Filter extends BasicDAO implements IAGENT_Filter {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AGNT001> find(CRIA001 item, String value) {
		List<AGNT001> result = new ArrayList<AGNT001>();
		StringBuffer bufferer = new StringBuffer("SELECT c.id, c.codeNo, c.liscenseNo,c.initialId, c.name, c.fullIdNo, c.residentAddress,");
		bufferer.append(" c.branch.branchCode, c.liscenseExpiredDate, CASE WHEN (c.liscenseExpiredDate < :today ) THEN TRUE ELSE FALSE END ");
		bufferer.append("FROM Agent c ");
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
				bufferer.append("WHERE c.fullIdNo LIKE '%" + value + "%' AND c.idType = org.ace.insurance.common.IdType.NRCNO");
			}
				break;
			case FRCNO: {
				bufferer.append("WHERE c.fullIdNo LIKE '%" + value + "%' AND c.idType = org.ace.insurance.common.IdType.FRCNO");
			}
				break;
			case PASSPORTNO: {
				bufferer.append("WHERE c.fullIdNo LIKE '%" + value + "%' AND c.idType = org.ace.insurance.common.IdType.PASSPORTNO");
			}
				break;
			case LISCENSENO: {
				bufferer.append("WHERE c.liscenseNo LIKE '%" + value + "%'");
			}
				break;
			default: {
				bufferer.append("WHERE CONCAT(c.name.firstName, ' ', c.name.middleName, ' ', c.name.lastName) like '%" + value + "%' "
						+ "OR CONCAT(c.name.firstName, ' ', c.name.middleName, c.name.lastName) like '%" + value + "%'");
			}
		}
		Query query = em.createQuery(bufferer.toString());
		query.setParameter("today", Utils.resetStartDate(new Date()), TemporalType.DATE);
		List<Object[]> objectList = query.getResultList();
		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			String agentCode = (String) obj[1];
			String licenseNo = (String) obj[2];
			String initialId = (String) obj[3];
			Name name = (Name) obj[4];
			String idNo = (String) obj[5];
			ResidentAddress address = (ResidentAddress) obj[6];
			String branch = (String) obj[7];
			Date liscenseExpiredDate = (Date) obj[8];
			boolean isExpireAgent = (boolean) obj[9];
			String fullName = initialId + name.getFullName();
			result.add(new AGNT001(id, agentCode, licenseNo, fullName, idNo, address.getFullResidentAddress(), branch, liscenseExpiredDate, isExpireAgent));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AGNT001> find(int maxResult) {
		List<AGNT001> result = new ArrayList<AGNT001>();
		StringBuffer bufferer = new StringBuffer(
				"SELECT a.id, a.codeNo, a.liscenseNo,a.initialId, a.name, a.fullIdNo, a.residentAddress, a.branch.branchCode , a.liscenseExpiredDate, CASE WHEN (a.liscenseExpiredDate < :today ) THEN TRUE ELSE FALSE END ");
		bufferer.append("FROM Agent a ORDER BY a.id DESC");
		Query query = em.createQuery(bufferer.toString());
		query.setParameter("today", Utils.resetStartDate(new Date()), TemporalType.DATE);
		query.setMaxResults(maxResult);
		List<Object[]> objectList = query.getResultList();

		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			String agentCode = (String) obj[1];
			String licenseNo = (String) obj[2];
			String initialId = (String) obj[3];
			Name name = (Name) obj[4];
			String idNo = (String) obj[5];
			ResidentAddress address = (ResidentAddress) obj[6];
			String branch = (String) obj[7];
			Date liscenseExpiredDate = (Date) obj[8];
			boolean isExpireAgent = (boolean) obj[9];
			String fullName = initialId + name.getFullName();
			result.add(new AGNT001(id, agentCode, licenseNo, fullName, idNo, address.getFullResidentAddress(), branch, liscenseExpiredDate, isExpireAgent));
		}
		return result;
	}
}