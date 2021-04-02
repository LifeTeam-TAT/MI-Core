package org.ace.java.component;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class DDLGenerator {
	public static void main(String args[]) {
		Map<String, String> persistProperties = new HashMap<String, String>();
		persistProperties.put(PersistenceUnitProperties.DDL_GENERATION, "create-tables");
		persistProperties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, "sql-script");
		persistProperties.put(PersistenceUnitProperties.APP_LOCATION, "D:\\temp");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA", persistProperties);
		EntityManager em = emf.createEntityManager();
	}
}
