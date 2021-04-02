package org.ace.insurance.web.manage.medical.proposal.validation;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.common.ValidationUtil;
import org.ace.insurance.web.manage.medical.proposal.CustomerDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "CustomerValidator")
public class CustomerValidator implements DTOValidator<CustomerDTO> {
	@Override
	public ValidationResult validate(CustomerDTO customerDTO) {
		ValidationResult result = new ValidationResult();
		String formID = "medicalProposalEntryForm";
		if (ValidationUtil.isStringEmpty(customerDTO.getInitialId())) {
			result.addErrorMessage(formID + ":customerRegInit", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (ValidationUtil.isStringEmpty(customerDTO.getName().getFirstName())) {
			result.addErrorMessage(formID + ":customerRegName", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (!customerDTO.getIdType().equals(IdType.STILL_APPLYING) && ValidationUtil.isStringEmpty(customerDTO.getIdNo())) {
			result.addErrorMessage(formID + ":customerRegidNo", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (customerDTO.getDateOfBirth() == null) {
			result.addErrorMessage(formID + ":customerRegdob", UIInput.REQUIRED_MESSAGE_ID);
		} else {
			String message = null;
			message = validateMinMaxAge(customerDTO.getDateOfBirth(), 18, 75);
			if (null != message) {
				result.addErrorMessage(formID + ":customerRegdob", MessageId.CUSTOMER_AGE_OVER, message);
			}
		}

		if (ValidationUtil.isStringEmpty(customerDTO.getResidentAddress().getResidentAddress())) {
			result.addErrorMessage(formID + ":customerRegresidentAdd", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (customerDTO.getResidentAddress().getTownship() == null || ValidationUtil.isStringEmpty(customerDTO.getResidentAddress().getTownship().getName())) {
			result.addErrorMessage(formID + ":customerRegresidentTownId", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (customerDTO.getGender() == null) {
			result.addErrorMessage(formID + ":customergender", UIInput.REQUIRED_MESSAGE_ID);
		}
	
		if (customerDTO.getContentInfo().getMobile().isEmpty()) {
			result.addErrorMessage(formID + ":customerRegmobile", UIInput.REQUIRED_MESSAGE_ID);
		} else if (!customerDTO.getContentInfo().getMobile().matches("[0-9-+, ]+")) {
			result.addErrorMessage(formID + ":customerRegmobile", MessageId.INVALID_NUMBER);
		}
		if (!customerDTO.getContentInfo().getPhone().isEmpty()) {
			if (!customerDTO.getContentInfo().getPhone().matches("[0-9-+, ]+"))
				result.addErrorMessage(formID + ":customerRegphone", MessageId.INVALID_NUMBER);
		}
		
		if (!customerDTO.getIdType().equals(IdType.STILL_APPLYING)) {
			String regIdNoMessageId = ":customerRegidNo";
			if (customerDTO.getIdType().equals(IdType.NRCNO)) {
				if (customerDTO.getIdNo() == null) {
					result.addErrorMessage(formID + regIdNoMessageId, UIInput.REQUIRED_MESSAGE_ID);
				} else if ((customerDTO.getStateCode() == null || customerDTO.getTownshipCode() == null)) {
					result.addErrorMessage(formID + regIdNoMessageId, MessageId.NRC_STATE_AND_TOWNSHIP_ERROR);
				} else if (customerDTO.getIdNo().length() != 6 && customerDTO.getStateCode() != null && customerDTO.getTownshipCode() != null) {
					result.addErrorMessage(formID + regIdNoMessageId, MessageId.NRC_FORMAT_INCORRECT);
				}
			} else {
				customerDTO.setStateCode(null);
				customerDTO.setTownshipCode(null);
				customerDTO.setIdConditionType(null);
				if (customerDTO.getIdNo() == null) {
					result.addErrorMessage(formID + regIdNoMessageId, UIInput.REQUIRED_MESSAGE_ID);
				}
			}
		} else {
			customerDTO.setStateCode(null);
			customerDTO.setTownshipCode(null);
			customerDTO.setIdConditionType(null);
			customerDTO.setIdNo("");
		}

		if (customerDTO.getContentInfo().getMobile() == null || ValidationUtil.isStringEmpty(customerDTO.getContentInfo().getMobile())) {
			if (customerDTO.getContentInfo().getPhone() == null || ValidationUtil.isStringEmpty(customerDTO.getContentInfo().getPhone())) {
				result.addErrorMessage(formID + ":customerRegphone", MessageId.FILL_PHONE_NUMBER);
			}
		}
		return result;
	}

	private String validateMinMaxAge(Date dateOfBirth, int minAge, int maxAge) {
		if (Utils.getAgeForNextYear(dateOfBirth) < minAge) {
			return " be over " + minAge + " year!";
		} else if (Utils.getAgeForNextYear(dateOfBirth) > maxAge) {
			return " not be over " + maxAge + " year!";
		} else {
			return null;
		}

	}

	public static void main(String args[]) {
		String phNo = "1-2345";
		if (phNo.matches("[-0-9]+")) {
			System.out.println("True");
		} else {
			System.out.println("False");
		}
	}
}
