package org.ace.insurance.managementreport.customer.persistence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.managementreport.customer.CustomerReport;
import org.ace.insurance.managementreport.customer.persistence.interfaces.ICustomerReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CustomerReportDAO")
public class CustomerReportDAO extends BasicDAO implements ICustomerReportDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByActivePolicies() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			StringBuffer customer = new StringBuffer();
			customer.append("SELECT c.activePolicy, COUNT(c.id) FROM Customer c GROUP BY c.activePolicy");
			Query cQuery = em.createQuery(customer.toString());
			List<Object[]> objList = cQuery.getResultList();
			if (objList != null) {
				for (Object[] object : objList) {
					pieChartMap.put(object[0].toString(), Integer.parseInt(object[1].toString()));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find active customer by active policies.", pe);
		}
		CustomerReport customerReport = new CustomerReport(pieChartMap);
		return customerReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByGender() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			StringBuffer customer = new StringBuffer();
			customer.append("SELECT c.gender, COUNT(DISTINCT c.id) AS TOTAL FROM Customer c  Where c.activePolicy > 0 GROUP BY c.gender");
			Query cQuery = em.createQuery(customer.toString());
			List<Object[]> objList = cQuery.getResultList();
			if (objList != null) {
				for (Object[] object : objList) {
					pieChartMap.put(object[0].toString(), Integer.parseInt(object[1].toString()));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find active customer by gender.", pe);
		}
		CustomerReport customerReport = new CustomerReport(pieChartMap);
		return customerReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByType() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			// individual count
			StringBuffer customerFemale = new StringBuffer();
			customerFemale.append("SELECT COUNT(c.id) FROM Customer c Where c.activePolicy > 0");
			Query iQuery = em.createQuery(customerFemale.toString());
			Number iCount = Integer.parseInt(iQuery.getSingleResult().toString());
			pieChartMap.put("INDIVIDUAL", iCount);
			// organization count
			StringBuffer customerMale = new StringBuffer();
			customerMale.append("SELECT COUNT(o.id) FROM Organization o");
			Query oQuery = em.createQuery(customerMale.toString());
			Number oCount = Integer.parseInt(oQuery.getSingleResult().toString());
			pieChartMap.put("ORGANIZATION", oCount);
		} catch (PersistenceException pe) {
			throw translate("Failed to find active customer by type.", pe);
		}
		CustomerReport customerReport = new CustomerReport(pieChartMap);
		return customerReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByProduct() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			// motor
			//To FIXME by THK
			StringBuffer customerMotor = new StringBuffer();
			customerMotor.append("SELECT COUNT(DISTINCT m.customer) FROM MotorPolicy m");
			Query imQuery = em.createQuery(customerMotor.toString());
			Number imCount = Integer.parseInt(imQuery.getSingleResult().toString());
			pieChartMap.put("M_INDIVIDUAL", imCount);
			StringBuffer organizationMotor = new StringBuffer();
			organizationMotor.append("SELECT COUNT(DISTINCT m.organization) FROM MotorPolicy m");
			Query omQuery = em.createQuery(organizationMotor.toString());
			Number omCount = Integer.parseInt(omQuery.getSingleResult().toString());
			pieChartMap.put("M_ORGANIZATION", omCount);

			// fire
			//TO FIXME by THK
			StringBuffer customerFire = new StringBuffer();
			customerFire.append("SELECT COUNT(DISTINCT f.customer) FROM FirePolicy f");
			Query ifQuery = em.createQuery(customerFire.toString());
			Number ifCount = Integer.parseInt(ifQuery.getSingleResult().toString());
			pieChartMap.put("F_INDIVIDUAL", ifCount);
			StringBuffer organizationFire = new StringBuffer();
			organizationFire.append("SELECT COUNT(DISTINCT f.organization) FROM FirePolicy f");
			Query ofQuery = em.createQuery(organizationFire.toString());
			Number ofCount = Integer.parseInt(ofQuery.getSingleResult().toString());
			pieChartMap.put("F_ORGANIZATION", ofCount);

			// life
			StringBuffer customerLife = new StringBuffer();
			customerLife.append("SELECT COUNT(DISTINCT l.customer) FROM LifePolicy l");
			Query ilQuery = em.createQuery(customerLife.toString());
			Number ilCount = Integer.parseInt(ilQuery.getSingleResult().toString());
			pieChartMap.put("L_INDIVIDUAL", ilCount);
			StringBuffer organizationLife = new StringBuffer();
			organizationLife.append("SELECT COUNT(DISTINCT l.organization) FROM LifePolicy l");
			Query olQuery = em.createQuery(organizationLife.toString());
			Number olCount = Integer.parseInt(olQuery.getSingleResult().toString());
			pieChartMap.put("L_ORGANIZATION", olCount);

		} catch (PersistenceException pe) {
			throw translate("Failed to find active customer by product.", pe);
		}
		CustomerReport customerReport = new CustomerReport(pieChartMap);
		return customerReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerReport findActiveCustomerByLocation() {
		Map<String, Number> pieChartMap = new HashMap<String, Number>();
		try {
			StringBuffer customer = new StringBuffer();
			customer.append("SELECT c.residentAddress.township.name, COUNT(DISTINCT c.id) FROM Customer c Where c.activePolicy > 0 GROUP BY c.residentAddress.township");
			Query cQuery = em.createQuery(customer.toString());
			List<Object[]> objList = cQuery.getResultList();
			if (objList != null) {
				for (Object[] object : objList) {
					pieChartMap.put(object[0].toString(), Integer.parseInt(object[1].toString()));
				}
			}

			pieChartMap = sortByTopTenValue(pieChartMap);
		} catch (PersistenceException pe) {
			throw translate("Failed to find active customer by Location.", pe);
		}
		CustomerReport customerReport = new CustomerReport(pieChartMap);
		return customerReport;
	}

	public Map<String, Number> sortByTopTenValue(Map<String, Number> map) {
		List<Map.Entry<String, Number>> list = new LinkedList<Map.Entry<String, Number>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Number>>() {
			public int compare(Map.Entry<String, Number> o1, Map.Entry<String, Number> o2) {
				double l1 = o1.getValue().intValue();
				double l2 = o2.getValue().intValue();

				if (l1 < l2) {
					return 1;
				} else if (l1 > l2) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		Map<String, Number> result = new LinkedHashMap<String, Number>();
		for (Map.Entry<String, Number> entry : list) {
			if (result.size() < 10) {
				result.put(entry.getKey(), entry.getValue());
			}
		}

		return result;
	}

}
