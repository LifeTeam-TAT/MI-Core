/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.township.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.township.TSP001;
import org.ace.insurance.system.common.township.TSP002;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.persistence.interfaces.ITownshipDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("TownshipDAO")
@SuppressWarnings("unchecked")
public class TownshipDAO extends BasicDAO implements ITownshipDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Township township) throws DAOException {
		try {
			em.persist(township);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Township", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Township township) throws DAOException {
		try {
			em.merge(township);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Township", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Township township) throws DAOException {
		try {
			township = em.merge(township);
			em.remove(township);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Township", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Township findById(String id) throws DAOException {
		Township result = null;
		try {
			result = em.find(Township.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Township", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Township> findByProvince(Province province) throws DAOException {
		List<Township> result = null;
		try {
			Query query = em.createNamedQuery("Township.findByProvince");
			query.setParameter("provinceId", province.getId());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Township", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Township> findAll() throws DAOException {
		List<Township> result = null;
		try {
			Query q = em.createNamedQuery("Township.findAll");
			result = q.getResultList();
			q.setMaxResults(50);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Township", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TSP001> findAll_TSP001() throws DAOException {
		List<TSP001> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + TSP001.class.getName());
			buffer.append("(t.id, t.name, t.district.name, t.district.province.name) FROM Township t ORDER BY t.district.name ASC");
			Query q = em.createQuery(buffer.toString());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all TSP001.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TSP002> findAll_TSP002() throws DAOException {
		List<TSP002> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + TSP002.class.getName());
			buffer.append("(t.id, t.name, t.code, t.shortName, t.district.name, t.description) FROM Township t ORDER BY t.code ASC");
			Query q = em.createQuery(buffer.toString());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all TSP001.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findNameById(String id) throws DAOException {
		String name = null;
		try {
			Query query = em.createQuery("SELECT t.name FROM Township t where t.id = :id");
			query.setParameter("id", id);
			name = (String) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Township", pe);
		}
		return name;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findTspShortNameByProvinceNo(String provinceNo) throws DAOException {
		List<String> result = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append("SELECT distinct t.shortName ");
			str.append("FROM Township t inner join t.district as d inner join d.province as p ");
			str.append("WHERE p.provinceNo = :provinceNo AND t.shortName !='' ");
			str.append("ORDER BY t.shortName");
			Query q = em.createQuery(str.toString());
			q.setParameter("provinceNo", provinceNo);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of ProvinceNo.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Township findTownshipByName(String name) {
		Township result = null;
		List<Township> townshipList = null;
		try {
			Query query = em.createQuery("SELECT t FROM Township t where t.name = :name");
			query.setParameter("name", name);
			townshipList = query.getResultList();
			if (townshipList.isEmpty()) {
				result = null;
			} else {
				result = townshipList.get(0);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Township", pe);
		}
		return result;
	}
}
