package gui;

/**
 * Enum com constantes para a url das paginas.
 */
public enum Pages {
	
	INDEX ("/index.xhtml"),
	HOME ("/view/home.xhtml"),
	HEADER ("/layout/header.xhtml"),
	FOOTER ("/layout/footer.xhtml"),
	SIDEBAR ("/layout/sidebar.xhtml"),
	MENU ("/layout/menu.xhtml"),
	PERFIL ("/view/perfil.xhtml");
	
	private String pagina;
	
	Pages(String pagina) {
		this.pagina = pagina;
	}

	/**
	 * Retorna a url da pagina com redirecionamento.
	 * @return String
	 */
	public String getUrl() {
		return pagina+"?faces-redirect=true";
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	
	/**
	 * Retorna a url original da pagina.
	 * @return String
	 */
	public String getPagina() {
		return pagina;
	}

}
