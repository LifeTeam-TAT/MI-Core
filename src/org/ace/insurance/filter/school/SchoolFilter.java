package org.ace.insurance.filter.school;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.filter.school.interfaces.ISchoolFilter;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("SchoolFilter")
public class SchoolFilter extends BasicDAO implements ISchoolFilter {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SCH001> find(SchoolFilterCriteria criteria) {
		List<SCH001> results = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + SCH001.class.getName());
			buffer.append("(s.id,s.name,s.address,s.phoneNo,s.schoolCodeNo,s.schoolType,s.schoolLevelType,t.name,p.name)");
			buffer.append("FROM School s LEFT JOIN s.township  t left join t.district d LEFT JOIN d.province p WHERE s.name is not null");
			if (criteria.getSchoolType() != null) {
				buffer.append(" AND s.schoolType=:schoolType");
			}
			if (criteria.getProvince() != null) {
				buffer.append(" AND p.name LIKE :province");
			}
			if (criteria.getTownship() != null) {
				buffer.append(" AND t.name LIKE :township");
			}

			Query query = em.createQuery(buffer.toString());
			if (criteria.getSchoolType() != null) {
				query.setParameter("schoolType", criteria.getSchoolType());
			}
			if (criteria.getProvince() != null) {
				query.setParameter("province", "%" + criteria.getProvince().getName() + "%");
			}
			if (criteria.getTownship() != null) {
				query.setParameter("township", "%" + criteria.getTownship().getName() + "%");
			}
			query.setMaxResults(30);
			results = query.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find School by LFP001 : ", pe);
		}

		return results;
	}

}
