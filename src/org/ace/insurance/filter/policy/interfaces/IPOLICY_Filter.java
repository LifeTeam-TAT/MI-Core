package org.ace.insurance.filter.policy.interfaces;

import java.util.List;

import org.ace.insurance.filter.policy.AllPolicyCriteria;
import org.ace.insurance.filter.policy.POLICY001;

public interface IPOLICY_Filter {

	List<POLICY001> findPolicyByCriteria(AllPolicyCriteria policyCriteria);

}
