package gui;

/**
 * Enum com constantes para a url das paginas.
 */
public enum Pages {
	
	INDEX ("/index.xhtml"),
	HOME ("/view/home.xhtml"),
	HEADER ("/view/header.xhtml"),
	FOOTER ("/view/footer.xhtml"),
	MENU ("/view/menu.xhtml"),
	
	/* ---- Páginas dos Módulos ----*/
	CAD_PRODUTO ("/view/cadastroproduto.xhtml");
	
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
