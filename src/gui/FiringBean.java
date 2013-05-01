package gui;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

import dados.sistema.Sistema;
import exceptions.user.LoginInvalidoException;
import gui.util.Contexto;

@ManagedBean
@SessionScoped
public class FiringBean {
	
	private String NOME_PROJETO = RedirectURLBackingBean.NOME_PROJETO;
	
	private String nome;
	private String email;
	private String login;
	private String senha;
	
	/**
	 * Requisita uma nova autenticação ao Facebook.
	 */
	public void codeRequest(ActionEvent event) {
		String furl = "https://www.facebook.com/dialog/oauth?"
				+ "client_id=129729313885038&"
				+ "redirect_uri=http://localhost:8080/"+NOME_PROJETO+"/faces/login.xhtml&"
				+ "scope=publish_stream,user_groups,status_update,email&"
				+ "response_type=code";

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		try {
			response.sendRedirect(furl);
		} catch (Exception e) {

		}
	}
	
	/**
	 * Se o usuario já existir no sistema redireciona para a home do usuario,
	 * caso contrário, redireciona para uma página para definir uma nova senha
	 * para o usuário no sistema.
	 */
	public void redireciona() throws IOException {

		FacebookClient client = new DefaultFacebookClient(getToken());
		User user = client.fetchObject("me", User.class);
		this.nome = user.getFirstName();
		this.email = user.getEmail();
		this.login = user.getUsername();
		Sistema sistema = Sistema.getInstance();
		try {
			sistema.getUsuarioID(user.getUsername());
			int ID_Sessao = sistema.abrirSessaoLogadoNoFacebook(user.getUsername());
			SessaoUsuario sessaoUsuario = new SessaoUsuario(sistema, ID_Sessao);
			Contexto.insertInContext(sessaoUsuario, "sessao" + ID_Sessao);
			sendRedirect(Pages.HOME.getPagina(), ID_Sessao);
		} catch (LoginInvalidoException e) {
			sendRedirect("cd_user.xhtml", -1);
		}
	}
	
	private void sendRedirect(String url, int sessao) throws IOException {
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		HttpServletRequest httpRequest = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
		if (sessao != -1) {
			//FIXME
			response.sendRedirect(httpRequest.getContextPath()+"/faces"+url+"?sessao="+sessao+"?");
		} else {
			response.sendRedirect(url);
		}
	}
	
	/**
	 * Cadastra novo usuário no sistema.
	 * @return Home do novo usuário cadastrado.
	 */
	public String novoCadastro() {
		CadastroUsuarioBean cdUsuario = new CadastroUsuarioBean();
		cdUsuario.setEmail(email);
		cdUsuario.setLogin(login);
		cdUsuario.setNome(nome);
		cdUsuario.setSenha(senha);
		return cdUsuario.novoCadastro();
	}

	public String getCode() {
		return Contexto.getExternalContext().getRequestParameterMap().get("code");
	}

	/**
	 * Retorna o código de acesso à sessão de login no Facebook.
	 */
	public String getToken() {
		RedirectURLBackingBean rUrl = new RedirectURLBackingBean();
		return rUrl.getAccessToken();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
