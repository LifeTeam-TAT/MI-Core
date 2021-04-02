package org.ace.insurance.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.life.endorsement.LifeBeneficiary;
import org.ace.insurance.life.endorsement.LifeEndorseItem;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;

/***************************************************************************************
 * @author <<Ye Wint Aung>>
 * @Date 2013-06-21
 * @Version 1023.0
 * @Purpose Utility class for Endorsement process.
 * 
 * 
 ***************************************************************************************/
public class EndorsementUtil {

	public static boolean isTerminate(EndorsementStatus endorsementStatus) {
		return Arrays.asList(EndorsementStatus.TERMINATE_BY_CUSTOMER, EndorsementStatus.TERMINATE_BY_RENEWAL, EndorsementStatus.TERMINATE_BY_INSURER, EndorsementStatus.TERMINATE)
				.contains(endorsementStatus);
	}

	public static <T> boolean isEndorsementProposal(T policy) {
		// return true if proposal has policy,otherwise false.
		return policy != null;
	}

	public static <T> boolean isTerminatePolicy(T proposal) {
		boolean result = false;

		if (proposal instanceof LifeProposal) {
			LifeProposal lifeProposal = (LifeProposal) proposal;
			for (ProposalInsuredPerson proposalInsuredPerson : lifeProposal.getProposalInsuredPersonList()) {
				if ((proposalInsuredPerson.getEndorsementStatus() == null) || (proposalInsuredPerson.getEndorsementStatus().equals(EndorsementStatus.REPLACE))
						|| proposalInsuredPerson.getEndorsementStatus().equals(EndorsementStatus.EDIT)
						|| proposalInsuredPerson.getEndorsementStatus().equals(EndorsementStatus.NEW)) {
					return false;
				}
			}
			result = true;
		}

		if (proposal instanceof LifePolicy) {
			LifePolicy lifePolicy = (LifePolicy) proposal;
			for (PolicyInsuredPerson policyInsuredPerson : lifePolicy.getPolicyInsuredPersonList()) {
				if ((policyInsuredPerson.getEndorsementStatus() == null) || (policyInsuredPerson.getEndorsementStatus().equals(EndorsementStatus.REPLACE))
						|| policyInsuredPerson.getEndorsementStatus().equals(EndorsementStatus.EDIT) || policyInsuredPerson.getEndorsementStatus().equals(EndorsementStatus.NEW)) {
					return false;
				}
			}
			result = true;
		}
		return result;
	}

	public static <T> List<T> getRemoveItemList(List<T> newList, List<T> oldList) {
		List<T> resultList = new ArrayList<T>();
		for (T t : oldList) {
			if (!newList.contains(t)) {
				resultList.add(t);
			}
		}
		return resultList;
	}

	public static <T> List<T> getNewItemList(List<T> newList, List<T> oldList) {
		List<T> resultList = new ArrayList<T>();
		for (T t : newList) {
			if (!oldList.contains(t)) {
				resultList.add(t);
			}
		}
		return resultList;
	}

	public static <T> List<T> getDuplicateItemList(List<T> newList, List<T> oldList) {
		List<T> resultList = new ArrayList<T>();
		for (T t : newList) {
			if (oldList.contains(t)) {
				resultList.add(t);
			}
		}
		return resultList;
	}

	/**************************************************************
	 * FIRE
	 **************************************************************/

	/* For Life Endorsement */
	public static List<LifeBeneficiary> getNewBeneficiaryItemList(List<LifeBeneficiary> oldList, List<LifeBeneficiary> newList) {
		List<LifeBeneficiary> resultList = new ArrayList<LifeBeneficiary>();
		for (LifeBeneficiary t : newList) {
			if (!oldList.contains(t)) {
				resultList.add(t);
			}
		}
		return resultList;
	}

	public static List<org.ace.insurance.life.endorsement.LifeBeneficiary> getRemoveBeneficiaryItemList(List<org.ace.insurance.life.endorsement.LifeBeneficiary> oldList,
			List<org.ace.insurance.life.endorsement.LifeBeneficiary> newList) {
		List<org.ace.insurance.life.endorsement.LifeBeneficiary> resultList = new ArrayList<org.ace.insurance.life.endorsement.LifeBeneficiary>();
		for (org.ace.insurance.life.endorsement.LifeBeneficiary t : oldList) {
			if (!newList.contains(t)) {
				resultList.add(t);
			}
		}
		return resultList;
	}

	public static List<org.ace.insurance.life.endorsement.LifeBeneficiary> getReplaceBeneficiaryItemList(List<org.ace.insurance.life.endorsement.LifeBeneficiary> oldList,
			List<org.ace.insurance.life.endorsement.LifeBeneficiary> newList) {
		List<org.ace.insurance.life.endorsement.LifeBeneficiary> resultList = new ArrayList<org.ace.insurance.life.endorsement.LifeBeneficiary>();
		for (org.ace.insurance.life.endorsement.LifeBeneficiary t : newList) {
			for (org.ace.insurance.life.endorsement.LifeBeneficiary d : oldList) {
				if (t.getBeneficiaryNo().equals(d.getBeneficiaryNo()) && (!t.getIdNo().equals(d.getIdNo()) || !t.getName().equals(d.getName()))) {
					resultList.add(t);
				}

			}

		}
		return resultList;
	}

	public static List<ProposalInsuredPerson> validateNewProposalInsuList(List<ProposalInsuredPerson> proposalInsuredPersonList,
			Map<String, PolicyInsuredPerson> policyInsuredPersonMap) {
		List<ProposalInsuredPerson> result = new ArrayList<ProposalInsuredPerson>();
		for (ProposalInsuredPerson proposalInsuredPerson : proposalInsuredPersonList) {
			if (!policyInsuredPersonMap.containsKey(proposalInsuredPerson.getInsPersonCodeNo())) {
				result.add(proposalInsuredPerson);
			}
		}

		return result;
	}

	public static List<ProposalInsuredPerson> getNewProposalInsuredPersonItemList(List<PolicyInsuredPerson> oldList, List<ProposalInsuredPerson> newList) {
		List<ProposalInsuredPerson> resultList = new ArrayList<ProposalInsuredPerson>();
		Map<String, PolicyInsuredPerson> policyInsuredPersonMap = new HashMap<String, PolicyInsuredPerson>();
		for (PolicyInsuredPerson policyInsuredPerson : oldList) {
			policyInsuredPersonMap.put(policyInsuredPerson.getInsPersonCodeNo(), policyInsuredPerson);
		}
		resultList = validateNewProposalInsuList(newList, policyInsuredPersonMap);

		return resultList;
	}

	/* For Life */
	/**************************************************************
	 * LIFE
	 **************************************************************************/
	public static boolean isLifeInsuredPersonNew(List<LifeEndorseItem> items) {
		if (items.contains(LifeEndorseItem.INSUREDPERSON_NEW)) {
			return true;
		}
		return false;
	}

	public static boolean isLifeSIIncreOnly(List<LifeEndorseItem> items) {
		if (items.contains(LifeEndorseItem.INCREASE_SI) && !items.contains(LifeEndorseItem.DECREASE_SI) && !items.contains(LifeEndorseItem.INCREASE_TERM)
				&& !items.contains(LifeEndorseItem.DECREASE_TERM)) {
			return true;
		}
		return false;
	}

	public static boolean isLifeSIDecreOnly(List<LifeEndorseItem> items) {
		if (!items.contains(LifeEndorseItem.INCREASE_SI) && items.contains(LifeEndorseItem.DECREASE_SI) && !items.contains(LifeEndorseItem.INCREASE_TERM)
				&& !items.contains(LifeEndorseItem.DECREASE_TERM)) {
			return true;
		}
		return false;
	}

	public static boolean isLifeTermIncreOnly(List<LifeEndorseItem> items) {
		if (!items.contains(LifeEndorseItem.INCREASE_SI) && !items.contains(LifeEndorseItem.DECREASE_SI) && items.contains(LifeEndorseItem.INCREASE_TERM)
				&& !items.contains(LifeEndorseItem.DECREASE_TERM)) {
			return true;
		}
		return false;
	}

	public static boolean isLifeTermDecreOnly(List<LifeEndorseItem> items) {
		if (!items.contains(LifeEndorseItem.INCREASE_SI) && !items.contains(LifeEndorseItem.DECREASE_SI) && !items.contains(LifeEndorseItem.INCREASE_TERM)
				&& items.contains(LifeEndorseItem.DECREASE_TERM)) {
			return true;
		}
		return false;
	}

	public static boolean isInsuredPersonChanges(ProposalInsuredPerson newProposalInsuredPerson, PolicyInsuredPerson oldProposalInsuredPerson) {
		if (!newProposalInsuredPerson.getFullName().equals(oldProposalInsuredPerson.getFullName())
				& !newProposalInsuredPerson.getIdNo().equals(oldProposalInsuredPerson.getIdNo())) {
			return true;
		}
		return false;
	}

	public static boolean isNRCNOChanges(ProposalInsuredPerson newProposalInsuredPerson, PolicyInsuredPerson oldProposalInsuredPerson) {
		if (!newProposalInsuredPerson.getIdNo().equals(oldProposalInsuredPerson.getIdNo())
				& newProposalInsuredPerson.getFullName().equals(oldProposalInsuredPerson.getFullName())) {
			return true;
		}
		return false;
	}

	public static boolean isSumInsuredChanges(ProposalInsuredPerson newProposalInsuredPerson, PolicyInsuredPerson oldProposalInsuredPerson) {
		if (newProposalInsuredPerson.getProposedSumInsured() != oldProposalInsuredPerson.getSumInsured()) {
			return true;
		}
		return false;
	}

	public static boolean isBeneficiaryPersonChanges(ProposalInsuredPerson newProposalInsuredPerson, PolicyInsuredPerson oldProposalInsuredPerson) {
		for (InsuredPersonBeneficiaries newbene : newProposalInsuredPerson.getInsuredPersonBeneficiariesList()) {
			for (PolicyInsuredPersonBeneficiaries oldbene : oldProposalInsuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
				if (!newbene.getIdNo().equals(oldbene.getIdNo()) || newbene.getPercentage() != oldbene.getPercentage() || !newbene.getFullName().equals(oldbene.getFullName())) {
					return true;
				}
			}
		}
		if (newProposalInsuredPerson.getInsuredPersonBeneficiariesList().size() != oldProposalInsuredPerson.getPolicyInsuredPersonBeneficiariesList().size()) {
			return true;
		}
		return false;
	}

	// public static int getPassedDays(Date startDate, Date endorseDate, boolean
	// includeEndorseDate, boolean dayCount) {
	// if (!includeEndorseDate) {
	// endorseDate = Utils.minusDays(endorseDate, 1);
	// }
	// return Utils.daysBetween(startDate, endorseDate, false, dayCount);
	// }
	//
	// public static int getRestDays(Date endorseDate, Date endDate, boolean
	// includeEndorseDate, boolean dayCount) {
	// if (!includeEndorseDate) {
	// endorseDate = Utils.plusDays(endorseDate, 1);
	// }
	// return Utils.daysBetween(endorseDate, endDate, false, dayCount);
	// }
	//
	// public static Period getPassedPeriod(Date startDate, Date endorseDate,
	// boolean includeEndorseDate, boolean dayCount) {
	// if (!includeEndorseDate) {
	// endorseDate = Utils.minusDays(endorseDate, 1);
	// }
	// return Utils.getPeriod(startDate, endorseDate, false, dayCount);
	// }
	//
	// public static Period getRestPeriod(Date endorseDate, Date endDate,
	// boolean includeEndorseDate, boolean dayCount) {
	// if (!includeEndorseDate) {
	// endorseDate = Utils.plusDays(endorseDate, 1);
	// }
	// return Utils.getPeriod(endorseDate, endDate, false, dayCount);
	// }

	public static <T> T getLatestPolicyHistory(List<T> list) {
		List<Date> endorsementConfirmDateList = new ArrayList<Date>();
		for (T t : list) {
			if (t instanceof LifePolicyHistory) {
				LifePolicyHistory lifePolicyHistory = (LifePolicyHistory) t;
				endorsementConfirmDateList.add(lifePolicyHistory.getEndorsementConfirmDate());
			}

		}
		int index = Utils.getIndexOfMaxDate(endorsementConfirmDateList);
		return list.get(index);

	}

	public static void main(String arg[]) {

	}

}
