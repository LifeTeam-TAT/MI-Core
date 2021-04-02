package org.ace.insurance.report.life.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.farmer.FarmerMonthlyReport;
import org.ace.insurance.report.life.LifePolicyReport;
import org.ace.insurance.report.life.LifePolicyReportCriteria;
import org.ace.insurance.report.life.persistence.interfaces.ILifePolicyReportDAO;
import org.ace.insurance.report.life.view.LifePolicyView;
import org.ace.insurance.report.personalAccident.PersonalAccidentPolicyReport;
import org.ace.insurance.report.personalAccident.PersonalAccidentPolicyView;
import org.ace.insurance.report.shortEndowLife.ShortEndowLifePolicyReport;
import org.ace.insurance.report.shortEndowLife.ShortEndowLifePolicyView;
import org.ace.insurance.report.sportMan.SportManMonthlyReportDTO;
import org.ace.insurance.web.manage.report.shortEndowLife.ShortEndownLifeMonthlyReportDTO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePolicyReportDAO")
public class LifePolicyReportDAO extends BasicDAO implements ILifePolicyReportDAO {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyReport> findLifePolicyReport(LifePolicyReportCriteria lifePolicyReportCriteria, List<String> productIdList) throws DAOException {
		List<LifePolicyReport> result = new ArrayList<LifePolicyReport>();
		List<LifePolicyView> viewList = new ArrayList<LifePolicyView>();
		try {
			StringBuffer query = new StringBuffer();

			query.append("SELECT l FROM LifePolicyView l WHERE l.id is not null ");
			query.append(" AND l.productId IN :productIdList");
			if (lifePolicyReportCriteria.getAgent() != null) {
				query.append(" AND l.agentId = :agentId");
			}

			if (lifePolicyReportCriteria.getCustomer() != null) {
				query.append(" AND l.customerId = :customerId");
			}
			if (lifePolicyReportCriteria.getBranch() != null) {
				query.append(" AND l.branchId = :branchId");
			}
			if (lifePolicyReportCriteria.getPaymentStartDate() != null) {
				lifePolicyReportCriteria.setPaymentStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getPaymentStartDate()));
				query.append(" AND l.paymentDate >= :paymentStartDate");
			}
			if (lifePolicyReportCriteria.getPaymentEndDate() != null) {
				lifePolicyReportCriteria.setPaymentEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getPaymentEndDate()));
				query.append(" AND l.paymentDate <= :paymentEndDate");
			}
			if (lifePolicyReportCriteria.getCommenceStartDate() != null) {
				lifePolicyReportCriteria.setCommenceStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getCommenceStartDate()));
				query.append(" AND l.commenmanceDate >= :commenceStartDate");
			}
			if (lifePolicyReportCriteria.getCommenceEndDate() != null) {
				lifePolicyReportCriteria.setCommenceEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getCommenceEndDate()));
				query.append(" AND l.commenmanceDate <= :commenceEndDate");
			}
			if (lifePolicyReportCriteria.getProposaltype() != null) {
				query.append(" AND l.status = :status");
			}

			query.append(" order by l.branchName, l.policyNo");

			Query q = em.createQuery(query.toString());
			q.setParameter("productIdList", productIdList);
			if (lifePolicyReportCriteria.getAgent() != null) {
				q.setParameter("agentId", lifePolicyReportCriteria.getAgent().getId());
			}
			if (lifePolicyReportCriteria.getCustomer() != null) {
				q.setParameter("customerId", lifePolicyReportCriteria.getCustomer().getId());
			}
			if (lifePolicyReportCriteria.getBranch() != null) {
				q.setParameter("branchId", lifePolicyReportCriteria.getBranch().getId());
			}
			if (lifePolicyReportCriteria.getPaymentStartDate() != null) {
				lifePolicyReportCriteria.setPaymentStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getPaymentStartDate()));
				q.setParameter("paymentStartDate", lifePolicyReportCriteria.getPaymentStartDate());
			}
			if (lifePolicyReportCriteria.getPaymentEndDate() != null) {
				lifePolicyReportCriteria.setPaymentEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getPaymentEndDate()));
				q.setParameter("paymentEndDate", lifePolicyReportCriteria.getPaymentEndDate());
			}
			if (lifePolicyReportCriteria.getCommenceStartDate() != null) {
				lifePolicyReportCriteria.setCommenceStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getCommenceStartDate()));
				q.setParameter("commenceStartDate", lifePolicyReportCriteria.getCommenceStartDate());
			}
			if (lifePolicyReportCriteria.getCommenceEndDate() != null) {
				lifePolicyReportCriteria.setCommenceEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getCommenceEndDate()));
				q.setParameter("commenceEndDate", lifePolicyReportCriteria.getCommenceEndDate());
			}
			if (lifePolicyReportCriteria.getProposaltype() != null) {
				q.setParameter("status", lifePolicyReportCriteria.getProposaltype().getLabel());
			}

			viewList = q.getResultList();
			em.flush();
			if (viewList != null) {
				for (LifePolicyView view : viewList) {
					result.add(new LifePolicyReport(view));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyReport by lifePolicyReportCriteria.", pe);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PersonalAccidentPolicyReport> findPersonalAccidentPolicyReport(LifePolicyReportCriteria lifePolicyReportCriteria) throws DAOException {
		List<PersonalAccidentPolicyReport> result = new ArrayList<PersonalAccidentPolicyReport>();
		List<PersonalAccidentPolicyView> viewList = null;
		try {
			StringBuffer query = new StringBuffer();

			query.append("SELECT p FROM PersonalAccidentPolicyView p WHERE p.policyId is not null ");

			if (lifePolicyReportCriteria.getAgent() != null) {
				query.append(" AND p.agentId = :agentId");
			}

			if (lifePolicyReportCriteria.getCustomer() != null) {
				query.append(" AND p.customerId = :customerId");
			}
			if (lifePolicyReportCriteria.getBranch() != null) {
				query.append(" AND p.branchId = :branchId");
			}
			if (lifePolicyReportCriteria.getOrganization() != null) {
				query.append(" AND p.organizationId =:organizationId");
			}
			if (lifePolicyReportCriteria.getPaymentStartDate() != null) {
				lifePolicyReportCriteria.setPaymentStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getPaymentStartDate()));
				query.append(" AND p.paymentDate >= :paymentStartDate");
			}
			if (lifePolicyReportCriteria.getPaymentEndDate() != null) {
				lifePolicyReportCriteria.setPaymentEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getPaymentEndDate()));
				query.append(" AND p.paymentDate <= :paymentEndDate");
			}
			if (lifePolicyReportCriteria.getCommenceStartDate() != null) {
				lifePolicyReportCriteria.setCommenceStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getCommenceStartDate()));
				query.append(" AND p.commenmanceDate >= :commenceStartDate");
			}
			if (lifePolicyReportCriteria.getCommenceEndDate() != null) {
				lifePolicyReportCriteria.setCommenceEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getCommenceEndDate()));
				query.append(" AND p.commenmanceDate <= :commenceEndDate");
			}
			if (lifePolicyReportCriteria.getProduct() != null) {
				query.append(" AND p.productId =:productId");
			}
			query.append(" order by p.branchId, p.policyNo");

			Query q = em.createQuery(query.toString());

			if (lifePolicyReportCriteria.getAgent() != null) {
				q.setParameter("agentId", lifePolicyReportCriteria.getAgent().getId());
			}
			if (lifePolicyReportCriteria.getCustomer() != null) {
				q.setParameter("customerId", lifePolicyReportCriteria.getCustomer().getId());
			}
			if (lifePolicyReportCriteria.getBranch() != null) {
				q.setParameter("branchId", lifePolicyReportCriteria.getBranch().getId());
			}
			if (lifePolicyReportCriteria.getOrganization() != null) {
				q.setParameter("organizationId", lifePolicyReportCriteria.getOrganization().getId());
			}
			if (lifePolicyReportCriteria.getPaymentStartDate() != null) {
				lifePolicyReportCriteria.setPaymentStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getPaymentStartDate()));
				q.setParameter("paymentStartDate", lifePolicyReportCriteria.getPaymentStartDate());
			}
			if (lifePolicyReportCriteria.getPaymentEndDate() != null) {
				lifePolicyReportCriteria.setPaymentEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getPaymentEndDate()));
				q.setParameter("paymentEndDate", lifePolicyReportCriteria.getPaymentEndDate());
			}
			if (lifePolicyReportCriteria.getCommenceStartDate() != null) {
				lifePolicyReportCriteria.setCommenceStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getCommenceStartDate()));
				q.setParameter("commenceStartDate", lifePolicyReportCriteria.getCommenceStartDate());
			}
			if (lifePolicyReportCriteria.getCommenceEndDate() != null) {
				lifePolicyReportCriteria.setCommenceEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getCommenceEndDate()));
				q.setParameter("commenceEndDate", lifePolicyReportCriteria.getCommenceEndDate());
			}
			if (lifePolicyReportCriteria.getProduct() != null) {
				q.setParameter("productId", lifePolicyReportCriteria.getProduct().getId());
			}
			viewList = q.getResultList();
			em.flush();
			if (viewList != null) {
				for (PersonalAccidentPolicyView view : viewList) {
					result.add(new PersonalAccidentPolicyReport(view));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of PersonalAccidentPolicyReport by lifePolicyReportCriteria.", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<FarmerMonthlyReport> findFarmerMonthlyReport(MonthlyReportCriteria lifePolicyReportCriteria) throws DAOException {
		List<FarmerMonthlyReport> resultList = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW org.ace.insurance.report.farmer.FarmerMonthlyReport(f.id, f.policyNo, f.insuredPersonName, f.address, f.sumInsured, f.premium,"
					+ " f.commission, f.cashReceiptNoAndPaymentDate, f.agentNameAndCode) FROM FarmerMonthlyView f"
					+ " WHERE f.policyNo IS NOT NULL AND f.activedPolicyStartDate >= :startDate AND f.activedPolicyStartDate <= :endDate");
			if (lifePolicyReportCriteria.getBranch() != null) {
				buffer.append(" AND f.branchId = :branchId");
			}

			Query query = em.createQuery(buffer.toString());
			query.setParameter("startDate", Utils.getStartDate(lifePolicyReportCriteria.getYear(), lifePolicyReportCriteria.getMonth()));
			query.setParameter("endDate", Utils.getEndDate(lifePolicyReportCriteria.getYear(), lifePolicyReportCriteria.getMonth()));

			if (lifePolicyReportCriteria.getBranch() != null) {
				query.setParameter("branchId", lifePolicyReportCriteria.getBranch().getId());
			}
			resultList = query.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find farmer monthly report.", pe);
		}
		return resultList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ShortEndowLifePolicyReport> findShortEndowLifePolicyReport(LifePolicyReportCriteria lifePolicyReportCriteria) throws DAOException {
		List<ShortEndowLifePolicyReport> result = new ArrayList<ShortEndowLifePolicyReport>();
		List<ShortEndowLifePolicyView> viewList = null;
		try {
			StringBuffer query = new StringBuffer();

			query.append("SELECT p FROM ShortEndowLifePolicyView p WHERE p.policyId is not null ");
			if (lifePolicyReportCriteria.getAgent() != null) {
				query.append(" AND p.agentId = :agentId");
			}
			if (lifePolicyReportCriteria.getCustomer() != null) {
				query.append(" AND p.customerId = :customerId");
			}
			if (lifePolicyReportCriteria.getBranch() != null) {
				query.append(" AND p.branchId = :branchId");
			}
			if (lifePolicyReportCriteria.getOrganization() != null) {
				query.append(" AND p.organizationId =:organizationId");
			}
			if (lifePolicyReportCriteria.getPaymentStartDate() != null) {
				lifePolicyReportCriteria.setPaymentStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getPaymentStartDate()));
				query.append(" AND p.paymentDate >= :paymentStartDate");
			}
			if (lifePolicyReportCriteria.getPaymentEndDate() != null) {
				lifePolicyReportCriteria.setPaymentEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getPaymentEndDate()));
				query.append(" AND p.paymentDate <= :paymentEndDate");
			}
			if (lifePolicyReportCriteria.getCommenceStartDate() != null) {
				lifePolicyReportCriteria.setCommenceStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getCommenceStartDate()));
				query.append(" AND p.commenmanceDate >= :commenceStartDate");
			}
			if (lifePolicyReportCriteria.getCommenceEndDate() != null) {
				lifePolicyReportCriteria.setCommenceEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getCommenceEndDate()));
				query.append(" AND p.commenmanceDate <= :commenceEndDate");
			}
			query.append(" order by p.branchId, p.policyNo");

			Query q = em.createQuery(query.toString());

			if (lifePolicyReportCriteria.getAgent() != null) {
				q.setParameter("agentId", lifePolicyReportCriteria.getAgent().getId());
			}
			if (lifePolicyReportCriteria.getCustomer() != null) {
				q.setParameter("customerId", lifePolicyReportCriteria.getCustomer().getId());
			}
			if (lifePolicyReportCriteria.getBranch() != null) {
				q.setParameter("branchId", lifePolicyReportCriteria.getBranch().getId());
			}
			if (lifePolicyReportCriteria.getOrganization() != null) {
				q.setParameter("organizationId", lifePolicyReportCriteria.getOrganization().getId());
			}
			if (lifePolicyReportCriteria.getPaymentStartDate() != null) {
				lifePolicyReportCriteria.setPaymentStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getPaymentStartDate()));
				q.setParameter("paymentStartDate", lifePolicyReportCriteria.getPaymentStartDate());
			}
			if (lifePolicyReportCriteria.getPaymentEndDate() != null) {
				lifePolicyReportCriteria.setPaymentEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getPaymentEndDate()));
				q.setParameter("paymentEndDate", lifePolicyReportCriteria.getPaymentEndDate());
			}
			if (lifePolicyReportCriteria.getCommenceStartDate() != null) {
				lifePolicyReportCriteria.setCommenceStartDate(Utils.resetStartDate(lifePolicyReportCriteria.getCommenceStartDate()));
				q.setParameter("commenceStartDate", lifePolicyReportCriteria.getCommenceStartDate());
			}
			if (lifePolicyReportCriteria.getCommenceEndDate() != null) {
				lifePolicyReportCriteria.setCommenceEndDate(Utils.resetEndDate(lifePolicyReportCriteria.getCommenceEndDate()));
				q.setParameter("commenceEndDate", lifePolicyReportCriteria.getCommenceEndDate());
			}
			viewList = q.getResultList();
			em.flush();
			if (viewList != null) {
				for (ShortEndowLifePolicyView view : viewList) {
					result.add(new ShortEndowLifePolicyReport(view));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ShortEndowLifePolicyReport by lifePolicyReportCriteria.", pe);
		}
		RegNoSorter<ShortEndowLifePolicyReport> regNoSorter = new RegNoSorter<ShortEndowLifePolicyReport>(result);
		return regNoSorter.getSortedList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ShortEndownLifeMonthlyReportDTO> findShortEndowMonthlyReport(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<ShortEndownLifeMonthlyReportDTO> resultList = new ArrayList<ShortEndownLifeMonthlyReportDTO>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + ShortEndownLifeMonthlyReportDTO.class.getName());
			buffer.append(
					"(s.insuredPersonName,s.age,s.policyTerm,s.policyNo,s.residentAddress,s.districtName,s.provinceName,s.sumInsured,s.amount,s.paymentType,");
			buffer.append(" s.commission,s.receiptNo,s.paymentDate,s.agentName,s.liscenseNo,s.salePointsName,s.activedPolicyStartDate,s.activedPolicyEndDate,s.fromTermToTerm,s.saleChannelType)");
			buffer.append(" FROM ShortEndowLifeMonthlyReportView s ");
			buffer.append(" WHERE 1=1 ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND s.salesPointsId = :salePointId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND s.branchId = :branchId ");
			
			if (criteria.getSaleChannelType() != null)
				buffer.append("AND s.saleChannelType = :saleChannelType ");

			if (criteria.getStartDate() != null)
				buffer.append("AND s.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND s.paymentDate <= :endDate ");

			buffer.append(" ORDER BY s.policyNo");

			Query query = em.createQuery(buffer.toString());

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());
			
			if (criteria.getSaleChannelType() != null )
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
			throw translate("Failed to find all of ShortEndowLifePolicyReport by lifePolicyReportCriteria.", pe);
		}
		return resultList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ShortEndownLifeMonthlyReportDTO> findPublicLifeMonthlyReport(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<ShortEndownLifeMonthlyReportDTO> resultList = new ArrayList<ShortEndownLifeMonthlyReportDTO>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + ShortEndownLifeMonthlyReportDTO.class.getName());
			buffer.append("(s.insuredPersonName,s.age,s.policyTerm,s.policyNo,s.residentAddress,s.districtName,s.provinceName,s.sumInsured,s.amount,s.paymentType,");
			buffer.append(" s.commission,s.receiptNo,s.paymentDate,s.agentName,s.liscenseNo,s.salePointsName,s.activePolicyStartDate,s.activePolicyEndDate,s.fromTermToTerm,s.saleChannelType)");
			buffer.append(" FROM PublicLifeMonthlyReportView s ");
			buffer.append(" WHERE 1=1 ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND s.salesPointsId = :salePointId ");
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND s.branchId = :branchId ");
			
			if (criteria.getSaleChannelType()!= null)
				buffer.append("AND s.saleChannelType = :saleChannelType ");

			if (criteria.getStartDate() != null)
				buffer.append("AND s.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND s.paymentDate <= :endDate ");

			buffer.append(" ORDER BY s.policyNo");

			Query query = em.createQuery(buffer.toString());
			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */

			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());
			
			if (criteria.getSaleChannelType() != null )
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
			throw translate("Failed to find all of PublicLifePolicyReport by lifePolicyReportCriteria.", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SportManMonthlyReportDTO> findSportManMonthlyReport(MonthlyIncomeReportCriteria criteria) throws DAOException {
		List<SportManMonthlyReportDTO> resultList = new ArrayList<SportManMonthlyReportDTO>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + SportManMonthlyReportDTO.class.getName());
			buffer.append("(s.insuredPersonName,s.residentAddress,s.districtName,s.provinceName,s.policyNo,s.typeOfSport,s.sumInsured,s.amount,");
			buffer.append(" s.receiptNo,s.paymentDate,s.commission,s.agentName,s.liscenseNo,s.salePointsName,s.fromDateToDate,s.fromTermToTerm,s.saleChannelType)");
			buffer.append(" FROM SportManMonthlyReportView s ");
			buffer.append(" WHERE 1=1 ");

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty())
			 * buffer.append("AND s.salesPointsId = :salePointId ");
			 */
			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				buffer.append("AND s.branchId = :branchId ");
			
			if (criteria.getSaleChannelType() != null)
				buffer.append("AND s.saleChannelType = :saleChannelType ");

			if (criteria.getStartDate() != null)
				buffer.append("AND s.paymentDate >= :startDate ");

			if (criteria.getEndDate() != null)
				buffer.append("AND s.paymentDate <= :endDate ");

			buffer.append(" ORDER BY s.policyNo");

			Query query = em.createQuery(buffer.toString());

			/*
			 * if (criteria.getSalePointName() != null &&
			 * !criteria.getSalePointId().isEmpty()) query.setParameter("salePointId",
			 * criteria.getSalePointId());
			 */
			if (criteria.getBranchId() != null && !criteria.getBranchId().isEmpty())
				query.setParameter("branchId", criteria.getBranchId());
			
			if (criteria.getSaleChannelType() != null )
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
			throw translate("Failed to find all of SportManMonthlyReport by lifePolicyReportCriteria.", pe);
		}
		return resultList;
	}
	
	

}
