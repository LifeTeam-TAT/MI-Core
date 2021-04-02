/*
 * package org.ace.insurance.system.common.stateCode.persistence;
 * 
 * import java.util.List;
 * 
 * import javax.persistence.NoResultException; import
 * javax.persistence.PersistenceException; import javax.persistence.Query;
 * 
 * import org.ace.insurance.system.common.stateCode.StateCode; import
 * org.ace.insurance.system.common.stateCode.persistence.interfaces.
 * IStateCodeDAO; import org.ace.java.component.SystemException; import
 * org.ace.java.component.persistence.BasicDAO; import
 * org.ace.java.component.persistence.exception.DAOException; import
 * org.springframework.stereotype.Repository; import
 * org.springframework.transaction.annotation.Propagation; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * @Repository("StateCodeDAO") public class StateCodeDAO extends BasicDAO
 * implements IStateCodeDAO {
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * insert(StateCode stateCode) throws DAOException { try { if
 * (!isAlreadyExistStateCode(stateCode)) { em.persist(stateCode); em.flush(); }
 * else { throw new SystemException(null, stateCode.getName() +
 * " is already exist."); } } catch (PersistenceException pe) { throw
 * translate("Failed to insert stateCode", pe); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * update(StateCode stateCode) throws DAOException { try { if
 * (!isAlreadyExistStateCode(stateCode)) { em.merge(stateCode); em.flush(); }
 * else { throw new SystemException(null, stateCode.getName() +
 * " is already exist."); } } catch (PersistenceException pe) { throw
 * translate("Failed to update stateCode", pe); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * delete(StateCode stateCode) throws DAOException { try { stateCode =
 * em.merge(stateCode); em.remove(stateCode); em.flush(); } catch
 * (PersistenceException pe) { throw translate("Failed to update stateCode",
 * pe); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public StateCode
 * findById(String id) throws DAOException { StateCode result = null; try {
 * result = em.find(StateCode.class, id); em.flush(); } catch
 * (PersistenceException pe) { throw translate("Failed to find StateCode", pe);
 * } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED, readOnly = true) public
 * StateCode findByCodeNo(String stateCodeNo, String townshipCodeNo) throws
 * DAOException { StateCode result = null; try { Query query = em.
 * createQuery("SELECT s FROM TownshipCode t JOIN t.stateCode s  WHERE s.codeNo = :codeNo AND t.townshipcodeno = :townshipCodeNo"
 * ); query.setParameter("codeNo", stateCodeNo);
 * query.setParameter("townshipCodeNo", townshipCodeNo); result = (StateCode)
 * query.getSingleResult(); em.flush(); } catch (NoResultException pe) { return
 * null; } catch (PersistenceException pe) { throw
 * translate("Failed to find StateCode", pe); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED, readOnly = true) public
 * List<StateCode> findAll() throws DAOException { List<StateCode> result =
 * null; try { Query q = em.createNamedQuery("StateCode.findAll"); result =
 * q.getResultList(); q.setMaxResults(50); em.flush(); } catch
 * (PersistenceException pe) { throw
 * translate("Failed to find all of StateCode", pe); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<StateCode>
 * findByCriteria(String criteria) throws DAOException { List<StateCode> result
 * = null; try { Query q =
 * em.createQuery("Select s from StateCode s where s.name Like '" + criteria +
 * "%'"); result = q.getResultList(); em.flush(); } catch (PersistenceException
 * pe) { throw translate("Failed to find by criteria of StateCode.", pe); }
 * return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public boolean
 * isAlreadyExistStateCode(StateCode stateCode) throws DAOException { boolean
 * exist = false; String stateCodeName = stateCode.getName().replaceAll("\\s+",
 * ""); try { StringBuffer buffer = new StringBuffer(); Query query = null;
 * 
 * buffer = new
 * StringBuffer("SELECT CASE WHEN (COUNT(c.name) > 0) THEN TRUE ELSE FALSE END FROM StateCode c "
 * ); buffer.append(" WHERE LOWER(FUNCTION('REPLACE',c.name,' ','')) = :name ");
 * buffer.append(stateCode.getId() != null ? "AND c.id != :id" : ""); query =
 * em.createQuery(buffer.toString()); if (stateCode.getId() != null)
 * query.setParameter("id", stateCode.getId()); query.setParameter("name",
 * stateCodeName.toLowerCase()); exist = (Boolean) query.getSingleResult();
 * em.flush();
 * 
 * return exist; } catch (PersistenceException pe) { throw
 * translate("Failed to find Existing Name ", pe); }
 * 
 * } }
 */