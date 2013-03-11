package gui;

import gui.util.Contexto;
import gui.util.Messages;
import gui.util.ValidaCampo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import data.system.Sistema;

@ManagedBean(name="cdUsuario")
@ViewScoped
public class CadastroUsuarioBean {
	
	/**
	 * Enum com as mensagens de erro de cadastro de usuario.
	 */
	public enum MsgErro {
		LOGIN_ERRO("Login não digitado."),
		SENHA_ERRO("Senha não digitada."),
		EMAIL_ERRO("Email não digitado."),
		NOME_ERRO("Nome não digitado.");
		
		private String msgErro;
		
		MsgErro(String msgErro) {
			this.msgErro = msgErro;
		}
		
		public String getErro() {
			return msgErro;
		}
	}
	
	private Sistema sistema;
	private LoginBean loginBean;
	
	private String login;
	private String email;
	private String senha;
	private String nome;

	public CadastroUsuarioBean() {
		sistema = (Sistema) Contexto.getInContext(
				Contexto.Variaveis.SISTEMA.getNome());
		loginBean = (LoginBean) Contexto.getInContext(
				Contexto.Variaveis.LOGIN.getNome());
	}

	/**
	 * Cria novo cadastro de usuario.
	 * @return se cadastrado com sucesso retorna a home do usuairo.
	 */
	public String novoCadastro() {
		if (verificaCampos()) {
			try {
				sistema.criarUsuario(login, senha, nome, email);
				return efetuaLogin();
			} catch (Exception e) {
				Messages.addMsgErro(e.getMessage());
			}
		}
		return "";
	}
	
	/*
	 * Verifica os campos do cadastro.
	 */
	private boolean verificaCampos() {
		return ValidaCampo.valida(nome, MsgErro.NOME_ERRO.getErro())
				&& ValidaCampo.valida(email, MsgErro.EMAIL_ERRO.getErro())
				&& ValidaCampo.valida(login, MsgErro.LOGIN_ERRO.getErro())
				&& ValidaCampo.valida(senha, MsgErro.SENHA_ERRO.getErro());
	}
	
	/**
	 * Efetua login do novo usuário cadastrado.
	 * @return url da Home do usuário.
	 */
	public String efetuaLogin() {
		try {
			return loginBean.efetuaLogin(login, senha);
		} catch (Exception e) {
			Messages.addMsgErro(e.getMessage());
		}
		return "";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
