package org.ace.insurance.payment.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.payment.TLF;
import org.ace.insurance.payment.persistence.interfacs.ITLFDAO;
import org.ace.insurance.report.TLF.AccountAndLifeCancelReportDTO;
import org.ace.insurance.report.TLF.AccountManualReceiptDTO;
import org.ace.insurance.report.TLF.CeoShortTermLifeDTO;
import org.ace.insurance.report.TLF.DailyIncomeReportDTO;
import org.ace.insurance.report.TLF.FarmerMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.GroupLifeMonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.GroupLifeMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.MonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.PAMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.SalePointIncomeCriteria;
import org.ace.insurance.report.TLF.SalePointIncomeReportDTO;
import org.ace.insurance.report.TLF.SnakeBiteMonthlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.SnakeBiteMonthlyIncomeReportDTO;
import org.ace.insurance.report.TLF.StudentMontlyIncomeReportCriteria;
import org.ace.insurance.report.TLF.StudentMontlyIncomeReportDTO;
import org.ace.insurance.report.TLF.TlfCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("TLFDAO")
public class TLFDAO extends BasicDAO implements ITLFDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public TLF insert(TLF tlf) throws DAOException {
		try {
			em.persist(tlf);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert TLF", pe);
		}
		return tlf;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PAMonthlyIncomeReportDTO> findPAMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<PAMonthlyIncomeReportDTO> resultList = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + PAMonthlyIncomeReportDTO.class.getName());
			buffer.append("(m.customerNam,m.address,m.sumInsured,m.premium,m.agentName,m.receiptNo,m.paymentDate,m.liscenseNo,m.periodMonth,"
					+ "m.activedPolicyStartDate,m.activedPolicyEndDate,m.commission,m.numberOfInsuredPerson,m.branchName,m.policyNo,m.sumInsuredUsd,m.premiumUsd,m.commissionUsd,m.salePointsName,m.fromDateToDate,m.fromTermToTerm,m.saleChannelType)FROM PAMonthlyIncomeReportView m");
			buffer.append(" WHERE 1=1 ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND m.salePointId = :salePointId ");
			 */
			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND m.branchId = :branchId ");

			if (criteria.getSaleChannelType() != null)
				buffer.append("AND m.saleChannelType = :saleChannelType ");

			if (criteria.getStartDate() != null)
				buffer.append("AND m.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND m.paymentDate <= :endDate ");

			buffer.append("ORDER BY m.policyNo");

			Query query = em.createQuery(buffer.toString());

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */
			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());

			if (criteria.getSaleChannelType() != null)
				query.setParameter("saleChannelType", criteria.getSaleChannelType());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			resultList = query.getResultList();

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find findPAMonthlyIncomeReport", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<FarmerMonthlyIncomeReportDTO> findFarmerMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<FarmerMonthlyIncomeReportDTO> resultList = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + FarmerMonthlyIncomeReportDTO.class.getName());
			buffer.append(
					"(m.paymentDate,m.customerName,m.address,m.nrcNo,m.sumInsured,m.premium,m.agentName,m.liscenseNo,m.commission,m.branchName,m.branchId,m.salePointsName,m.fromDateToDate,m.fromTermToTerm,m.saleChannelType) From FarmerMonthlyIncomeReportView m");
			buffer.append(" WHERE 1=1 ");
			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND m.salepointsId = :salepointsId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND m.branchId = :branchId ");

			if (criteria.getSaleChannelType() != null)
				buffer.append("AND m.saleChannelType = :saleChannelType ");

			if (criteria.getStartDate() != null)
				buffer.append("AND m.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND m.paymentDate <= :endDate ");

			buffer.append("ORDER BY m.paymentDate");

			Query query = em.createQuery(buffer.toString());
			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salepointsId",
			 * criteria.getSalePointId());
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());

			if (criteria.getSaleChannelType() != null)
				query.setParameter("saleChannelType", criteria.getSaleChannelType());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			resultList = query.getResultList();

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find FarmerMonthlyIncomeReport", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SalePointIncomeReportDTO> findSalePointIncomeByCriteria(SalePointIncomeCriteria sPIncomeCriteria) throws DAOException {
		List<SalePointIncomeReportDTO> result = new ArrayList<SalePointIncomeReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT  NEW " + SalePointIncomeReportDTO.class.getName()
					+ " (l.tlfNo, l.coaId, l.narration, l.salePointName, l.salePointId, l.paymentChannel, l.createdDate, l.cashDebit,"
					+ "l.cashCredit, l.transferDebit, l.transferCredit, l.policyNo, l.agentTransaction,l.accountType) FROM SalePointIncomeReportView l WHERE l.tlfNo IS NOT NULL ");
			/*
			 * if (sPIncomeCriteria.getSalePointId() != null)
			 * query.append(" AND l.salePointId = :salePointId");
			 */
			if (sPIncomeCriteria.getStartDate() != null) {
				query.append(" AND l.createdDate >= :startDate");
			}
			if (sPIncomeCriteria.getEndDate() != null) {
				query.append(" AND l.createdDate <= :endDate");
			}
			if (sPIncomeCriteria.getPaymentChannel() != null) {
				query.append(" AND l.paymentChannel = :paymentChannel");
			}

			query.append(
					" ORDER BY l.tlfNo,CASE WHEN (l.accountType LIKE 'A%') THEN 1 WHEN (l.accountType LIKE 'I%') THEN 2 WHEN (l.accountType LIKE 'L%')  THEN 3 WHEN (l.accountType LIKE 'E%')  THEN 4 ELSE 5 END");

			Query q = em.createQuery(query.toString());
			/*
			 * if (sPIncomeCriteria.getSalePointId() != null) q.setParameter("salePointId",
			 * sPIncomeCriteria.getSalePointId());
			 */

			if (sPIncomeCriteria.getStartDate() != null) {
				sPIncomeCriteria.setStartDate(Utils.resetStartDate(sPIncomeCriteria.getStartDate()));
				q.setParameter("startDate", sPIncomeCriteria.getStartDate());
			}
			if (sPIncomeCriteria.getEndDate() != null) {
				sPIncomeCriteria.setEndDate(Utils.resetEndDate(sPIncomeCriteria.getEndDate()));
				q.setParameter("endDate", sPIncomeCriteria.getEndDate());
			}
			if (sPIncomeCriteria.getPaymentChannel() != null) {
				q.setParameter("paymentChannel", sPIncomeCriteria.getPaymentChannel());
			}

			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of SalePoint Income by criteria.", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyIncomeReportDTO> findDailyReportDTO(TlfCriteria criteria) throws DAOException {
		List<DailyIncomeReportDTO> resultList = new ArrayList<DailyIncomeReportDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append(
					"SELECT NEW " + DailyIncomeReportDTO.class.getName() + "(d.paymentDate,d.paymentChannel,d.receiptNo,d.policyNo,d.productName,d.homeAmount,d.salePointName)");
			query.append(" FROM DailyIncomeReportView d WHERE d.policyNo IS NOT NULL");

			if (criteria.getStartDate() != null) {
				query.append(" AND d.paymentDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND d.paymentDate <= :endDate");
			}
			/*
			 * if (criteria.getSalePoint() != null) {
			 * query.append(" AND d.salePointId = :salePointId"); }
			 */
			if (criteria.getPaymentChannel() != null) {
				query.append(" AND d.paymentChannel = :paymentChannel");
			}
			if (!criteria.getProductIdList().isEmpty()) {
				query.append(" AND d.productId IN :productId");
			}

			query.append(" ORDER BY d.paymentDate");

			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", Utils.resetStartDate(criteria.getStartDate()));
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", Utils.resetEndDate(criteria.getEndDate()));
			}
			/*
			 * if (criteria.getSalePoint() != null) { q.setParameter("salePointId",
			 * criteria.getSalePoint().getId()); }
			 */
			if (criteria.getPaymentChannel() != null) {
				q.setParameter("paymentChannel", criteria.getPaymentChannel().toString());
			}
			if (!criteria.getProductIdList().isEmpty()) {
				q.setParameter("productId", criteria.getProductIdList());
			}

			resultList = q.getResultList();

		} catch (PersistenceException pe) {
			throw translate("Failed to find DailyIncomeReportDTO", pe);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AccountAndLifeCancelReportDTO> findCancelReportDTO(TlfCriteria criteria) throws DAOException {
		List<AccountAndLifeCancelReportDTO> resultList = new ArrayList<AccountAndLifeCancelReportDTO>();
		try {

			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + AccountAndLifeCancelReportDTO.class.getName() + "(a)");
			buffer.append(" FROM AccountAndLifeDeptCancelReportView a WHERE a.TLFNo IS NOT NULL ");

			if (criteria.getStartDate() != null) {
				buffer.append(" AND a.accountReceiptDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				buffer.append(" AND a.accountReceiptDate <= :endDate");
			}

			/*
			 * if (criteria.getSalePoint() != null) {
			 * buffer.append(" AND a.salePointId = :salePointId"); }
			 */
			if (criteria.getPaymentChannel() != null) {
				buffer.append(" AND a.paymentChannel = :paymentChannel");
			}
			if (!criteria.getProductIdList().isEmpty()) {
				buffer.append(" AND a.productId IN :productId");
			}

			Query q = em.createQuery(buffer.toString());

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", Utils.resetStartDate(criteria.getStartDate()));
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", Utils.resetEndDate(criteria.getEndDate()));
			}
			/*
			 * if (criteria.getSalePoint() != null) { q.setParameter("salePointId",
			 * criteria.getSalePoint().getId()); }
			 */
			if (criteria.getPaymentChannel() != null) {
				q.setParameter("paymentChannel", criteria.getPaymentChannel().toString());
			}
			if (!criteria.getProductIdList().isEmpty()) {
				q.setParameter("productId", criteria.getProductIdList());
			}

			resultList = q.getResultList();

		} catch (PersistenceException pe) {
			throw translate("Failed to find CancelReportDTO", pe);
		}

		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AccountManualReceiptDTO> findAccountManualReceiptDTO(TlfCriteria tlfCriteria) throws DAOException {
		List<AccountManualReceiptDTO> resultList = new ArrayList<AccountManualReceiptDTO>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT C.ACNAME, SUM(T.HOMEAMOUNT), CONVERT(VARCHAR(10),T.CREATEDDATE,120)");
			buffer.append(" FROM TLF T, COA C, CCOA CC");
			buffer.append("	WHERE T.ENO LIKE 'VOC%' AND C.ACTYPE='I' AND T.CCOAID=CC.ID AND CC.COAID=C.ID");

			if (tlfCriteria.getStartDate() != null) {
				Date startDate = Utils.resetStartDate(tlfCriteria.getStartDate());
				String formattedDate = Utils.getDatabaseDateString(startDate);
				buffer.append(" AND T.CREATEDDATE >= '" + formattedDate + "'");
			}
			if (tlfCriteria.getEndDate() != null) {
				Date endDate = Utils.resetEndDate(tlfCriteria.getEndDate());
				String formattedEndDate = Utils.getDatabaseDateString(endDate);
				buffer.append(" AND T.CREATEDDATE <= '" + formattedEndDate + "'");
			}

			buffer.append(" GROUP BY C.ACNAME,CONVERT(VARCHAR(10),T.CREATEDDATE,120)");
			buffer.append("	ORDER BY CONVERT(VARCHAR(10),T.CREATEDDATE,120)");

			Query q = em.createNativeQuery(buffer.toString());

			List<Object[]> objectList = new ArrayList<>();

			objectList = q.getResultList();
			for (Object[] object : objectList) {
				resultList.add(new AccountManualReceiptDTO(String.valueOf(object[0]), ((BigDecimal) object[1]).doubleValue(), String.valueOf(object[2])));
			}

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AccountManualReceiptDT", pe);
		}
		return resultList;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MonthlyIncomeReportDTO> findMonthlyIncomeReport(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<MonthlyIncomeReportDTO> resultList = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + MonthlyIncomeReportDTO.class.getName());
			buffer.append("(m.customerName , m.organizationName, m.sumInsured, m.premium, m.activedPolicyStartDate, m.activedPolicyEndDate,"
					+ "m.productName ,m.paymentDate, m.saleChannelType, m.agentName,m.branchName) FROM MonthlyIncomeReportView m");

			buffer.append(" WHERE 1=1 ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND m.salePointId = :salePointId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND m.branchId = :branchId ");

			if (criteria.getSaleChannelType() != null)
				buffer.append("AND m.saleChannelType = :saleChannelType ");

			if (criteria.getSaleChannelType() != null && criteria.getSaleChannelType().equals("DIRECTMARKETING") && criteria.isIncludeAgent() == true)
				buffer.append("AND m.agentName IS NOT NULL ");

			if (criteria.getSaleChannelType() != null && criteria.getSaleChannelType().equals("DIRECTMARKETING") && criteria.isIncludeAgent() == false)
				buffer.append("AND m.agentName IS NULL ");

			if (criteria.getStartDate() != null)
				buffer.append("AND m.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND m.paymentDate <= :endDate ");

			buffer.append("ORDER BY m.paymentDate");

			Query query = em.createQuery(buffer.toString());

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */
			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());

			if (criteria.getSaleChannelType() != null)
				query.setParameter("saleChannelType", criteria.getSaleChannelType());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			resultList = query.getResultList();
		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find findMonthlyIncomeReport", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MonthlyIncomeReportDTO> findQuantityAndTotalSIDetails(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<MonthlyIncomeReportDTO> resultList = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + MonthlyIncomeReportDTO.class.getName());
			buffer.append("(m.productId,COUNT(DISTINCT(m.policyId)),SUM(m.sumInsured)) FROM MonthlyIncomeReportView m");
			buffer.append(" WHERE 1=1 ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND m.salePointId = :salePointId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND m.branchId = :branchId ");

			if (criteria.getSaleChannelType() != null)
				buffer.append("AND m.saleChannelType = :saleChannelType ");

			if (criteria.getSaleChannelType() != null && criteria.getSaleChannelType().equals("DIRECTMARKETING") && criteria.isIncludeAgent() == true)
				buffer.append("AND m.agentName IS NOT NULL ");

			if (criteria.getSaleChannelType() != null && criteria.getSaleChannelType().equals("DIRECTMARKETING") && criteria.isIncludeAgent() == false)
				buffer.append("AND m.agentName IS NULL ");

			if (criteria.getStartDate() != null)
				buffer.append("AND m.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND m.paymentDate <= :endDate ");

			buffer.append("GROUP BY m.productId");

			Query query = em.createQuery(buffer.toString());
			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());

			if (criteria.getSaleChannelType() != null)
				query.setParameter("saleChannelType", criteria.getSaleChannelType());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			resultList = query.getResultList();

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AccountManualReceiptDT", pe);
		}
		return resultList;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<GroupLifeMonthlyIncomeReportDTO> findGLMonthlyIncomeReport(GroupLifeMonthlyIncomeReportCriteria criteria) throws DAOException {
		List<GroupLifeMonthlyIncomeReportDTO> resultList = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + GroupLifeMonthlyIncomeReportDTO.class.getName());
			buffer.append("(m.policyNo, m.receiptNo, m.customerName, m.agentName, m.paymentDate, m.sumInsured, m.premium,"
					+ "m.commission, m.insuPersonCount, m.address,m.salePointsName,m.fromDateToDate,m.fromTermToTerm,m.saleChannelType) FROM GroupLifeMonthlyIncomeReportView m");
			buffer.append(" WHERE 1=1 ");
			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND m.salePointId = :salePointId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND m.branchId = :branchId ");

			if (criteria.getStartDate() != null)
				buffer.append("AND m.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND m.paymentDate <= :endDate ");

			if (criteria.getSaleChannelType() != null)
				buffer.append("AND m.saleChannelType = :saleChannelType ");

			buffer.append("ORDER BY m.policyNo");

			Query query = em.createQuery(buffer.toString());
			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */
			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());

			if (criteria.getSaleChannelType() != null)
				query.setParameter("saleChannelType", criteria.getSaleChannelType());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			resultList = query.getResultList();

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Group Life Montly Income Report ", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SnakeBiteMonthlyIncomeReportDTO> findSNBMonthlyIncomeReport(SnakeBiteMonthlyIncomeReportCriteria criteria) throws DAOException {
		List<SnakeBiteMonthlyIncomeReportDTO> resultList = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + SnakeBiteMonthlyIncomeReportDTO.class.getName());
			buffer.append("(m.policyNo, m.customerName, m.agentName, m.paymentDate, m.sumInsured, m.premium,"
					+ "m.commission, m.unit, m.address, m.idNo,m.salePointsName,m.fromDateToDate,m.fromTermToTerm,m.saleChannelType) FROM SnakeBiteMonthlyIncomeReportView m");
			buffer.append(" WHERE 1=1 ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND m.salePointId = :salePointId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND m.branchId = :branchId ");

			if (criteria.getSaleChannelType() != null)
				buffer.append("AND m.saleChannelType = :saleChannelType ");

			if (criteria.getStartDate() != null)
				buffer.append("AND m.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND m.paymentDate <= :endDate ");

			buffer.append("ORDER BY m.policyNo");

			Query query = em.createQuery(buffer.toString());

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());

			if (criteria.getSaleChannelType() != null)
				query.setParameter("saleChannelType", criteria.getSaleChannelType());

			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			resultList = query.getResultList();

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Group Life Montly Income Report ", pe);
		}
		return resultList;
	}

	@Override
	public List<StudentMontlyIncomeReportDTO> findStudentMontlyIncomeReport(StudentMontlyIncomeReportCriteria criteria) throws DAOException {
		List<StudentMontlyIncomeReportDTO> resultList = new ArrayList<>();
		LocalDate localStartDate = LocalDate.of(criteria.getYear(), criteria.getMonth() + 1, 1);
		LocalDate localEndDate = localStartDate.withDayOfMonth(localStartDate.lengthOfMonth());
		Date startDate = Date.from(localStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(localEndDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		startDate = DateUtils.resetStartDate(startDate);
		endDate = DateUtils.resetEndDate(endDate);

		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + StudentMontlyIncomeReportDTO.class.getName());
			buffer.append("(s.customerName,s.insuredPersonName,s.age,s.policyTerm,s.policyNo,s.fullAddress,s.sumInsured,s.amount,s.paymentType,");
			buffer.append(" s.commission,s.receiptNo,s.paymentDate,s.agentName,s.salePointsName,s.activePolicystartDate,s.activePolicyendDate,s.fromTermToTerm,s.saleChannelType,s.premiumPeriod)");
			buffer.append(" FROM StudentMontlyIncomeReportView s ");
			buffer.append(" WHERE 1=1 ");
			buffer.append("AND s.paymentDate >= :startDate ");
			buffer.append("AND s.paymentDate <= :endDate ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND s.salesPointsId = :salePointId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND s.branchId = :branchId ");

			if (criteria.getSaleChannelType() != null)
				buffer.append("AND s.saleChannelType = :saleChannelType ");

			buffer.append(" ORDER BY s.paymentDate");

			Query query = em.createQuery(buffer.toString());
			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */
			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());

			if (criteria.getSaleChannelType() != null)
				query.setParameter("saleChannelType", criteria.getSaleChannelType());

			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);

			resultList = query.getResultList();

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of StudentLifePolicyReport by StudentMontlyReportCriteria.", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CeoShortTermLifeDTO> findCeoShortEndowLifePolicyMonthlyReport(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<CeoShortTermLifeDTO> resultList = new ArrayList<>();

		LocalDate localStartDate = LocalDate.of(criteria.getRequiredYear(), criteria.getRequiredMonth() + 1, 1);
		LocalDate localEndDate = localStartDate.withDayOfMonth(localStartDate.lengthOfMonth());
		Date startDate = Date.from(localStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(localEndDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		startDate = DateUtils.resetStartDate(startDate);
		endDate = DateUtils.resetEndDate(endDate);
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + CeoShortTermLifeDTO.class.getName());
			buffer.append("(c.policyNo, c.policyTerm,c.paymentType , c.activedPolicyStartDate, c.activedPolicyEndDate, c.suminsured,c.paymentDate)");
			buffer.append("FROM CeoShortEndowLifeMonthlyReportView c");
			buffer.append(" WHERE 1=1 ");
			buffer.append("AND c.paymentDate >= :startDate ");
			buffer.append("AND c.paymentDate <= :endDate ");

			/*
			 * if (criteria.getRequiredMonth() != 0)
			 * buffer.append(" AND c.paymentDate >= :startDate ");
			 * 
			 * if (criteria.getRequiredYear() != 0)
			 * buffer.append("AND c.paymentDate <= :endDate ");
			 */

			if (criteria.getPeriodOfYears() != 0)
				buffer.append("AND  c.policyTerm = :term ");

			buffer.append("ORDER BY c.policyNo");

			Query query = em.createQuery(buffer.toString());

			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("term", criteria.getPeriodOfYears());

			resultList = query.getResultList();

		} catch (NoResultException ne) {
			return new ArrayList<>();
		} catch (PersistenceException pe) {
			throw translate("Failed to find CEO Short Term Life Montly Income Report ", pe);
		}
		return resultList;
	}

}
