package org.ace.insurance.report.life.persistence.interfaces;

/**
 * @author NNH
 */
import java.util.List;

import org.ace.insurance.report.life.GroupLifeProposalCriteria;
import org.ace.insurance.report.life.GroupLifeProposalReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface IGroupLifeProposalReportDAO {
	public List<GroupLifeProposalReport> find(GroupLifeProposalCriteria grouplifeProposalCriteria)throws DAOException;

}
