package org.ace.insurance.life.claim.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.life.claim.persistence.interfaces.IClaimMedicalFeesDAO;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "ClaimMedicalFeesDAO")
public class ClaimMedicalFeesDAO extends BasicDAO implements IClaimMedicalFeesDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(ClaimMedicalFees medicalFees) {
		try {
			em.persist(medicalFees);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Bank", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ClaimMedicalFees medicalFees) {
		try {
			medicalFees = em.merge(medicalFees);
			em.remove(medicalFees);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Bank", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ClaimMedicalFees medicalFees) {
		try {
			em.persist(medicalFees);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Bank", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimMedicalFees> findMedicalFeesBySanctionNo(String sanctionNo) {
		List<ClaimMedicalFees> result = null;
		try {
			Query q = em.createQuery("select s from ClaimMedicalFees s where s.sanctionNo=:sanctionNo ");
			q.setParameter("sanctionNo", sanctionNo);
			result = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Bank", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMedicalFeesStaus(List<LifeClaimMedicalFeeDTO> claimMedicalFeeInfoList) throws DAOException {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE ClaimMedicalFees a SET a.status = :status , a.sanctionDate = :sanctionDate , a.sanctionNo = :sanctionNo ");
			buffer.append("WHERE a.id = :id");
			Query query = em.createQuery(buffer.toString());
			for (LifeClaimMedicalFeeDTO sanctionInfo : claimMedicalFeeInfoList) {
				query.setParameter("id", sanctionInfo.getId());
				query.setParameter("status", true);
				query.setParameter("sanctionDate", sanctionInfo.getSanctionDate());
				query.setParameter("sanctionNo", sanctionInfo.getSanctionNo());
				query.executeUpdate();
				em.flush();
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to update AgentCommission", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimMedicalFees> findMedicalFeesByInvoiceNo(String invoiceNo) {
		List<ClaimMedicalFees> result = null;
		try {
			Query q = em.createQuery("select s from ClaimMedicalFees s where s.invoiceNo=:invoiceNo ");
			q.setParameter("invoiceNo", invoiceNo);
			result = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Bank", pe);
		}
		return result;
	}

}
