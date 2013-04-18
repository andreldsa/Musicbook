package gui;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.types.User;

import dados.usuario.Usuario;

public class UsuarioFacebook extends User{
	
	@Facebook
	private String username;
	
	@Facebook
	private String name;
	
	@Facebook
	private String email;

	private Usuario usuario;
	
	public UsuarioFacebook() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static UsuarioFacebook loga(String acessToken) {
		FacebookClient cliente = new DefaultFacebookClient(acessToken);
		return cliente.fetchObject("me", UsuarioFacebook.class);
	}
}
