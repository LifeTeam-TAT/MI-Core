package org.ace.insurance.proxy.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.BC001;
import org.ace.insurance.proxy.AGT001;
import org.ace.insurance.proxy.LCB001;
import org.ace.insurance.proxy.LCLD001;
import org.ace.insurance.proxy.LCP001;
import org.ace.insurance.proxy.LIF001;
import org.ace.insurance.proxy.LPP001;
import org.ace.insurance.proxy.LSP001;
import org.ace.insurance.proxy.MED001;
import org.ace.insurance.proxy.MEDCLM002;
import org.ace.insurance.proxy.MEDFEES001;
import org.ace.insurance.proxy.SPMA001;
import org.ace.insurance.proxy.WorkflowCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProxyDAO {

	public List<LIF001> find_LIF001(WorkflowCriteria wfc) throws DAOException;

	public List<LCLD001> find_LCLD001(WorkflowCriteria wfc) throws DAOException;

	public List<LCB001> find_LCB001(WorkflowCriteria wfc) throws DAOException;

	public List<AGT001> find_AGT001(WorkflowCriteria wfc) throws DAOException;

	public List<MEDCLM002> find_MEDCLM002(WorkflowCriteria wfc) throws DAOException;

	public List<LSP001> find_LSP001(WorkflowCriteria wfc) throws DAOException;

	public List<LPP001> find_LPP001(WorkflowCriteria wfc) throws DAOException;

	public List<MED001> find_MED001(WorkflowCriteria wfc) throws DAOException;
	

	public List<MED001> find_MED002(WorkflowCriteria wfc) throws DAOException;

	public List<BC001> find_BC001(WorkflowCriteria wfc) throws DAOException;

	public List<BC001> find_Health_BC001(WorkflowCriteria wfc) throws DAOException;

	public List<SPMA001> findSoprtManAbroad_SPMA001(WorkflowCriteria wfc) throws DAOException;

	public List<LCP001> find_LCP001(WorkflowCriteria wfc) throws DAOException;

	public List<MEDFEES001> find_MEDFEES001(WorkflowCriteria wfc) throws DAOException;
}
