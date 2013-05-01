package gui;

import java.util.List;

import javax.faces.bean.SessionScoped;

import dados.sistema.Sistema;
import dados.usuario.Usuario;
import exceptions.system.SessaoInexistenteException;

@SessionScoped
public class SessaoUsuario {
	
	private Sistema sistema;
	private int id;
	
	public SessaoUsuario(Sistema sistema, int idSessao) {
		this.sistema = sistema;
		this.id = idSessao;
	}
	
	public Usuario getUsuario() throws SessaoInexistenteException {
		return sistema.getUsuarioLogado(id);
	}
	
	public List<Integer> getFeedPrincipal() throws SessaoInexistenteException {
		return sistema.getMainFeed(id);
	}
	
	public List<Integer> getSonsFavoritos() throws SessaoInexistenteException {
		return sistema.getSonsFavoritos(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public List<Integer> getPerfilMusical() throws SessaoInexistenteException {
		return sistema.getPerfilMusical(id);
	}
	
	public List<Integer> getFeedExtra() throws SessaoInexistenteException {
		return sistema.getFeedExtra(id);
	}
	
	public List<Integer> getSeguidores() throws SessaoInexistenteException {
		return sistema.getListaDeSeguidores(id);
	}
}
