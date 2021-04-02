package org.ace.java.component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBMigrate {
/*
SELECT ID, REGISTRATIONNO, CHASSISNO, ENGINENO, VEHCODENO, STARTDATE from MPROPOSAL_VEHICLE_LINK ORDER BY STARTDATE ASC
SELECT ID, REGISTRATIONNO, CHASSISNO, VEHCODENO, STARTDATE from MPOLICY_VEHICLE_LINK ORDER BY STARTDATE ASC

SELECT ID, IDNO, IDTYPE, INPERSONCODENO, STARTDATE FROM LIFEPROPOSAL_INSUREDPERSON_LINK ORDER BY STARTDATE ASC
SELECT ID, IDNO, IDTYPE, INPERSONCODENO, STARTDATE FROM LIFEPOLICY_INSUREDPERSON_LINK ORDER BY STARTDATE ASC

SELECT ID, BUILDINGCLASSID, BUILDINGOCCUPATIONID, BUILDINGADDRESS, BUILDINGCODENO  FROM BUILDINGINFO
SELECT ID, BUILDINGCLASSID, BUILDINGOCCUPATIONID, BUILDINGADDRESS, BUILDINGCODENO FROM FIREPOLICY_BUILDINGINFO_LINK

SELECT GENERATEITEM, MAXVALUE FROM CUSTOM_ID_GEN WHERE GENERATEITEM='MOTOR_VEHICLE_CODE_NO'
SELECT GENERATEITEM, MAXVALUE FROM CUSTOM_ID_GEN WHERE GENERATEITEM='LIFE_INSUREDPERSON_CODENO_ID_GEN'
SELECT GENERATEITEM, MAXVALUE FROM CUSTOM_ID_GEN WHERE GENERATEITEM='FIRE_BUILDING_ID_GEN'
*/
	public static String formatId(String id, String prefix, int serialLength) {
		if (prefix != null && !id.startsWith(prefix)) {
			int length = id.length();
			for (; (serialLength - length)  > 0; length++) {
				id = '0' + id;
			}
			id = prefix + "/2408/" + id + "/HO";
		}
		return id;
	}
	
	public static void main(String args[]) {
		Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://acehawksvr;databaseName=ggip_production;selectMethod=cursor", "sa", "sa");
            connection.setAutoCommit(false);
            Statement motorStatement = connection.createStatement();
            ResultSet motorResultSet = motorStatement.executeQuery("SELECT ID, ENGINENO, CHASSISNO FROM MPROPOSAL_VEHICLE_LINK ORDER BY STARTDATE ASC");
            int m = 1;
            while (motorResultSet.next()) {
            	String id = motorResultSet.getString("ID");
            	String engineNo = motorResultSet.getString("ENGINENO");
            	String chassisNo = motorResultSet.getString("CHASSISNO");
            	String vehCode = formatId(m + "", "VEH", 10);
            	Statement st =  connection.createStatement();
            	st.execute("UPDATE MPROPOSAL_VEHICLE_LINK SET VEHCODENO='" + vehCode + "' WHERE ID='" + id + "'");
            	st.execute("UPDATE MPOLICY_VEHICLE_LINK SET VEHCODENO='" + vehCode + "' WHERE ENGINENO='" + engineNo + "' AND CHASSISNO='" + chassisNo + "'");
            	m++;
            }
            System.out.println("Motor Finished..." + m);
            
            Statement lifeStatement = connection.createStatement();
            ResultSet lifeResultSet = lifeStatement.executeQuery("SELECT ID, IDNO, IDTYPE, INPERSONCODENO FROM LIFEPROPOSAL_INSUREDPERSON_LINK ORDER BY STARTDATE ASC");
            int l = 1;
            while (lifeResultSet.next()) {
            	String id = lifeResultSet.getString("ID");
            	String idno = lifeResultSet.getString("IDNO");
            	String idType = lifeResultSet.getString("IDTYPE");
            	String insuPersonCode = formatId(l + "", "INP", 10);
            	Statement st =  connection.createStatement();
            	st.execute("UPDATE LIFEPROPOSAL_INSUREDPERSON_LINK SET INPERSONCODENO='" + insuPersonCode + "' WHERE ID='" + id + "'");
            	st.execute("UPDATE LIFEPOLICY_INSUREDPERSON_LINK SET INPERSONCODENO='" + insuPersonCode + "' WHERE IDNO='" + idno + "' AND IDTYPE='" + idType + "'");
            	l++;
            }
            System.out.println("Life Finished..." + l);
            
            
            //to FIXME this query by THK
            Statement fireStatement = connection.createStatement();
            ResultSet fireResultSet = fireStatement.executeQuery("SELECT ID, BUILDINGCLASSID, BUILDINGOCCUPATIONID, BUILDINGADDRESS FROM BUILDINGINFO");
            int f = 1;
            while (fireResultSet.next()) {
            	String id = fireResultSet.getString("ID");
            	String classId = fireResultSet.getString("BUILDINGCLASSID");
            	String ocpId = fireResultSet.getString("BUILDINGOCCUPATIONID");
            	String bdAddress = fireResultSet.getString("BUILDINGADDRESS");
            	String bdCode = formatId(f + "", "FBD", 10);
            	Statement st =  connection.createStatement();
            	st.execute("UPDATE BUILDINGINFO SET BUILDINGCODENO='" + bdCode + "' WHERE ID='" + id + "'");
            	st.execute("UPDATE FIREPOLICY_BUILDINGINFO_LINK SET BUILDINGCODENO='" + bdCode + "' WHERE BUILDINGCLASSID='" + classId + "' AND BUILDINGOCCUPATIONID='" + ocpId + "' AND BUILDINGADDRESS='" + bdAddress + "'");
            	f++;
            }
            
            Statement cusIdStatement = connection.createStatement();
            cusIdStatement.execute("UPDATE CUSTOM_ID_GEN SET MAXVALUE=" + (m-1) + " WHERE GENERATEITEM='MOTOR_VEHICLE_CODE_NO'");
            cusIdStatement.execute("UPDATE CUSTOM_ID_GEN SET MAXVALUE=" + (l-1) + " WHERE GENERATEITEM='LIFE_INSUREDPERSON_CODENO_ID_GEN'");
            cusIdStatement.execute("UPDATE CUSTOM_ID_GEN SET MAXVALUE=" + (f-1) + " WHERE GENERATEITEM='FIRE_BUILDING_ID_GEN'");
            System.out.println("CUSTOM_ID_GEN finished...");
            
            System.out.println("Fire Finished..." + f);
            List<String> queries = new ArrayList<String>();
            queries.add("DELETE FROM LIFEPOLICYHISTORY");
            queries.add("DELETE FROM MPOLICY_HISTORY_ATTACH_LINK");
            queries.add("DELETE FROM MPOLICY_HISTORY_VEHICLE_KEYFACTORVALUE_LINK");
            queries.add("DELETE FROM MPOLICY_HISTORY_VEHICLE_LINK");
            queries.add("DELETE FROM MOTORPOLICY_HISTORY");
            queries.add("DELETE FROM MPROPOSAL_ATTACH_HISTORY");
            queries.add("DELETE FROM MOTORPROPOSAL_HISTORY");
            queries.add("DELETE FROM MPROPOSAL_DRIVER_HISTORY");
            queries.add("DELETE FROM MPROPOSAL_VEHICLE_ADDON_HISTORY");
            queries.add("DELETE FROM MPROPOSAL_VEHICLE_ATTACH_HISTORY");
            queries.add("DELETE FROM MPROPOSAL_VEHICLE_KEYFACTORVALUE_HISTORY");
            queries.add("DELETE FROM MPROPOSAL_VEHICLE_HISTORY");
            queries.add("DELETE FROM MOTORPOLICY_HISTORY_DRIVERLIST_LINK");
            queries.add("DELETE FROM MPOLICY_HISTORY_VEHICLE_ATTACH_LINK");
            queries.add("DELETE FROM MPOLICY_HISTORY_VEHICLE_ADDON_LINK");
            queries.add("DELETE FROM LPOLICY_INSU_BENEFI_HISTORY");
            queries.add("DELETE FROM LPOLICY_INSUPERSON_HISTORY");
            queries.add("DELETE FROM LPOLICY_INSU_ADDON_HISTORY_LINK");
            queries.add("DELETE FROM LPOLICY_INSU_KEYFACTOR_HISTORY");
            queries.add("DELETE FROM LPOLICYHISTORY_ATTACH_LINK");
            Statement delSt =  connection.createStatement();
            for(String q : queries) {
            	delSt.execute(q);
            }
            System.out.println("Clear History....");
            connection.commit();
        } catch (Exception e) {
        	try {
        		connection.rollback();
				connection.close();
			} catch (SQLException e1) {
			}
            e.printStackTrace();
        }		
	}
}
