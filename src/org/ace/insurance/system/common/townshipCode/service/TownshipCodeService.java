/*
 * package org.ace.insurance.system.common.townshipCode.service;
 * 
 * import java.util.List;
 * 
 * import javax.annotation.Resource;
 * 
 * import org.ace.insurance.system.common.stateCode.StateCode; import
 * org.ace.insurance.system.common.townshipCode.TownshipCode; import
 * org.ace.insurance.system.common.townshipCode.persistence.interfaces.
 * ITownshipCodeDAO; import
 * org.ace.insurance.system.common.townshipCode.service.interfaces.
 * ITownshipCodeService; import org.ace.java.component.SystemException; import
 * org.ace.java.component.persistence.exception.DAOException; import
 * org.ace.java.component.service.BaseService; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Propagation; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * @Service(value = "TownshipCodeService") public class TownshipCodeService
 * extends BaseService implements ITownshipCodeService {
 * 
 * @Resource(name = "TownshipCodeDAO") private ITownshipCodeDAO townshipCodeDAO;
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * addNewTownshipCode(TownshipCode townshipCode) { try {
 * townshipCodeDAO.insert(townshipCode); } catch (DAOException e) { throw new
 * SystemException(e.getErrorCode(), "Faield to add a new TownshipCode", e); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * updateTownshipCode(TownshipCode townshipCode) { try {
 * townshipCodeDAO.update(townshipCode); } catch (DAOException e) { throw new
 * SystemException(e.getErrorCode(), "Faield to update a TownshipCode", e); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * deleteTownshipCode(TownshipCode townshipCode) { try {
 * townshipCodeDAO.delete(townshipCode); } catch (DAOException e) { throw new
 * SystemException(e.getErrorCode(), "Faield to delete a TownshipCode", e); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public TownshipCode
 * findTownshipCodeById(String id) { TownshipCode result = null; try { result =
 * townshipCodeDAO.findById(id); } catch (DAOException e) { throw new
 * SystemException(e.getErrorCode(), "Faield to find a TownshipCode (ID : " + id
 * + ")", e); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED, readOnly = true) public
 * TownshipCode findTownshipCodeByCodeNo(String tspCodeNo, String stateCodeNo) {
 * TownshipCode result = null; try { result =
 * townshipCodeDAO.findByCodeNo(tspCodeNo, stateCodeNo); } catch (DAOException
 * e) { throw new SystemException(e.getErrorCode(),
 * "Faield to find a TownshipCode (CodeNo : " + tspCodeNo + ")", e); } return
 * result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findAllTownshipCode() { List<TownshipCode> result = null; try { result =
 * townshipCodeDAO.findAll(); } catch (DAOException e) { throw new
 * SystemException(e.getErrorCode(), "Faield to find all of TownshipCode)", e);
 * } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findByCriteria(String criteria) { List<TownshipCode> result = null; try {
 * result = townshipCodeDAO.findByCriteria(criteria); } catch (DAOException e) {
 * throw new SystemException(e.getErrorCode(),
 * "Faield to find TownshipCode by criteria " + criteria, e); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findByStateCode(StateCode stateCode) { List<TownshipCode> result = null; try
 * { result = townshipCodeDAO.findByStateCode(stateCode.getId()); } catch
 * (DAOException e) { throw new SystemException(e.getErrorCode(),
 * "Faield to find TownshipCode by criteria " + stateCode, e); } return result;
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<TownshipCode>
 * findByStateCodeNo(String stateCode) { List<TownshipCode> result = null; try {
 * result = townshipCodeDAO.findByStateCodeNo(stateCode); } catch (DAOException
 * e) { throw new SystemException(e.getErrorCode(),
 * "Faield to find TownshipCode by StateCodeNo " + stateCode, e); } return
 * result; }
 * 
 * }
 */