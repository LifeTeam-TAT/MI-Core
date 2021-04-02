package org.ace.java.web.upload;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Servlet Filter implementation class MultipartFilter
 */
public class MultipartFilter implements Filter {
	private long maxFileSize = 0;
	private String repositoryPath;

	/**
	 * Default constructor.
	 */
	public MultipartFilter() {
	}

	public void init(FilterConfig config) throws ServletException {
		String maxFileSize = config.getInitParameter("maximumFileSize");
		if (maxFileSize != null) {
			if (!maxFileSize.matches("^\\d+$")) {
				throw new ServletException("MultipartFilter parameter 'maxFileSize' is not numeric!");
			}
			this.maxFileSize = Long.parseLong(maxFileSize);
		}
		repositoryPath = config.getInitParameter("repositoryPath");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (isMultipart(httpRequest)) {
			try {
				// instantiate the MultipartRequest
				MultipartRequest mr;
				if (repositoryPath == null) {
					mr = new MultipartRequest(request);
				} else {
					mr = new MultipartRequest(request, repositoryPath);
				}
				request = MultipartFilter.wrapRequest(httpRequest, mr.getParameterMap());
			} catch (UploadedFileException e) {
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	private static HttpServletRequest wrapRequest(HttpServletRequest request, final Map<String, String[]> parameterMap) {
		return new HttpServletRequestWrapper(request) {
			// inner methods passed as parameters
			public Map<String, String[]> getParameterMap() {
				return parameterMap;
			}

			public String[] getParameterValues(String name) {
				return (String[]) parameterMap.get(name);
			}

			public String getParameter(String name) {
				String[] params = getParameterValues(name);
				if (params == null)
					return null;
				return params[0];
			}

			public Enumeration<String> getParameterNames() {
				return Collections.enumeration(parameterMap.keySet());
			}
		};
	}

	private boolean isMultipart(HttpServletRequest request) {
		if (request.getContentType() != null)
			if (request.getContentType().toLowerCase().indexOf("multipart/") != -1)
				return true;
		return false;
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
