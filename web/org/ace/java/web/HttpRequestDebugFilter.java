package org.ace.java.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HttpRequestDebugFilter implements Filter {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();
		chain.doFilter(request, response);
		stopWatch.stop();
		logger.info(">>>>>>>>>>>>>>>>>>> " + stopWatch.getTime() + " milli seconds");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
