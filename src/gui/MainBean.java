package gui;

import gui.util.Contexto;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import dados.sistema.Sistema;

/**
 * Classe que representa as acoes da pagina inicial.
 */
@ManagedBean(name = "main")
@ApplicationScoped
public class MainBean implements Serializable {

	private static final long serialVersionUID = 6686329191099618764L;

	private String sysName = "MusicBook";
	private Sistema sistema;

	/**
	 * Construtor padrao do MainBean
	 */
	public MainBean() {
		setSistema(Sistema.getInstance());
		// Seta o objeto no contexto da aplicacao.
		Contexto.insertInContext(sistema, Contexto.Variaveis.SISTEMA.getNome());
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	/**
	 * Acessa o banco de paginas e retorna a url.
	 * 
	 * @param name
	 * @return String url
	 */
	public String getPage(String name) {
		try {
			Pages page = Pages.valueOf(name.toUpperCase());
			return page.getPagina();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Acessa o banco de paginas e retorna a url com redirecinamento.
	 * 
	 * @param name
	 * @return String url
	 */
	public String getURL(String name) {
		try {
			Pages page = Pages.valueOf(name.toUpperCase());
			return page.getUrl();
		} catch (Exception e) {
			return "";
		}
	}
}