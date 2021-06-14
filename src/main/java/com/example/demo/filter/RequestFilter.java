package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.constants.Constants;
import com.example.demo.controller.TweetController;
import com.example.demo.support.Validation;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		String headerUser = request.getHeader(Constants.REQUEST_HEADER_USER);
        
		if (headerUser == null ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.NO_USER);
		}else if(!Validation.isUserValid(headerUser,Constants.USER_VALIDATION_REGEX)) {
			logger.error(headerUser);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, Constants.INVALID_USER);
		}else {
			chain.doFilter(req, res);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}
}
