package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;

public interface IClaimMedicalFeesDAO {

	public void insert(ClaimMedicalFees medicalFees);

	public void delete(ClaimMedicalFees medicalFees);

	public void update(ClaimMedicalFees medicalFees);

	public List<ClaimMedicalFees> findMedicalFeesBySanctionNo(String sanctionNo);

	public void updateMedicalFeesStaus(List<LifeClaimMedicalFeeDTO> claimMedicalFeeInfoList);

	public List<ClaimMedicalFees> findMedicalFeesByInvoiceNo(String invoiceNo);

}
