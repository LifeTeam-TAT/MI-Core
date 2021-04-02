package org.ace.insurance.report.life.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.life.LifeClaimRegisterReport;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeClaimRegisterReportDAO {
	public List<LifeClaimRegisterReport> find(EnquiryCriteria criteria, List<String> productIdList) throws DAOException;
}
