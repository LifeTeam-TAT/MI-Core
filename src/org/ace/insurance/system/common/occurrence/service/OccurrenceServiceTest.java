package org.ace.insurance.system.common.occurrence.service;

import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.city.service.interfaces.ICityService;
import org.ace.insurance.system.common.occurrence.Occurrence;
import org.ace.insurance.system.common.occurrence.service.interfaces.IOccurrenceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OccurrenceServiceTest {
	private static Logger logger = LogManager.getLogger(OccurrenceServiceTest.class);
	private static IOccurrenceService occurrenceService;
	private static ICityService cityService;

	@BeforeClass
	public static void init() {
		logger.info("OccurrenceServiceTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		occurrenceService = (IOccurrenceService) factory.getBean("OccurrenceService");
		cityService = (ICityService) factory.getBean("CityService");

		logger.info("OccurrenceServiceTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(OccurrenceServiceTest.class.getName());
	}

	@Test
	public void test() {
		City fromCity = cityService.findCityById("ISSYS0050001000000028131032013");
		City toCity = cityService.findCityById("ISSYS005HO000000028221062013");
		Occurrence occurrence = new Occurrence();
		occurrence.setFromCity(fromCity);
		occurrence.setToCity(toCity);
		System.out.println(fromCity.getName());
		occurrence.setDescription(fromCity.getName() + " - " + toCity.getName());
		System.out.println(occurrence.getDescription());
		occurrenceService.addNewOccurrence(occurrence);
		// Occurrence occurrence1 = occurrenceService.findOccurrenceById("11");
		// System.out.println(occurrence1.getFromCity().getName());
		// occurrenceService.deleteOccurrence(occurrence1);
	}
}
