package org.ace.insurance.report.common.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.WorkFlowStatusCriteria;
import org.ace.insurance.report.common.WorkFlowStatusReport;
import org.ace.insurance.report.common.persistence.interfaces.IWorkFlowReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository("WorkFlowReportDAO")
public class WorkFlowReportDAO extends BasicDAO implements IWorkFlowReportDAO {

	private class ReportItemType {
		public static final String MOTOR = "MOTOR";
		public static final String FIRE = "FIRE";
		public static final String LIFE = "LIFE";
	}

	private class Queries {
		public static final String MOTOR_WORKFLOW_STATUS = "SELECT COUNT(x.id), x.workflowTask FROM WorkFlow x WHERE x.referenceType = org.ace.insurance.common.ReferenceType.MOTOR_PROPOSAL GROUP BY x.workflowTask";
		public static final String FIRE_WORKFLOW_STATUS = "SELECT COUNT(x.id), x.workflowTask FROM WorkFlow x WHERE x.referenceType = org.ace.insurance.common.ReferenceType.FIRE_PROPOSAL GROUP BY x.workflowTask";
		public static final String LIFE_WORKFLOW_STATUS = "SELECT COUNT(x.id), x.workflowTask FROM WorkFlow x WHERE x.referenceType = org.ace.insurance.common.ReferenceType.LIFE_PROPOSAL GROUP BY x.workflowTask";
		public static final String MOTOR_WORKFLOW_DETAILS_STATUS = "SELECT t1.createdUser.name, t1.responsiblePerson.name, t2.proposalNo, t1.createdDate FROM WorkFlow t1, MotorProposal t2 WHERE t1.workflowTask = :workflowTask AND t1.referenceNo = t2.id";
		public static final String FIRE_WORKFLOW_DETAILS_STATUS = "SELECT t1.createdUser.name, t1.responsiblePerson.name, t2.proposalNo, t1.createdDate FROM WorkFlow t1, FireProposal t2 WHERE t1.workflowTask = :workflowTask AND t1.referenceNo = t2.id";
		public static final String LIFE_WORKFLOW_DETAILS_STATUS = "SELECT t1.createdUser.name, t1.responsiblePerson.name, t2.proposalNo, t1.createdDate FROM WorkFlow t1, LifeProposal t2 WHERE t1.workflowTask = :workflowTask AND t1.referenceNo = t2.id";
	}

	private void populateData(FinancialReport summeryReport, String reportItemType, List<Object> objList) {
		Map<WorkflowTask, Double> tmpMap = new HashMap<WorkflowTask, Double>();
		tmpMap.put(WorkflowTask.SURVEY, 0.0);
		tmpMap.put(WorkflowTask.APPROVAL, 0.0);
		tmpMap.put(WorkflowTask.INFORM, 0.0);
		tmpMap.put(WorkflowTask.CONFIRMATION, 0.0);
		tmpMap.put(WorkflowTask.PAYMENT, 0.0);
		tmpMap.put(WorkflowTask.ISSUING, 0.0);

		List<WorkflowTask> xSeriesList = new ArrayList<WorkflowTask>();
		xSeriesList.add(WorkflowTask.SURVEY);
		xSeriesList.add(WorkflowTask.APPROVAL);
		xSeriesList.add(WorkflowTask.INFORM);
		xSeriesList.add(WorkflowTask.CONFIRMATION);
		xSeriesList.add(WorkflowTask.PAYMENT);
		xSeriesList.add(WorkflowTask.ISSUING);

		for (Object obj : objList) {
			Object[] objArray = (Object[]) obj;
			double rate = Double.parseDouble(objArray[0].toString());
			WorkflowTask status = (WorkflowTask) objArray[1];
			tmpMap.put(status, rate);
		}
		for (WorkflowTask xSeries : xSeriesList) {
			summeryReport.addReportItem(reportItemType, xSeries.toString(), tmpMap.get(xSeries));
		}
	}

	public FinancialReport findAllWorkFlowStatus() {
		FinancialReport summeryReport = new FinancialReport();
		try {
			Query motorQuery = em.createQuery(Queries.MOTOR_WORKFLOW_STATUS);
			populateData(summeryReport, ReportItemType.MOTOR, motorQuery.getResultList());

			Query fireQuery = em.createQuery(Queries.FIRE_WORKFLOW_STATUS);
			populateData(summeryReport, ReportItemType.FIRE, fireQuery.getResultList());

			Query lifeQuery = em.createQuery(Queries.LIFE_WORKFLOW_STATUS);
			populateData(summeryReport, ReportItemType.LIFE, lifeQuery.getResultList());
		} catch (PersistenceException pe) {
			throw translate("Failed to find WorkFlowStatus of All Proposal Status Report.", pe);
		}
		return summeryReport;
	}

	public List<WorkFlowStatusReport> findStatusReport(WorkFlowStatusCriteria criteria) {
		List<WorkFlowStatusReport> result = new ArrayList<WorkFlowStatusReport>();
		try {
			Query query = null;
			if (criteria.getWorkFlowType().equals(WorkFlowType.MOTOR)) {
				query = em.createQuery(Queries.MOTOR_WORKFLOW_DETAILS_STATUS);
			} else if (criteria.getWorkFlowType().equals(WorkFlowType.FIRE)) {
				query = em.createQuery(Queries.FIRE_WORKFLOW_DETAILS_STATUS);
			} else if (criteria.getWorkFlowType().equals(WorkFlowType.LIFE)) {
				query = em.createQuery(Queries.LIFE_WORKFLOW_DETAILS_STATUS);
			}
			query.setParameter("workflowTask", criteria.getWorkFlowTask());
			List<Object> motorResult = query.getResultList();
			for (Object obj : motorResult) {
				Object[] objArray = (Object[]) obj;
				result.add(new WorkFlowStatusReport((String) objArray[0], (String) objArray[1], (String) objArray[2], criteria.getWorkFlowType(), (Date) objArray[3]));
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find details WorkFlow Status of " + criteria.getWorkFlowType() + " Proposal.", pe);
		}
		return result;
	}

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		String motorQueryString = "SELECT t1.responsiblePerson.name, t1.createdUser.name, t2.proposalNo FROM WorkFlow t1, FireProposal t2 WHERE t1.workflowTask = :workflowTask AND t1.referenceNo = t2.id";
		Query q = em.createQuery(motorQueryString);
		q.setParameter("workflowTask", WorkflowTask.SURVEY);
		List<Object> result = q.getResultList();
		for (Object obj : result) {
			Object[] objArray = (Object[]) obj;
			System.out.println(objArray[0] + " \t\t: " + objArray[1] + "  \t\t: " + objArray[2]);
		}
		em.close();
	}
}
