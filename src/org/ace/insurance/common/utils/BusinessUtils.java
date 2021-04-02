package org.ace.insurance.common.utils;

import java.util.Arrays;
import java.util.List;

import org.ace.insurance.common.PolicyReferenceType;

public class BusinessUtils {

	public static List<PolicyReferenceType> getLifePolicyReferenceTypeList() {
		return Arrays.asList(PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY, PolicyReferenceType.LIFE_BILL_COLLECTION, PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY,
				PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION);
	}

	public static boolean isLifePolicyReferenceType(PolicyReferenceType policyReferenceType) {
		if (getLifePolicyReferenceTypeList().contains(policyReferenceType)) {
			return true;
		}
		return false;
	}

	public static double getTermPremium(double amount, int month) {
		return month > 0 ? amount * month / 12 : amount;
	}
}
