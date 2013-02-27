package gui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import data.system.Sistema;

@ManagedBean(name="cdUsuario")
@SessionScoped
public class CadastroUsuarioBean {
	
	@Inject
	private Sistema sistema;
	private String login;
	private String email;
	private String senha;
	private String nome;

	public CadastroUsuarioBean() {
		System.out.println("iniciou o bean de cadastro...");
	}

	public String novoCadastro() {
		if (getLogin() != null && getEmail() != null
				&& getSenha() != null && getNome() != null) {
			sistema.criarUsuario(getLogin(), getSenha(), getNome(), getEmail());
			System.out.println(sistema.abrirSessao(getLogin(), getSenha()));
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
