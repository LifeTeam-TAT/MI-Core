package org.ace.insurance.web.manage.medical.proposal.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.product.ProductValidateData;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ErrorMessage;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.common.ValidationUtil;
import org.ace.insurance.web.manage.medical.proposal.CustomerDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProGuardianDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuAddOnDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuBeneDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ProposalInsuredPersonValidator")
public class ProposalInsuredPersonValidator implements DTOValidator<MedProInsuDTO> {
	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{GuardianPersonValidator}")
	private DTOValidator<MedProGuardianDTO> guardianValidator;

	public void setGuardianValidator(DTOValidator<MedProGuardianDTO> guardianValidator) {
		this.guardianValidator = guardianValidator;
	}

	@ManagedProperty(value = "#{ProductDataValidator}")
	private DTOValidator<ProductValidateData> productDataValidator;

	public void setProductDataValidator(DTOValidator<ProductValidateData> productDataValidator) {
		this.productDataValidator = productDataValidator;
	}

	@Override
	public ValidationResult validate(MedProInsuDTO dto) {
		CustomerDTO customer = dto.getCustomer();
		ValidationResult result = new ValidationResult();
		String formID = "medicalProposalEntryForm";
		if (ValidationUtil.isStringEmpty(customer.getName().getFirstName())) {
			result.addErrorMessage(formID + ":name", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (!IdType.STILL_APPLYING.equals(customer.getIdType())) {
			if (IdType.NRCNO.equals(customer.getIdType())) {
				if (customer.getIdNo() == null || ValidationUtil.isStringEmpty(customer.getIdNo())) {
					result.addErrorMessage(formID + ":idNo", UIInput.REQUIRED_MESSAGE_ID);
				} else if ((customer.getStateCode() == null && customer.getTownshipCode() == null) || (customer.getStateCode() == null && customer.getTownshipCode() != null)
						|| (customer.getStateCode() != null && customer.getTownshipCode() == null)) {
					result.addErrorMessage(formID + ":idNo", MessageId.NRC_STATE_AND_TOWNSHIP_ERROR);
				} else if (customer.getIdNo().length() != 6 && customer.getStateCode() != null && customer.getTownshipCode() != null) {
					result.addErrorMessage(formID + ":idNo", MessageId.NRC_FORMAT_INCORRECT);
				}
			} else {
				customer.setIdConditionType(null);
				customer.setStateCode(null);
				customer.setTownshipCode(null);
				if (ValidationUtil.isStringEmpty(customer.getIdNo())) {
					result.addErrorMessage(formID + ":idNo", UIInput.REQUIRED_MESSAGE_ID);
				}
			}
		} else {
			customer.setIdConditionType(null);
			customer.setStateCode(null);
			customer.setTownshipCode(null);
			customer.setIdNo(null);
		}
		if (customer.getDateOfBirth() == null || ValidationUtil.isStringEmpty(customer.getDateOfBirth().toString())) {
			result.addErrorMessage(formID + ":dateOfBirth", UIInput.REQUIRED_MESSAGE_ID);
		}

		if (ValidationUtil.isStringEmpty(customer.getResidentAddress().getResidentAddress())) {
			result.addErrorMessage(formID + ":resident", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (customer.getResidentAddress().getTownship() == null || ValidationUtil.isStringEmpty(customer.getResidentAddress().getTownship().getName())) {
			result.addErrorMessage(formID + ":townShip", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (customer.getGender() == null) {
			result.addErrorMessage(formID + ":gender", UIInput.REQUIRED_MESSAGE_ID);
		}

		if (dto.getRelationship() == null) {
			result.addErrorMessage(formID + ":beneficiaryRelationShip", UIInput.REQUIRED_MESSAGE_ID);
		} else if (dto.getRelationship().getName().equalsIgnoreCase("---------")) {
			result.addErrorMessage(formID + ":beneficiaryRelationShip", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (dto.getProduct() == null) {
			result.addErrorMessage(formID + ":product", UIInput.REQUIRED_MESSAGE_ID);
		}
		if (dto.getInsuredPersonBeneficiariesList() == null || dto.getInsuredPersonBeneficiariesList().size() < 1) {
			result.addErrorMessage(formID + ":beneficiaryPersonTable", MessageId.REQUIRE_BENEFICIARY);
		} else {
			double percentage = 0.0;
			for (MedProInsuBeneDTO beneficiary : dto.getInsuredPersonBeneficiariesList()) {
				percentage += beneficiary.getPercentage();
			}
			if (percentage != 100) {
				result.addErrorMessage(formID + ":beneficiaryPersonTable", MessageId.BENEFICIARY_PERCENTAGE_MUST_BE_100);
			}
		}
		if (customer.getDateOfBirth() != null && Utils.getAgeForNextYear(customer.getDateOfBirth()) < 18) {
			if (dto.getGuardianDTO() == null) {
				result.addErrorMessage(formID + ":guradianPersonTable", MessageId.REQUIRE_GUARDIAN);
			}
		}
		// AddOn validate
		for (MedProInsuAddOnDTO addOn : dto.getInsuredPersonAddOnList()) {
			if (addOn.getUnit() > dto.getUnit()) {
				result.addErrorMessage(formID + ":addOnGrid", MessageId.MAX_UNIT_MSG, dto.getUnit());
			}
		}
		/* Product data validate */
		if (dto.getProduct() != null && customer.getDateOfBirth() != null) {
			ValidationResult r = productDataValidator
					.validate(new ProductValidateData(dto.getProduct(), null, null, null, dto.getUnit(), Utils.getAgeForNextYear(customer.getDateOfBirth())));
			if (!r.isVerified()) {
				for (ErrorMessage errMsg : r.getErrorMeesages()) {
					errMsg.concatFormId(formID);
					result.addErrorMessage(errMsg);
				}
			}
		}

		return result;
	}
}
