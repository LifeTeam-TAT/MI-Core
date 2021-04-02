package org.ace.insurance.report.life.service.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifeProposalCriteria;
import org.ace.insurance.report.life.LifeProposalReport;
import org.ace.insurance.report.personalAccident.PersonalAccidentProposalReport;

public interface ILifeProposalReportService {
	public List<LifeProposalReport> findLifeProposal(LifeProposalCriteria lifeProposalCriteria, List<String> productIdList);

	public void generateLifeProposalReport(List<LifeProposalReport> lifeProposalReportList, String dirPath, String fileName, String branchName) throws Exception;

	public List<PersonalAccidentProposalReport> findPersonalAccidentProposal(LifeProposalCriteria lifeProposalCriteria);

	public void generatePersonalAcdtProposalReport(List<PersonalAccidentProposalReport> personalAcdtProposalReportList, String dirPath, String fileName, String branchName)
			throws Exception;
}
