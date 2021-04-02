package org.ace.insurance.proxy.service;

import java.util.List;

import javax.annotation.Resource;

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
import org.ace.insurance.proxy.persistence.interfaces.IProxyDAO;
import org.ace.insurance.proxy.service.interfaces.IProxyService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProxyService")
public class ProxyService implements IProxyService {
	@Resource(name = "ProxyDAO")
	private IProxyDAO proxyDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LIF001> find_LIF001(WorkflowCriteria wfc) {
		List<LIF001> result = null;
		try {
			result = proxyDAO.find_LIF001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LIF001 By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LCLD001> find_LCLD001(WorkflowCriteria wfc) {
		List<LCLD001> result = null;
		try {
			result = proxyDAO.find_LCLD001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LCLD001 for DashBoar By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LCB001> find_LCB001(WorkflowCriteria wfc) {
		List<LCB001> result = null;
		try {
			result = proxyDAO.find_LCB001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LCB001 for DashBoar By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AGT001> find_AGT001(WorkflowCriteria wfc) {
		List<AGT001> result = null;
		try {
			result = proxyDAO.find_AGT001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentCommissionDTO for DashBoar By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MEDCLM002> find_MEDCLM002(WorkflowCriteria wfc) {
		List<MEDCLM002> result = null;
		try {
			result = proxyDAO.find_MEDCLM002(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find MED002 By WorkflowCriteria", e);
		}
		return result;
	}

	/* Life Surrender Proposal */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LSP001> find_LSP001(WorkflowCriteria wfc) {
		List<LSP001> result = null;
		try {
			result = proxyDAO.find_LSP001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find LSP001 By WorkflowCriteria", e);
		}
		return result;

	}

	/* Life PaidUp */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LPP001> find_LPP001(WorkflowCriteria wfc) {
		List<LPP001> result = null;
		try {
			result = proxyDAO.find_LPP001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find LPP001 By WorkflowCriteria", e);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BC001> find_BC001(WorkflowCriteria wfc) {
		List<BC001> result = null;
		try {
			result = proxyDAO.find_BC001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find BC001 By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BC001> find_Health_BC001(WorkflowCriteria wfc) {
		List<BC001> result = null;
		try {
			result = proxyDAO.find_Health_BC001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find BC001 By WorkflowCriteria", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MED001> find_MED001(WorkflowCriteria wfc) {
		List<MED001> result = null;
		try {
			result = proxyDAO.find_MED001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find TRA001 By WorkflowCriteria", e);
		}
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MED001> find_MED002(WorkflowCriteria wfc) {
		List<MED001> result = null;
		try {
			result = proxyDAO.find_MED002(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find TRA001 By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPMA001> findSoprtManAbroad_SPMA001(WorkflowCriteria wfc) {
		List<SPMA001> result = null;
		try {
			result = proxyDAO.findSoprtManAbroad_SPMA001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find SPMA001 By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LCP001> find_LCP001(WorkflowCriteria wfc) {
		List<LCP001> result = null;
		try {
			result = proxyDAO.find_LCP001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find groupFarmer for dashboard by WorkflowCriteria", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MEDFEES001> find_MEDFEES001(WorkflowCriteria wfc) {
		List<MEDFEES001> result = null;
		try {
			result = proxyDAO.find_MEDFEES001(wfc);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find groupFarmer for dashboard by WorkflowCriteria", e);
		}
		return result;
	}
}
