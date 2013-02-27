package gui;

import gui.util.Contexto;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import data.system.Sistema;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean {
	
	public Sistema sistema;
	private int ID_Sessao = -1;
	private String login;
	private String senha;
	
	public LoginBean() {
		sistema = (Sistema) Contexto.getInContext("sistema");
		System.out.println("Sistema recuperado...");
		System.out.println("Login bean construido....");
	}
	
	/**
	 * Efetua login no sistema a partir de um Login e Senha.	
	 * @return String Pages.HOME ou UsuarioNaoCadastradoException
	 * caso o usuario invalido ou ErroDeAutenticacaoException caso
	 * a senha esteja incorreta.
	 */
	public String efetuaLogin() {
		ID_Sessao  = sistema.abrirSessao(getLogin(), getSenha());
		if (ID_Sessao != -1) {
			return Pages.HOME.getUrl();
		}
		return "";
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
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
