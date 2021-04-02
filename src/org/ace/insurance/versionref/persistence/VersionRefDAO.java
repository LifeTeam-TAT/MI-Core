// TODO UNUSED
// package org.ace.insurance.versionref.persistence;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import javax.persistence.PersistenceException;
// import javax.persistence.Query;
//
// import org.ace.insurance.common.TableName;
// import org.ace.insurance.versionref.VersionRef;
// import org.ace.insurance.versionref.VersionRefDTO;
// import org.ace.insurance.versionref.persistence.interfaces.IVersionRefDAO;
// import org.ace.java.component.persistence.BasicDAO;
// import org.ace.java.component.persistence.exception.DAOException;
// import org.springframework.stereotype.Repository;
// import org.springframework.transaction.annotation.Propagation;
// import org.springframework.transaction.annotation.Transactional;
//
// @Repository("VersionRefDAO")
// public class VersionRefDAO extends BasicDAO implements IVersionRefDAO {
//
// @Transactional(propagation = Propagation.REQUIRED)
// public void insert(VersionRef versionRef) throws DAOException {
// try {
// em.persist(versionRef);
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to insert Version Reference", pe);
// }
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findAll() throws DAOException {
// List<VersionRef> result = new ArrayList<VersionRef>();
// try {
// Query q = em.createNamedQuery("VersionRef.findAll");
// result = q.getResultList();
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find all of VersionRef", pe);
// }
// return result;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public VersionRef findById(String id) throws DAOException {
// VersionRef result = null;
// try {
// result = em.find(VersionRef.class, id);
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find VersionRef", pe);
// }
// return result;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRefDTO> findIndexesByGroup(String groupName) throws
// DAOException {
// List<VersionRefDTO> result = new ArrayList<VersionRefDTO>();
// List<Object[]> rawResult = null;
// String entityName = null;
// int versionNo = 0;
// try {
// Query query = em.createNamedQuery("VersionRef.findIndexesByGroup");
// query.setParameter("group", groupName);
// rawResult = query.getResultList();
// for (Object[] items : rawResult) {
// entityName = (String) items[0];
// versionNo = (Integer) items[1];
// result.add(new VersionRefDTO(null, entityName, versionNo, null, null));
// }
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find Version Reference indexes records of given
// group: ", pe);
// }
// return result;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findUpdatesByGroupAndEntity(String entityName, String
// groupName, int versionNo) throws DAOException {
// List<VersionRef> result = new ArrayList<VersionRef>();
// try {
// Query query =
// em.createNamedQuery("VersionRef.findUpdatesByGroupAndEntityName");
// query.setParameter("group", groupName);
// query.setParameter("entityName", entityName);
// query.setParameter("versionNo", versionNo);
// result = query.getResultList();
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find Updated Version Reference records of given
// group and entity with version No.: ", pe);
// }
// return result;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findUpdatesByGroup(String groupName, int versionNo)
// throws DAOException {
// List<VersionRef> result = new ArrayList<VersionRef>();
// try {
// Query query = em.createNamedQuery("VersionRef.findUpdatesByGroup");
// query.setParameter("group", groupName);
// query.setParameter("versionNo", versionNo);
// result = query.getResultList();
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find Updated Version Reference records of given
// group and version No.: ", pe);
// }
// return result;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findAllByGroup(String groupName) throws DAOException
// {
// List<VersionRef> result = new ArrayList<VersionRef>();
// try {
// Query query = em.createNamedQuery("VersionRef.findAllByGroup");
// query.setParameter("group", groupName);
// result = query.getResultList();
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find All Updated Version Reference records of
// given group: ", pe);
// }
// return result;
//
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public int findMaximumVersionNo(String groupName, String entityName) throws
// DAOException {
// int maxVersionNo = 0;
// try {
// Query query =
// em.createNamedQuery("VersionRef.findMaxVersionNoByGroupAndEntityName");
// query.setParameter("group", groupName);
// query.setParameter("entityName", entityName);
// Object result = query.getSingleResult();
// if (result != null)
// maxVersionNo = Integer.parseInt(result.toString());
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find maximum version number of given group: " +
// groupName + " entity: " + entityName, pe);
// }
// return maxVersionNo;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public void delete(VersionRef versionRef) throws DAOException {
// try {
// em.remove(em.merge(versionRef));
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to delete Version Reference", pe);
// }
//
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findUpdatesByEntityId(String entityId) throws
// DAOException {
// List<VersionRef> result = new ArrayList<VersionRef>();
// try {
// Query query = em.createNamedQuery("VersionRef.findAllByEntityId");
// query.setParameter("entityId", entityId);
// result = query.getResultList();
// em.flush();
// } catch (PersistenceException pe) {
// throw translate("Failed to find All Updated Version Reference records by
// given Entity Id: " + entityId, pe);
// }
// return result;
// }
//
// }
