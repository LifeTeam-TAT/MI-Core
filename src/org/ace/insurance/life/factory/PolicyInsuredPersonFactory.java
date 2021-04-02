package org.ace.insurance.life.factory;

import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.PolicyInsuredPersonDTO;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonBeneficiariesHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;

public class PolicyInsuredPersonFactory {

	public static PolicyInsuredPersonDTO createPolicyInsuredPersonDTO(PolicyInsuredPerson person) {
		PolicyInsuredPersonDTO personDTO = new PolicyInsuredPersonDTO();
		personDTO.setFullName(person.getFullName());
		personDTO.setFatherName(person.getFatherName());
		personDTO.setFullIdNo(person.getIdNoForView());
		personDTO.setOccupation(person.getOccupationName());
		personDTO.setTypeOfSport(person.getTypesOfSportName());
		personDTO.setDateOfBirth(person.getDateOfBirth());
		personDTO.setInsPersonCodeNo(person.getInsPersonCodeNo());
		String beneficiaryNames = "", beneRelationships = "", beneFullIdNos = "";
		int size = person.getPolicyInsuredPersonBeneficiariesList().size();
		int count = 1;
		for (PolicyInsuredPersonBeneficiaries b : person.getPolicyInsuredPersonBeneficiariesList()) {
			beneficiaryNames += b.getFullName();
			beneRelationships += b.getRelationshipName();
			beneFullIdNos += b.getIdNoForView();
			if (count != size) {
				beneficiaryNames += ", ";
				beneRelationships += ", ";
				beneFullIdNos += ", ";
			}
		}
		personDTO.setBeneficiaryNames(beneficiaryNames);
		personDTO.setBeneRelationships(beneRelationships);
		personDTO.setBeneFullIdNos(beneFullIdNos);
		personDTO.setSumInsured(person.getSumInsured());
		personDTO.setPremium(person.getPremium());
		personDTO.setTermPremium(person.getBasicTermPremium());
		return personDTO;
	}

	public static PolicyInsuredPersonDTO createPolicyInsuredPersonDTO(PolicyInsuredPersonHistory personHistory) {
		PolicyInsuredPersonDTO personDTO = new PolicyInsuredPersonDTO();
		personDTO.setFullName(personHistory.getFullName());
		personDTO.setFatherName(personHistory.getFatherName());
		personDTO.setFullIdNo(personHistory.getIdNo());
		personDTO.setOccupation(personHistory.getOccupationName());
		personDTO.setTypeOfSport(personHistory.getTypesOfSportName());
		personDTO.setDateOfBirth(personHistory.getDateOfBirth());
		personDTO.setInsPersonCodeNo(personHistory.getInPersonCodeNo());
		String beneficiaryNames = "", beneRelationships = "", beneFullIdNos = "";
		int size = personHistory.getPolicyInsuredPersonBeneficiariesList().size();
		int count = 1;
		for (PolicyInsuredPersonBeneficiariesHistory b : personHistory.getPolicyInsuredPersonBeneficiariesList()) {
			beneficiaryNames += b.getFullName();
			beneRelationships += b.getRelationshipName();
			beneFullIdNos += b.getIdNo() == null ? "Still Applying" : b.getIdNo();
			if (count != size) {
				beneficiaryNames += ", ";
				beneRelationships += ", ";
				beneFullIdNos += ", ";
			}
		}
		personDTO.setBeneficiaryNames(beneficiaryNames);
		personDTO.setBeneRelationships(beneRelationships);
		personDTO.setBeneFullIdNos(beneFullIdNos);
		personDTO.setSumInsured(personHistory.getSumInsured());
		personDTO.setPremium(personHistory.getPremium());
		personDTO.setTermPremium(personHistory.getBasicTermPremium());
		return personDTO;
	}

}
