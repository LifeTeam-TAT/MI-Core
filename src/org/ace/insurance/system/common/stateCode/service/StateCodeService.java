/*
 * package org.ace.insurance.system.common.stateCode.service;
 * 
 * import java.util.Collections; import java.util.Comparator; import
 * java.util.List;
 * 
 * import javax.annotation.Resource;
 * 
 * import org.ace.insurance.system.common.stateCode.StateCode; import
 * org.ace.insurance.system.common.stateCode.persistence.interfaces.
 * IStateCodeDAO; import
 * org.ace.insurance.system.common.stateCode.service.interfaces.
 * IStateCodeService; import org.ace.java.component.SystemException; import
 * org.ace.java.component.persistence.exception.DAOException; import
 * org.ace.java.component.service.BaseService; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Propagation; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * @Service(value = "StateCodeService") public class StateCodeService extends
 * BaseService implements IStateCodeService {
 * 
 * @Resource(name = "StateCodeDAO") private IStateCodeDAO stateCodeDAO;
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * addNewStateCode(StateCode stateCode) { try { stateCodeDAO.insert(stateCode);
 * } catch (DAOException e) { throw new SystemException(e.getErrorCode(),
 * "Faield to add a new stateCode", e); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * updateStateCode(StateCode stateCode) { try { stateCodeDAO.update(stateCode);
 * } catch (DAOException e) { throw new SystemException(e.getErrorCode(),
 * "Faield to update a stateCode", e); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public void
 * deleteStateCode(StateCode stateCode) { try { stateCodeDAO.delete(stateCode);
 * } catch (DAOException e) { throw new SystemException(e.getErrorCode(),
 * "Faield to delete a stateCode", e); }
 * 
 * }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public StateCode
 * findStateCodeById(String id) { StateCode result = null; try { result =
 * stateCodeDAO.findById(id); } catch (DAOException e) { throw new
 * SystemException(e.getErrorCode(), "Faield to find a stateCode (ID : " + id +
 * ")", e); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED, readOnly = true) public
 * StateCode findStateCodeByCodeNo(String stateCodeNo, String townshipCodeNo) {
 * StateCode result = null; try { result =
 * stateCodeDAO.findByCodeNo(stateCodeNo, townshipCodeNo); } catch (DAOException
 * e) { throw new SystemException(e.getErrorCode(),
 * "Faield to find a stateCode (CodeNo : " + stateCodeNo + ")", e); } return
 * result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED, readOnly = true) public
 * List<StateCode> findAllStateCode() { List<StateCode> result = null; try {
 * result = stateCodeDAO.findAll(); Collections.sort(result, new
 * Comparator<StateCode>() { public int compare(StateCode p1, StateCode p2) {
 * return Integer.parseInt(p1.getCodeNo()) - Integer.parseInt(p2.getCodeNo()); }
 * }); } catch (DAOException e) { throw new SystemException(e.getErrorCode(),
 * "Faield to find all of stateCode)", e); } return result; }
 * 
 * @Transactional(propagation = Propagation.REQUIRED) public List<StateCode>
 * findByCriteria(String criteria) { List<StateCode> result = null; try { result
 * = stateCodeDAO.findByCriteria(criteria); } catch (DAOException e) { throw new
 * SystemException(e.getErrorCode(), "Faield to find StateCode by criteria " +
 * criteria, e); } return result; }
 * 
 * }
 */