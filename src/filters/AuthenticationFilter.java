package filters;

import gui.LoginBean;
import gui.util.Contexto;

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
		
		LoginBean login = (LoginBean) context.getAttribute(
				Contexto.Variaveis.LOGIN.getNome());
		if(login.usuarioAutenticado()) {
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
