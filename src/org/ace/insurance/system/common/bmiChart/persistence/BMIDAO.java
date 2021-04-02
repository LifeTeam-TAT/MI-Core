package org.ace.insurance.system.common.bmiChart.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.system.common.bmiChart.BMIChart;
import org.ace.insurance.system.common.bmiChart.persistence.interfaces.IBMIDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("BMIDAO")
public class BMIDAO extends BasicDAO implements IBMIDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BMIChart> findAllBMIChart() throws DAOException {
		List<BMIChart> bmiChartList;
		try {
			Query query = em.createNamedQuery("BMIChart.findAll");
			bmiChartList = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find All BMI Chart", pe);
		}
		return bmiChartList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertBMIChart(BMIChart bmiChart) throws DAOException {
		try {
			em.persist(bmiChart);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert BMI Chart", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBMIChart(BMIChart bmiChart) throws DAOException {
		try {
			em.merge(bmiChart);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update BMI Chart", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteBMIChart(BMIChart bmiChart) throws DAOException {
		try {
			bmiChart = em.find(BMIChart.class, bmiChart.getId());
			em.remove(bmiChart);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete BMI Chart", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int findPoundByAgeAndHeight(int age, int height) {
		int bmiWeight;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("Select b.weight from BMIChart b where b.age=:age and b.height=:height");
			Query query = em.createQuery(builder.toString());
			query.setParameter("age", age);
			query.setParameter("height", height);
			bmiWeight = (int) query.getSingleResult();
		} catch (NoResultException n) {
			bmiWeight=0;
		} catch (PersistenceException pe) {
			throw translate("Faield to find Pound By Age And Height", pe);
		}
		return bmiWeight;
	}

}
