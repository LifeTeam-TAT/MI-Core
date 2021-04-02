package org.ace.insurance.report.config.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.report.config.AgentJobConfigLoader;
import org.ace.insurance.report.config.persistence.interfaces.IReportConfigDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This interface serves as the DAO to configure report by access SQL Agent Job.
 * 
 * @author HS
 * @since 1.0.0
 * @date 2015/03/18
 */
@Repository("ReportConfigDAO")
public class ReportConfigDAO extends BasicDAO implements IReportConfigDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void configReport(List<String> reportType) throws DAOException, InterruptedException {
		try {
			Query query = null;
			List<String> jobNames = null;
			for (String key : reportType) {
				jobNames = AgentJobConfigLoader.getJobNames(key);
				for (String job : jobNames) {
					query = em.createNativeQuery("EXEC msdb.dbo.sp_start_job N'" + job + "';");
					query.executeUpdate();
					em.flush();
					Thread.sleep(3000);
					jobFinish(job);
				}
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to Config Report", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void jobFinish(String job) throws DAOException, InterruptedException {
		try {
			Query query = null;
			StringBuffer buffer = new StringBuffer();

			buffer.append("IF OBJECT_ID('tempdb.dbo.RunningJobs') IS NOT NULL DROP TABLE tempdb.dbo.RunningJobs ");
			query = em.createNativeQuery(buffer.toString());
			query.executeUpdate();

			buffer = new StringBuffer();
			buffer.append("CREATE TABLE tempdb.dbo.RunningJobs (Job_ID UNIQUEIDENTIFIER, Last_Run_Date INT,  Last_Run_Time INT, "
					+ " Next_Run_Date INT, Next_Run_Time INT, Next_Run_Schedule_ID INT, Requested_To_Run INT, "
					+ " Request_Source INT, Request_Source_ID VARCHAR(100), Running INT, Current_Step INT,Current_Retry_Attempt INT, State INT ) ");
			query = em.createNativeQuery(buffer.toString());
			query.executeUpdate();

			buffer = new StringBuffer();
			buffer.append("INSERT INTO tempdb.dbo.RunningJobs EXEC master.dbo.xp_sqlagent_enum_jobs 1,garbage");
			query = em.createNativeQuery(buffer.toString());
			query.executeUpdate();

			buffer = new StringBuffer();
			buffer.append(" SELECT name FROM tempdb.dbo.RunningJobs JSR JOIN msdb.dbo.sysjobs ON JSR.Job_ID=sysjobs.job_id  ");
			buffer.append(" WHERE Running=1 AND name='" + job + "' ");
			query = em.createNativeQuery(buffer.toString());
			em.flush();
			List<Object> objects = query.getResultList();
			if (!objects.isEmpty()) {
				Thread.sleep(5000);
				jobFinish(job);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to examine job finished or not.", pe);
		}
	}
}
