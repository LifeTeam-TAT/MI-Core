package org.ace.insurance.system.common.reinsuranceRatio.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceRatio;
import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceRationDTO;
import org.ace.insurance.system.common.reinsuranceRatio.persistence.interfaces.IReinsuranceRatioDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ReinsuranceRatioDAO")
public class ReinsuranceRatioDAO extends BasicDAO implements IReinsuranceRatioDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewReinsuranceRatio(ReinsuranceRatio reinsuranceRatio) {
		try {
			em.persist(reinsuranceRatio);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to add New ReinsuranceRatio", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateReinsuranceRatio(ReinsuranceRatio reinsuranceRatio) {
		try {
			em.merge(reinsuranceRatio);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ReinsuranceRatio", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ReinsuranceRatio> findReinsuranceRatioListByProductGroupId(String productGroupId) {
		List<ReinsuranceRatio> reinsuranceRatioList = new ArrayList<>();
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("Select c from ReinsuranceRatio c where c.productGroup.id=:id ");
			Query query = em.createQuery(queryString.toString());
			query.setParameter("id", productGroupId);
			reinsuranceRatioList = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ReinsuranceRatioList By ProductGroup Id", pe);
		}
		return reinsuranceRatioList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndateByReInRatioId(ReinsuranceRatio reinsuranceRatio) {
		try {
			StringBuilder query = new StringBuilder();
			query.append("Update ReinsuranceRatio r set r.endDate=:endDate where r.id=:id ");
			Query q = em.createQuery(query.toString());
			q.setParameter("endDate", reinsuranceRatio.getEndDate());
			q.setParameter("id", reinsuranceRatio.getId());
			q.executeUpdate();
		} catch (PersistenceException pe) {
			throw translate("Failed to update End Date By ReInRatio Id", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ReinsuranceRationDTO> findReinsuranceDetailRatioList(String productGroup, double sumInsured) {
		List<ReinsuranceRationDTO> resultList = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + ReinsuranceRationDTO.class.getName());
			buffer.append(" ( rdr.coinsuranceCompany, rr.commission, rdr.minSI, rdr.maxSI ) ");
			buffer.append("FROM ReinsuranceRatio rr ");
			buffer.append("LEFT OUTER JOIN  rr.reinsuranceDetailRatioList rdr ");
			buffer.append("WHERE rr.productGroup.id = :productGroup ");
			buffer.append("AND rdr.minSI < :sumInsured ");
			buffer.append("AND rr.endDate IS NULL ");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("productGroup", productGroup);
			q.setParameter("sumInsured", sumInsured);
			resultList = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Ratio DTO List..." + productGroup, pe);
		}

		return resultList;
	}

}
