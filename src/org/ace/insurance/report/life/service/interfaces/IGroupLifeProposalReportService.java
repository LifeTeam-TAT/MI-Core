package org.ace.insurance.report.life.service.interfaces;

/**
 * @author NNH
 */
import java.util.List;

import org.ace.insurance.report.life.GroupLifeProposalCriteria;
import org.ace.insurance.report.life.GroupLifeProposalReport;
import org.ace.insurance.system.common.branch.Branch;

public interface IGroupLifeProposalReportService {

	public List<GroupLifeProposalReport> findLifeProposal(GroupLifeProposalCriteria grouplifeProposalCriteria);
	public void generateGroupLifeProposalReport(List<GroupLifeProposalReport> grouplifeProposalReports,
			String dirPath, String fileName, GroupLifeProposalCriteria criteria, List<Branch> branchList);
}
