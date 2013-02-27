package gui;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import data.system.Sistema;

/**
 * Classe que representa as acoes da pagina inicial.
 */
@ManagedBean(name="main")
@SessionScoped
public class MainBean implements Serializable{
	
	private static final long serialVersionUID = 6686329191099618764L;
	
	private String sysName = "MusicBook";
	private Sistema sistema = new Sistema();
	private int ID_Sessao = -1;
	
	private String login;
	private String senha;
	
	public MainBean() {
		getExternalContext().getApplicationMap().put("main", this); // Seta o objeto no contexto da aplicacao.
	}
	
	/**
	 * Retorna o contexto da Aplicacao
	 * @return ExternalContext
	 */
	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	public String getHome() {
		return Pages.HOME.getUrl();
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return "";
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/**
	 * Efetua login no sistema a partir de um Login e Senha.	
	 * @return String Pages.HOME ou UsuarioNaoCadastradoException
	 * caso o usuario invalido ou ErroDeAutenticacaoException caso
	 * a senha esteja incorreta.
	 */
	public String efetuaLogin() {
		ID_Sessao  = sistema.abrirSessao(login, senha);
		if (ID_Sessao != -1) {
			return Pages.HOME.getUrl();
		}
		return "";
	}
	
	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	/**
	 * Verifica se existe um usuario autenticado no sistema.
	 * @return True or False
	 * @throws IOException
	 */
	public boolean usuarioAutenticado() throws IOException {
		if(ID_Sessao == -1) {
			return false;
		}
		return true;
	}

	/**
	 * Encerra a autenticacao do usuario que esta¡ logado no sistema.
	 * @return String Pages.INDEX
	 */
	public String logout() {
		sistema.encerraSessao(ID_Sessao);
		return Pages.INDEX.getUrl();
	}
	
	/**
	 * Acessa o banco de paginas e retorna a url.
	 * @param name
	 * @return String url
	 */
	public String getPage(String name) {
		try{
			Pages page = Pages.valueOf(name.toUpperCase());
			return page.getPagina();
		}catch(Exception e) {
			return "";
		}
	}

}
