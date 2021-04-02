package org.ace.insurance.web.manage.surveyquestion.validation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.product.ProductProcessDTO;
import org.ace.insurance.web.manage.surveyquestion.ProductProcessQuestionLinkDTO;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ProductProcessValidator")
public class ProductProcessValidator implements DTOValidator<ProductProcessDTO> {

	@Override
	public ValidationResult validate(ProductProcessDTO productProcessDto) {
		ValidationResult result = new ValidationResult();
		String formID = "configurationForm";
		int maxPriorityNo = productProcessDto.getProductProcessQuestionLinkList().size();

		if (productProcessDto.getProduct() == null) {
			result.addErrorMessage(formID + ":productPanelGroup", UIInput.REQUIRED_MESSAGE_ID);
		}

		if (productProcessDto.getProcess() == null) {
			result.addErrorMessage(formID + ":processPanelGroup", UIInput.REQUIRED_MESSAGE_ID);
		}

		if (productProcessDto.getProductProcessQuestionLinkList() == null || productProcessDto.getProductProcessQuestionLinkList().size() < 1) {
			result.addErrorMessage(formID + ":questionList", MessageId.QUESTION);
		}

		for (ProductProcessQuestionLinkDTO ppqlDto : productProcessDto.getProductProcessQuestionLinkList()) {
			if (ppqlDto.getPriority() < 1) {
				result.addErrorMessage(formID + ":questionList", MessageId.MINIMUM_PRIORITY_NUM, 1);
				break;
			}

			if (ppqlDto.getPriority() > maxPriorityNo) {
				result.addErrorMessage(formID + ":questionList", MessageId.MAXIMUM_PRIORITY_NUM, maxPriorityNo);
				break;
			}

			if (duplicatePriority(ppqlDto, productProcessDto)) {
				result.addErrorMessage(formID + ":questionList", MessageId.DUPLICATE_PRIORITY_NUM);
				break;
			}
		}

		return result;
	}

	private boolean duplicatePriority(ProductProcessQuestionLinkDTO processQueLink, ProductProcessDTO productProcessDto) {
		boolean duplicate = false;
		for (ProductProcessQuestionLinkDTO ppql : productProcessDto.getProductProcessQuestionLinkList()) {
			if (!processQueLink.getId().equals(ppql.getId())) {
				if (processQueLink.getPriority() == ppql.getPriority()) {
					duplicate = true;
					break;
				}
			}
		}

		return duplicate;
	}

}
