//
// package org.ace.insurance.versionref.service;
//
// import java.util.List;
//
// import javax.annotation.Resource;
//
// import org.ace.insurance.versionref.VersionRef;
// import org.ace.insurance.versionref.VersionRefDTO;
// import org.ace.insurance.versionref.persistence.interfaces.IVersionRefDAO;
// import org.ace.insurance.versionref.service.interfaces.IVersionRefService;
// import org.ace.java.component.SystemException;
// import org.ace.java.component.persistence.exception.DAOException;
// import org.ace.java.component.service.BaseService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Propagation;
// import org.springframework.transaction.annotation.Transactional;
//
//// TODO UNUSED
// @Service("VersionRefService")
// public class VersionRefService extends BaseService implements
// IVersionRefService {
//
// @Resource(name = "VersionRefDAO")
// private IVersionRefDAO versionRefDAO;
//
// /**
// * To remove entity from the system if it was previously updated (insert,
// * update or remove) in the system. So that the latest version ref record
// * will be retreived without entity duplication.
// *
// * @param entityId
// */
// private void handleExsitingEntity(String entityId) {
// List<VersionRef> ret = findVersionRefByEntityId(entityId);
// if (ret != null && ret.size() > 0) {
// for (VersionRef versionRef : ret) {
// versionRefDAO.delete(versionRef);
// }
// }
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public void addNewVersionRef(VersionRef versionRef) {
// try {
// handleExsitingEntity(versionRef.getEntityId());
// versionRef.setPrefix(getPrefix(VersionRef.class));
// // versionRef.setPrefix("VERPRE");
// versionRefDAO.insert(versionRef);
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to add a new Version
// Ref", e);
// }
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findAllVersionRef() {
// List<VersionRef> ret = null;
// try {
// ret = versionRefDAO.findAll();
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to find all Version Ref",
// e);
// }
// return ret;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public VersionRef findVersionRefById(String id) {
// VersionRef ret = null;
// try {
// ret = versionRefDAO.findById(id);
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to find Version Ref given
// id :" + id, e);
// }
// return ret;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRefDTO> findVersionRefIndexesByGroup(String groupName) {
// List<VersionRefDTO> ret = null;
// try {
// ret = versionRefDAO.findIndexesByGroup(groupName);
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to find Version Ref by
// given group :" + groupName, e);
// }
// return ret;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findVersionRefUpdateByGroupAndEntity(String
// entityName, String groupName, int versionNo) {
// List<VersionRef> ret = null;
// try {
// ret = versionRefDAO.findUpdatesByGroupAndEntity(entityName, groupName,
// versionNo);
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to find Version Ref by
// given group :" + groupName + " version No. :" + versionNo + " entity :" +
// entityName, e);
// }
// return ret;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findVersionRefUpdateByGroup(String groupName, int
// versionNo) {
// List<VersionRef> ret = null;
// try {
// ret = versionRefDAO.findUpdatesByGroup(groupName, versionNo);
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to find Version Ref by
// given group :" + groupName + " version No. :" + versionNo, e);
// }
// return ret;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public int findMaximumVersionNo(String groupName, String entityName) {
// int maxVersionNo = 0;
// try {
// maxVersionNo = versionRefDAO.findMaximumVersionNo(groupName, entityName);
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to find maximum version
// number of given group:" + groupName + " entity name:" + entityName, e);
// }
// return maxVersionNo;
// }
//
// @Transactional(propagation = Propagation.REQUIRED)
// public List<VersionRef> findVersionRefByEntityId(String entityId) {
// List<VersionRef> ret = null;
// try {
// ret = versionRefDAO.findUpdatesByEntityId(entityId);
// } catch (DAOException e) {
// throw new SystemException(e.getErrorCode(), "Faield to find Version Ref by
// given entity Id: " + entityId, e);
// }
// return ret;
// }
// }
