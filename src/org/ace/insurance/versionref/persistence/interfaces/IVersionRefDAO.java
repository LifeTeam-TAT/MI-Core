// TODO UNUSED
// package org.ace.insurance.versionref.persistence.interfaces;
//
// import java.util.List;
//
// import org.ace.insurance.versionref.VersionRef;
// import org.ace.insurance.versionref.VersionRefDTO;
// import org.ace.java.component.persistence.exception.DAOException;
//
/// **
// *
// * @author sun@sky
// *
// */
// public interface IVersionRefDAO {
// public void insert(VersionRef versionRef) throws DAOException;
// public List<VersionRef> findAll() throws DAOException;
// public VersionRef findById(String id) throws DAOException;
// public void delete(VersionRef versionRef) throws DAOException;
// /**
// * To retrieve the set of index records with unique entity name and the latest
// version No.
// * that can be used to be referenced for later request of updated records from
// the system.
// *
// * @param groupName - the group name or business domain name under that entity
// names are corresponding to
// * @return - the list of {@link VersionRefDTO}
// * @throws DAOException - throws if any db operation problem occurs
// */
// public List<VersionRefDTO> findIndexesByGroup(String groupName) throws
// DAOException;
//
// /**
// * To retrieve all the version reference records related with the given group
// name (or business domain name)
// *
// * @param groupName - the group name or business domain name under that entity
// names are corresponding to
// * @return - the list of {@link VersionRef} found in the system
// * @throws DAOException - throws if any db operation problem occurs
// */
// public List<VersionRef> findAllByGroup(String groupName) throws DAOException;
//
// /**
// * To retrieve the set of updated records related to given entity name, and
// group name whereas version values are
// * greater than the given version No.. It means the records are update than
// the last request time.
// *
// * @param groupName - the group name or business domain name under that entity
// names are corresponding to
// * @param entityName - the name of entity
// * @param versionNo - the reference version number used to be compared
// * @return - the list of {@link VersionRef}
// * @throws DAOException - throws if any db operation problem occurs
// */
// public List<VersionRef> findUpdatesByGroupAndEntity(String entityName, String
// groupName, int versionNo) throws DAOException;
// /**
// * To retrieve the set of updated records related to given group name whereas
// version values are
// * greater than the given version number. It means the records are update than
// the last request time.
// *
// * @param groupName - the group name or business domain name under that entity
// names are corresponding to
// * @param entityName - the name of entity
// * @param versionNo - the reference version number used to be compared
// *
// * @return - the list of {@link VersionRef}
// * @throws DAOException - throws if any db operation problem occurs
// */
// public List<VersionRef> findUpdatesByGroup(String groupName, int versionNo)
// throws DAOException;
//
// /**
// * To get the maxumum version number of the given group name and entity name.
// * If the group name or entity name does not exists, 0 value will be returned.
// *
// * @param groupName
// * @param entityName
// * @return maximum version number
// * @throws DAOException
// */
// public int findMaximumVersionNo(String groupName, String entityName) throws
// DAOException;
//
// public List<VersionRef> findUpdatesByEntityId(String entityId) throws
// DAOException;
// }
