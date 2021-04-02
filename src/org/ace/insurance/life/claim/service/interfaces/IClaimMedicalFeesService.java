package org.ace.insurance.life.claim.service.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;

public interface IClaimMedicalFeesService {

	public void insert(ClaimMedicalFees claimMedicalFees);

	public void delete(ClaimMedicalFees claimMedicalFees);

	public void update(ClaimMedicalFees claimMedicalFees);

	public List<ClaimMedicalFees> findMedicalFeesBySanctionNo(String santionNo);

	public void updateMedicalFeesStaus(List<LifeClaimMedicalFeeDTO> claimMedicalFeeInfoList);

	public List<ClaimMedicalFees> findMedicalFeesByInvoiceNo(String invoiceNo);
}
