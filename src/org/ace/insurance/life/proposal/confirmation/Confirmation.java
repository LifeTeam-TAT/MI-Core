package org.ace.insurance.life.proposal.confirmation;

import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Confirmation {

private Logger logger = LogManager.getLogger(this.getClass());
	
	public static void main(String args[]){
		new Confirmation().findCustomer("000011");
	}

	public Customer findCustomer(String id) {
		Customer customer = null;
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        BeanFactory factory = context;
        CustomerService customerService = (CustomerService)factory.getBean("CustomerService");
        customer = customerService.findCustomerById(id);
        logger.info("Found id....." + id);
		return customer;
	}
}
