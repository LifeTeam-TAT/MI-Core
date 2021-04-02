package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.medical.proposal.CustomerInfoStatus;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.web.manage.medical.proposal.CustomerDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProGuardianDTO;

public class CustomerFactory {
	public static Customer getCustomer(CustomerDTO dto) {
		Customer customer = new Customer();
		if (dto.isExistsEntity()) {
			customer.setId(dto.getId());
			customer.setVersion(dto.getVersion());
		}
		customer.setName(dto.getName());
		customer.setInitialId(dto.getInitialId());
		customer.setFatherName(dto.getFatherName());
		customer.setIdNo(dto.getIdNo());
		customer.setDateOfBirth(dto.getDateOfBirth());
		customer.setLabourNo(dto.getLabourNo());
		customer.setBirthMark(dto.getBirthMark());
		customer.setSalary(dto.getSalary());
		customer.setClosedPolicy(dto.getClosedPolicy());
		customer.setActivePolicy(dto.getActivePolicy());
		customer.setBankAccountNo(dto.getBankAccountNo());
		customer.setGender(dto.getGender());
		customer.setIdType(dto.getIdType());
		customer.setMaritalStatus(dto.getMaritalStatus());
		customer.setOfficeAddress(dto.getOfficeAddress());
		customer.setPermanentAddress(dto.getPermanentAddress());
		customer.setResidentAddress(dto.getResidentAddress());
		customer.setContentInfo(dto.getContentInfo());
		customer.setFamilyInfo(dto.getFamilyInfo());
		customer.setBranch(dto.getBranch());
		customer.setQualification(dto.getQualification());
		customer.setOccupation(dto.getOccupation());
		customer.setReligion(dto.getReligion());
		customer.setCountry(dto.getCountry());
		customer.setIndustry(dto.getIndustry());
		customer.setBankBranch(dto.getBankBranch());
		customer.setPlaceOfBirth(dto.getPlaceOfBirth());
		customer.setWeight(dto.getWeight());
		customer.setHeight(dto.getHeight());
		customer.setStateCode(dto.getStateCode());
		customer.setTownshipCode(dto.getTownshipCode());
		customer.setFullIdNo(dto.getFullIdNo());
		customer.setIdConditionType(dto.getIdConditionType());
		customer.setPassportType(dto.getpType());

		if (dto.getCustomerInfoStatusList() != null) {
			for (CustomerInfoStatus cis : dto.getCustomerInfoStatusList()) {
				customer.addCustomerInfoStatus(cis);
			}
		}
		if (dto.getRecorder() != null) {
			customer.setRecorder(dto.getRecorder());
		}

		return customer;
	}

	public static CustomerDTO getCustomerDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		if (customer.getId() != null) {
			dto.setExistsEntity(true);
			dto.setId(customer.getId());
			dto.setVersion(customer.getVersion());
		}
		dto.setName(customer.getName());
		dto.setInitialId(customer.getInitialId());
		dto.setFatherName(customer.getFatherName());
		dto.setIdNo(customer.getIdNo());
		dto.setDateOfBirth(customer.getDateOfBirth());
		dto.setLabourNo(customer.getLabourNo());
		dto.setBirthMark(customer.getBirthMark());
		dto.setSalary(customer.getSalary());
		dto.setClosedPolicy(customer.getClosedPolicy());
		dto.setActivePolicy(customer.getActivePolicy());
		dto.setBankAccountNo(customer.getBankAccountNo());
		dto.setGender(customer.getGender());
		dto.setIdType(customer.getIdType());
		dto.setMaritalStatus(customer.getMaritalStatus());
		dto.setOfficeAddress(customer.getOfficeAddress());
		dto.setPermanentAddress(customer.getPermanentAddress());
		dto.setResidentAddress(customer.getResidentAddress());
		dto.setContentInfo(customer.getContentInfo());
		dto.setFamilyInfo(customer.getFamilyInfo());
		dto.setBranch(customer.getBranch());
		dto.setQualification(customer.getQualification());
		dto.setOccupation(customer.getOccupation());
		dto.setReligion(customer.getReligion());
		dto.setCountry(customer.getCountry());
		dto.setIndustry(customer.getIndustry());
		dto.setBankBranch(customer.getBankBranch());
		dto.setPlaceOfBirth(customer.getPlaceOfBirth());
		dto.setHeight(customer.getHeight());
		dto.setWeight(customer.getWeight());
		dto.setStateCode(customer.getStateCode());
		dto.setTownshipCode(customer.getTownshipCode());
		dto.setFullIdNo(customer.getFullIdNo());
		dto.setIdNo(customer.getIdNo());
		dto.setIdConditionType(customer.getIdConditionType());
		dto.setpType(customer.getPassportType());
		if (customer.getCustomerStatusList() != null) {
			for (CustomerInfoStatus cis : customer.getCustomerStatusList()) {
				dto.addCustomerInfoStatus(cis);
			}
		}
		if (dto.getRecorder() != null) {
			customer.setRecorder(dto.getRecorder());
		}

		return dto;
	}

	public static MedProGuardianDTO getGuardianFromCustomer(Customer c) {
		MedProGuardianDTO gdto = new MedProGuardianDTO();
		gdto.setCustomer(getCustomerDTO(c));
		return gdto;
	}
}
