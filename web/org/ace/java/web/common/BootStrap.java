/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ace.java.web.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class BootStrap implements ServletContextListener {
	private static Logger logger = LogManager.getLogger(BootStrap.class);

	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Data Initialization has been started...................");
		// System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
		// "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		// ApplicationContext appContext = new
		// ClassPathXmlApplicationContext("spring-beans.xml");
		// FDPolicySchedularService schedular = (FDPolicySchedularService)
		// appContext.getBean("FDPolicySchedularService");
		// schedular.start();
		logger.info("Data Initilization has been fiished......................");
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}
