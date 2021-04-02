package org.ace.java.web.common;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.SystemException;

@RequestScoped
@ManagedBean(name = "ExceptionResolver")
public class ExceptionResolver {
	/*
	 * ApprovalServiceException MotorPolicyServiceException
	 * PolicyVehicleInfoServiceException MotorProductServiceException
	 * MotorProposalServiceException MotorProposalWorkFlowServiceException
	 * ProposalHistoryServiceException ProposalVehicleInfoServiceException
	 * PaymentServiceException AgentCommissionDetaillReportServiceException
	 * MotorDailyIncomeReportServiceException MotorPolicyReportServiceException
	 * MotorProposalReportServiceException PremiumPaymentReportServiceException
	 * RoleServiceException AddOnServiceException AgentServiceException
	 * BankServiceException BranchServiceException CityServiceException
	 * CompanyServiceException CountryServiceException CustomerServiceException
	 * DenoServiceException KeyFactorServiceException OccupationServiceException
	 * PaymentTypeServiceException ProvinceServiceException
	 * RelationShipServiceException TownshipServiceException
	 * BuildingClassServiceException FloorServiceException
	 * MainCoverServiceException RoofServiceException WallServiceException
	 * ManufactureServiceException MotorTypeServiceException
	 * TypeOfBodyServiceException VehicleInfoServiceException
	 * UserServiceException
	 */
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected Application getApplication() {
		return getFacesContext().getApplication();
	}

	public ResourceBundle getBundle() {
		return ResourceBundle.getBundle(getApplication().getMessageBundle(), getFacesContext().getViewRoot().getLocale());
	}

	private String getMessage(String id, Object... params) {
		String text = null;
		try {
			text = getBundle().getString(id);
		} catch (MissingResourceException e) {
			text = "!! key " + id + " not found !!";
		}
		if (params != null) {
			MessageFormat mf = new MessageFormat(text);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}

	public FacesMessage resolveExcption(SystemException systemException) {
		String errorCode = systemException.getErrorCode();
		if (ErrorCode.NO_PREMIUM_RATE.equals(errorCode)) {
			String message = "There is no premium rate with [";
			Collection<KeyFactor> keyfactorList = (Collection<KeyFactor>) systemException.getResponse();
			for (KeyFactor kf : keyfactorList) {
				message = kf.getValue() + "";
			}
			message = message + "]";
			return new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
		}
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage(systemException.getErrorCode()), "");
	}
}
