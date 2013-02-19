package br.com.sys.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.sys.Pages;
import br.com.sys.Sistema;
import br.com.sys.exceptions.ErroDeAutenticacaoException;
import br.com.sys.exceptions.UsuarioNaoCadastradoException;

@ManagedBean(name="main")
@SessionScoped
public class MainBean implements Serializable{
	
	private static final long serialVersionUID = 6686329191099618764L;
	
	private String sysName = "Authentication System";;
	private Sistema sistema = new Sistema();
	
	private String login;
	private String senha;
	
	private String erro;
	private boolean errorHasOccurred;
	
	public MainBean() {
		getExternalContext().getApplicationMap().put("main", this);
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
	
	/**
	 * Retorna a página de novo Cadastro de Produto.
	 * @return String Pages.CAD_PRODUTO
	 */
	public String cadastroProduto() {
		return Pages.CAD_PRODUTO.getUrl();
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
	 * caso o usuario inválido ou ErroDeAutenticacaoException caso
	 * a senha esteja incorreta.
	 */
	public String efetuaLogin() {
		try {
			sistema.efetuaLogin(login, senha);
			resetExceptions();
			return Pages.HOME.getUrl();
		} catch (UsuarioNaoCadastradoException e) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR,UsuarioNaoCadastradoException.MENSAGEM, 
							""));
		} catch (ErroDeAutenticacaoException e) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR,ErroDeAutenticacaoException.MENSAGEM, 
							""));
		}
		return "";
	}
	
	private void resetExceptions() {
		setErro("");
		setErrorHasOccurred(false);
	}
	
	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		setErrorHasOccurred(true);
		this.erro = erro;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	/**
	 * Verifica se existe um usuário autenticado no sistema.
	 * @return True or False
	 * @throws IOException
	 */
	public boolean usuarioAutenticado() throws IOException {
		if(sistema.getUsuarioLogado() == null)
			return false;
		return true;
	}

	public boolean isErrorHasOccurred() {
		return errorHasOccurred;
	}

	public void setErrorHasOccurred(boolean errorHasOccurred) {
		this.errorHasOccurred = errorHasOccurred;
	}

	/**
	 * Encerra a autenticação do usuário que está logado no sistema.
	 * @return String Pages.INDEX
	 */
	public String logout() {
		sistema.logout();
		return Pages.INDEX.getUrl();
	}
	
	/**
	 * Acessa o banco de páginas e retorna a url.
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
