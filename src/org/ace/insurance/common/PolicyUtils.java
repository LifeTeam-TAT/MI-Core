package org.ace.insurance.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ace.insurance.common.interfaces.IPolicy;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.product.ProductGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The utility class having the generic behaviors facilitated for all policy
 * objects.
 * 
 * @author ACN
 * @since 1.0.0
 * @date 2013/05/13
 * 
 */
public class PolicyUtils {
	private static Logger logger = LogManager.getLogger(org.ace.insurance.common.PolicyUtils.class);

	/**
	 * This method filters given Policy instance list by checking policy's total
	 * insured amount against it's product group's maximum insured amount limit.
	 * 
	 * @param policies
	 *            {@link List} of generic policy instances to be filtered
	 * @return {@link List} of policy instances whose total insured amount is
	 *         more than it's product group's maximum insured amount limit
	 */
	public static final <E> List<E> filterPoliciesByInsuredAmount(List<E> policies) {
		List<E> ret = Collections.EMPTY_LIST;

		// Check if the result retrieved from the DB is NOT null and the NOT the
		// empty result list, then proceed ahead
		// Otherwise, no point in doing the further works
		if (policies != null && !policies.isEmpty()) {
			ret = new ArrayList<E>();
			double totalSumInsured = 0.0;
			ProductGroup productGroup = null;
			// Looping the result list to compare the total sum insured amount
			// of the policy against the max sum insured amount of the Product
			// Group
			for (E policy : policies) {
				totalSumInsured = getTotalSumInsured(policy);
				productGroup = getProductGroup(policy);
				// Check if the product group exists for this policy to avoid
				// the null surprise.
				if (productGroup != null && totalSumInsured > productGroup.getMaxSumInsured()) {
					logger.debug("Total Sum Insured = " + totalSumInsured);
					logger.debug("Max Sum Insured = " + productGroup.getMaxSumInsured());
					// Check if the total sum insured amount of the policy is
					// more than the max sum insured amount of the product group
					// if that's the case, then we'll keep this policy in the
					// return list
					ret.add(policy);
				}
			}
		}
		return ret;
	}

	private static <E> double getTotalSumInsured(E policy) {
		double totalSumInsured = 0.0;
		if (policy instanceof LifePolicy) {
			totalSumInsured = ((LifePolicy) policy).getTotalSumInsured();
		}
		return totalSumInsured;
	}

	private static <E> ProductGroup getProductGroup(E policy) {
		ProductGroup productGroup = null;
		if (policy instanceof LifePolicy) {
			productGroup = ((LifePolicy) policy).getProductGroup();
		}
		return productGroup;
	}

	/**
	 * This method analyzes the given {@link IPolicy} object whether it is
	 * {@link FirePolicy}, {@link FirePolicy} or {@link MotorPolicy}
	 * instance.<br>
	 * Then, the method returns {@link InsuranceType} instance which represents
	 * one of the above policy objects.
	 * 
	 * @param policy
	 *            an instance of {@link IPolicy} to be classified
	 * @return an instance of {@link InsuranceType} representing the type of
	 *         policy object
	 */
	public static InsuranceType classifyPolicy(IPolicy policy) {
		InsuranceType ret = null;
		if (policy instanceof LifePolicy) {
			ret = InsuranceType.LIFE;
		}

		return ret;
	}

	public static boolean isCoInsurancePolicy(IPolicy policy) {
		double maxSumInsured = policy.getProductGroup().getMaxSumInsured();
		double totalSumInsured = policy.getTotalSumInsured();
		if (totalSumInsured > maxSumInsured) {
			return true;
		}
		return false;
	}
}
