package org.ace.insurance.web.manage.medical.proposal.factory;

import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.web.manage.medical.proposal.OrganizationDTO;

public class OrganizationFactory {

	public static Organization getOrganization(OrganizationDTO dto) {
		Organization organization = new Organization();
		if (dto.isExistsEntity()) {
			organization.setId(dto.getId());
			organization.setVersion(dto.getVersion());
		}
		organization.setCodeNo(dto.getCodeNo());
		organization.setName(dto.getName());
		organization.setRegNo(dto.getRegNo());
		organization.setOwnerName(dto.getOwnerName());
		organization.setActivePolicy(dto.getActivePolicy());
		organization.setActivedDate(dto.getActivedDate());
		organization.setAddress(dto.getAddress());
		organization.setContentInfo(dto.getContentInfo());
		organization.setBranch(dto.getBranch());
		return organization;
	}

	public static OrganizationDTO getOrganizationDTO(Organization org) {
		OrganizationDTO dto = new OrganizationDTO();
		if (org.getId() != null) {
			dto.setExistsEntity(true);
			dto.setId(org.getId());
			dto.setVersion(org.getVersion());
		}
		dto.setCodeNo(org.getCodeNo());
		dto.setName(org.getName());
		dto.setRegNo(org.getRegNo());
		dto.setOwnerName(org.getOwnerName());
		dto.setActivePolicy(org.getActivePolicy());
		dto.setActivedDate(org.getActivedDate());
		dto.setAddress(org.getAddress());
		dto.setContentInfo(org.getContentInfo());
		dto.setBranch(org.getBranch());
		return dto;
	}

}
