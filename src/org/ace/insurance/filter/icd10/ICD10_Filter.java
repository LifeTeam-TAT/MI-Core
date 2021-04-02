package org.ace.insurance.filter.icd10;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.ace.insurance.filter.cirteria.CRIAICD10;
import org.ace.insurance.filter.icd10.interfaces.IICD10_Filter;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value = "ICD10_Filter")
public class ICD10_Filter extends BasicDAO implements IICD10_Filter{

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ICD10> find(CRIAICD10 item, String value){
		List<ICD10> result = new ArrayList<ICD10>();
		StringBuffer bufferer = new StringBuffer("SELECT ");
		bufferer.append("c.id, ");
		bufferer.append("c.code, ");
		bufferer.append("c.description ");
		bufferer.append("FROM ICD10 c ");
		switch (item) {
			case CODE: {
				bufferer.append("WHERE c.code like '%" + value + "%'");
			}
			break;
			case DISEASE: {
				bufferer.append("WHERE c.description like '%" + value + "%'");
		}
	}
		Query query = em.createQuery(bufferer.toString());
		List<Object[]> objectList = query.getResultList();
		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			String code = (String) obj[1];
			String description = (String) obj[2];
			result.add(new ICD10(id , code , description));
		}
		return result;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ICD10> find(int maxResult) {
		List<ICD10> result = new ArrayList<ICD10>();
		StringBuffer bufferer = new StringBuffer("SELECT a.id ,a.code, a.description FROM ICD10 a ");
		Query query = em.createQuery(bufferer.toString());
		query.setMaxResults(maxResult);
		List<Object[]> objectList = query.getResultList();

		for (Object[] obj : objectList) {
			String id = (String) obj[0];
			String code = (String) obj[1];
			String description = (String) obj[2];
			
			result.add(new ICD10(id,code,description));
		}
		return result;
	}
}
