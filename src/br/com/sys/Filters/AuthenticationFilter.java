package br.com.sys.Filters;

import java.io.IOException;

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
