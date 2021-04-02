package org.ace.insurance.web.manage.product;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductBaseType;
import org.ace.insurance.product.ProductValidateData;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ProductDataValidator")
public class ProductDataValidator implements DTOValidator<ProductValidateData> {

	@Override
	public ValidationResult validate(ProductValidateData data) {
		ValidationResult result = new ValidationResult();
		Product product = data.getProduct();
		/* Term validate */
		if (data.getPeriod() != null) {
			int minDays = DateUtils.getNumberOfDaysByPeriod(product.getMinTerm(), product.getMinTermType());
			int maxDays = DateUtils.getNumberOfDaysByPeriod(product.getMaxTerm(), product.getMaxTermType());
			int noOfDays = DateUtils.getNumberOfDaysByPeriod(data.getPeriod(), data.getPeriodType());
			if (noOfDays < minDays) {
				result.addErrorMessage(":peirod", MessageId.MIN_PERIOD_MSG, product.getMinTerm(), product.getMinTermType());
			} else if (noOfDays > maxDays) {
				result.addErrorMessage(":peirod", MessageId.MAX_PERIOD_MSG, product.getMaxTerm(), product.getMaxTermType());
			}
		}
		/* SI validate */
		if (data.getSumInsured() != null && ProductBaseType.SI.equals(product.getProductBaseType())) {
			if (data.getSumInsured() < product.getMinValue()) {
				result.addErrorMessage(":sumInsured", MessageId.MIN_SI_MSG, product.getMinValue());
			} else if (data.getSumInsured() > product.getMaxValue()) {
				result.addErrorMessage(":sumInsured", MessageId.MAX_SI_MSG, product.getMaxValue());
			}
		}
		/* Unit validate */
		if (data.getUnit() != null && ProductBaseType.UNIT.equals(product.getProductBaseType())) {
			if (data.getUnit() < product.getMinValue()) {
				result.addErrorMessage(":unit", MessageId.MIN_UNIT_MSG, product.getMinValue());
			} else if (data.getUnit() > product.getMaxValue()) {
				result.addErrorMessage(":unit", MessageId.MAX_UNIT_MSG, product.getMaxValue());
			}
		}
		/* Age validate */
		if (data.getAge() != null) {
			if (data.getAge() < product.getMinAge()) {
				result.addErrorMessage(":dateOfBirth", MessageId.MEDICAL_INSURED_PERSON_MINAGE, product.getMinAge());
			} else if (data.getAge() > product.getMaxAge()) {
				result.addErrorMessage(":dateOfBirth", MessageId.MEDICAL_INSURED_PERSON_MAXAGE, product.getMaxAge());
			}
		}

		return result;
	}

}
