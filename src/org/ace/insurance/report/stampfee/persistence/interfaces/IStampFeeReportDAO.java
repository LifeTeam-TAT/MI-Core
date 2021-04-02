package org.ace.insurance.report.stampfee.persistence.interfaces;

import java.util.List;

import org.ace.insurance.report.stampfee.StampFeeCriteria;
import org.ace.insurance.report.stampfee.StampFeeReport;
import org.ace.java.component.persistence.exception.DAOException;

/**
 * This interface serves as the DAO to manipulate the <code>Stamp Fee Report</code> object.
 * 
 * @author TDP
 * @since 1.0.0
 * @date 2013/11/28
 */
public interface IStampFeeReportDAO {

	public List<StampFeeReport> find(StampFeeCriteria criteria) throws DAOException;
}
