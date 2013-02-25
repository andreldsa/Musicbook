package br.com.sys.Filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sys.beans.MainBean;

public class AuthenticationFilter implements Filter{
	
	private ServletContext context;

	@Override
	public void destroy() {
		context = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		MainBean main = (MainBean) context.getAttribute("main");
		if(main.usuarioAutenticado()) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(httpRequest.getContextPath());
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.context = config.getServletContext();
	}

}
