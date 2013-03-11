package gui;

import gui.util.Contexto;
import gui.util.Messages;
import gui.util.ValidaCampo;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import data.system.Sistema;
import exceptions.system.SessaoInexistenteException;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean {
	
	private Sistema sistema;
	private int ID_Sessao = -1;
	private String login;
	private String senha;
	private String USUARIO_INVALIDO = "Digite o login!";
	private String SENHA_INVALIDA = "Digite a senha!"; 
	
	private String imagemPerfil = "imgs/foto.jpg";
	
	public LoginBean() {
		sistema = (Sistema) Contexto.getInContext(
				Contexto.Variaveis.SISTEMA.getNome());
		verificaContexto();
	}
	
	private void verificaContexto() {
		String loginContextName = Contexto.Variaveis.LOGIN.getNome();
		if (Contexto.getInContext(loginContextName) == null) {
			Contexto.insertInContext(this, 
					Contexto.Variaveis.LOGIN.getNome());
		} else {
			this.login = null;
			this.senha = null;
			setID_Sessao(-1);
		}
	}
	
	/**
	 * Efetua login no sistema.	
	 * @return String Pages.HOME
	 */
	public String efetuaLogin() {
		if (!verificaCampos()) {
			return "";
		}
		try {
			setID_Sessao(sistema.abrirSessao(getLogin(), getSenha()));
			return Pages.HOME.getUrl();
		} catch (Exception e) {
			Messages.addMsgErro(e.getMessage());
			return "";
		}
	}
	
	private boolean verificaCampos() {
		return ValidaCampo.valida(login, USUARIO_INVALIDO)
				&& ValidaCampo.valida(senha, SENHA_INVALIDA);
	}
	
	/**
	 * Efetua login no sistema a partir de um Login e Senha.
	 * @param login
	 * @param senha
	 * @return pagina home	
	 */
	public String efetuaLogin(String login, String senha) {
		this.login = login;
		this.senha = senha;
		return efetuaLogin();
	}

	/**
	 * Verifica se existe um usuario autenticado no sistema.
	 * @return True or False
	 * @throws IOException
	 */
	public boolean usuarioAutenticado() throws IOException {
		if(getID_Sessao() == -1) {
			return false;
		}
		return true;
	}

	/**
	 * Encerra a autenticacao do usuario que esta logado 
	 * no sistema.
	 * @return String Pages.INDEX
	 */
	public String logout() {
		sistema.encerraSessao(getID_Sessao());
		setID_Sessao(-1);
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
	
	/**
	 * Retorna o nome do usuario logado na sessão.
	 * @throws SessaoInexistenteException 
	 */
	public String getNomeUsuario() {
		try {
			return sistema.getUsuarioLogado(getID_Sessao()).getNome();
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return "";
		}
	}

	public int getID_Sessao() {
		return ID_Sessao;
	}

	private void setID_Sessao(int iD_Sessao) {
		ID_Sessao = iD_Sessao;
	}

	public String getImagemPerfil() {
		return imagemPerfil;
	}

	public void setImagemPerfil(String imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}
}