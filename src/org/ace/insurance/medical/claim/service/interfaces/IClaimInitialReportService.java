package org.ace.insurance.medical.claim.service.interfaces;

import java.util.List;

import org.ace.insurance.medical.claim.ClaimInitialReport;
import org.ace.insurance.medical.claim.ClaimStatus;

public interface IClaimInitialReportService {

	public void addNewClaimInitialReport(ClaimInitialReport claimInitialReport);

	public void updateClaimInitialReport(ClaimInitialReport claimInitialReport);

	public List<ClaimInitialReport> findAllClaimInitialReport();

	public List<ClaimInitialReport> findAllActiveClaim();

	public ClaimInitialReport findByPolicyInsuredPersonId(String id);

	public void updateByInsuredPerson(String id, ClaimStatus claimStatus);

}
