package org.ace.insurance.report.life.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifeProposalCriteria;
import org.ace.insurance.report.life.LifeProposalReport;
import org.ace.insurance.report.personalAccident.PersonalAccidentProposalReport;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeProposalReportDAO {
	public List<LifeProposalReport> find(LifeProposalCriteria lifeProposalCriteria, List<String> productList) throws DAOException;

	public List<PersonalAccidentProposalReport> findPersonalAccidentProposal(LifeProposalCriteria lifeProposalCriteria) throws DAOException;
}
