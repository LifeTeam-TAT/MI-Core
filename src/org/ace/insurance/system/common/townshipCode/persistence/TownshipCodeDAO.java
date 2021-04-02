/*
 * package org.ace.insurance.system.common.townshipCode.persistence;
 * 
 * import java.util.List;
 * 
 * import javax.persistence.NoResultException; import
 * javax.persistence.PersistenceException; import javax.persistence.Query;
 * 
 * import org.ace.insurance.system.common.townshipCode.TownshipCode; import
 * org.ace.insurance.system.common.townshipCode.persistence.interfaces.
 * ITownshipCodeDAO; import org.ace.java.component.SystemException; import
 * org.ace.java.component.persistence.BasicDAO; import
 * org.ace.java.component.persistence.exception.DAOException; import
 * org.springframework.stereotype.Repository; import
 * org.springframework.transaction.annotation.Propagation; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * @Repository("TownshipCodeDAO") public class TownshipCodeDAO extends BasicDAO
 * implements ITownshipCodeDAO {
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * insert(TownshipCode townshipCode) throws DAOException { try { if
 * (!isAlreadyExistTownshipCode(townshipCode)) { em.persist(townshipCode);
 * em.flush(); } else { throw new SystemException(null, townshipCode.getName() +
 * " is already exist."); } } catch (PersistenceException pe) { throw
 * translate("Failed to insert townshipCode", pe); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * update(TownshipCode townshipCode) throws DAOException { try { if
 * (!isAlreadyExistTownshipCode(townshipCode)) { em.merge(townshipCode);
 * em.flush(); } else { throw new SystemException(null, townshipCode.getName() +
 * " is already exist."); } } catch (PersistenceException pe) { throw
 * translate("Failed to update townshipCode", pe); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * delete(TownshipCode townshipCode) throws DAOException { try { townshipCode =
 * em.merge(townshipCode); em.remove(townshipCode); em.flush(); } catch
 * (PersistenceException pe) { throw translate("Failed to update townshipCode",
 * pe); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public TownshipCode
 * findById(String id) throws DAOException { TownshipCode result = null; try {
 * result = em.find(TownshipCode.class, id); em.flush(); } catch
 * (PersistenceException pe) { throw translate("Failed to find TownshipCode",
 * pe); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED, readOnly = true) public
 * TownshipCode findByCodeNo(String tspCodeNo, String stateCodeNo) throws
 * DAOException { TownshipCode result = null;
 * 
 * try { Query query = em.
 * createQuery("SELECT t FROM TownshipCode t JOIN t.stateCode s WHERE t.townshipcodeno = :codeNo AND s.codeNo = :stateCodeNo"
 * ); query.setParameter("codeNo", tspCodeNo); query.setParameter("stateCodeNo",
 * stateCodeNo); result = (TownshipCode) query.getSingleResult(); em.flush(); }
 * catch (NoResultException pe) { return null; } catch (PersistenceException pe)
 * { throw translate("Failed to find StateCode", pe); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findAll() throws DAOException { List<TownshipCode> result = null; try { Query
 * q = em.createNamedQuery("TownshipCode.findAll"); result = q.getResultList();
 * q.setMaxResults(50); em.flush(); } catch (PersistenceException pe) { throw
 * translate("Failed to find all of TownshipCode", pe); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findByCriteria(String criteria) throws DAOException { List<TownshipCode>
 * result = null; try { // Query q =
 * em.createNamedQuery("Township.findByCriteria"); Query q =
 * em.createQuery("Select t from TownshipCode t where t.name Like '" + criteria
 * + "%'"); // q.setParameter("criteriaValue", "%" + criteria + "%"); result =
 * q.getResultList(); em.flush(); } catch (PersistenceException pe) { throw
 * translate("Failed to find by criteria of TownshipCode.", pe); } return
 * result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findByStateCode(String stateCodeId) throws DAOException { List<TownshipCode>
 * result = null; try { Query q = em.
 * createQuery("Select t from TownshipCode t where t.stateCode.id =:stateCodeId ORDER BY t.name"
 * ); q.setParameter("stateCodeId", stateCodeId); result = q.getResultList();
 * em.flush(); } catch (PersistenceException pe) { throw
 * translate("Failed to find by criteria of TownshipCode.", pe); } return
 * result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findByStateCodeNo(String stateCode) { List<TownshipCode> result = null; try {
 * Query q = em.
 * createQuery("Select t from TownshipCode t where t.stateCode.codeNo =:stateCode ORDER BY t.name"
 * ); q.setParameter("stateCode", stateCode); result = q.getResultList();
 * em.flush(); } catch (PersistenceException pe) { throw
 * translate("Failed to find by criteria of TownshipCode.", pe); } return
 * result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public boolean
 * isAlreadyExistTownshipCode(TownshipCode townshipCode) throws DAOException {
 * boolean exist = false; String townshipCodeName =
 * townshipCode.getName().replaceAll("\\s+", ""); try { StringBuffer buffer =
 * new StringBuffer(); Query query = null;
 * 
 * buffer = new
 * StringBuffer("SELECT CASE WHEN (COUNT(c.name) > 0) THEN TRUE ELSE FALSE END FROM TownshipCode c "
 * ); buffer.append(" WHERE LOWER(FUNCTION('REPLACE',c.name,' ','')) = :name ");
 * buffer.append(townshipCode.getId() != null ? "AND c.id != :id" : ""); query =
 * em.createQuery(buffer.toString()); if (townshipCode.getId() != null)
 * query.setParameter("id", townshipCode.getId()); query.setParameter("name",
 * townshipCodeName.toLowerCase()); exist = (Boolean) query.getSingleResult();
 * em.flush();
 * 
 * return exist; } catch (PersistenceException pe) { throw
 * translate("Failed to find Existing Name ", pe); }
 * 
 * } }
 */