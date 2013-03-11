package gui;

import gui.util.Contexto;
import gui.util.Messages;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import data.som.Som;
import data.system.Sistema;
import data.user.Usuario;
import exceptions.system.SessaoInexistenteException;

@ManagedBean(name="perfil")
@ViewScoped
public class PerfilBean {
	
	private static final String 
		MSG_SEGUINDO_SUCESSO = "Seguindo usuário com sucesso.";
	private static final String 
		MSG_JA_EH_SEGUIDOR = "Você já está seguindo este usuário.";
	private static final String 
		MSG_SOM_FAVORITADO = "Som favoritado com sucesso";
	private Sistema sistema;
	private Usuario usuario;
	private String imagemPerfil;
	private LoginBean login;
	
	/**
	 * Construtor
	 */
	public PerfilBean() {
		sistema = (Sistema) Contexto.getInContext(
				Contexto.Variaveis.SISTEMA.getNome());
		login = (LoginBean) Contexto.getInContext(
				Contexto.Variaveis.LOGIN.getNome());
		usuario = (Usuario) Contexto.getInContext(
				Contexto.Variaveis.USER_PESQ.getNome());
		imagemPerfil = "imgs/foto.jpg";
	}

	/**
	 * Mostra a lista com a visão de sons do usuário.
	 * @return lista de sons
	 */
	public List<Som> visaoDeSons() {
		List<Som> listaSons = new ArrayList<Som>();
		for(Integer somID: usuario.getPerfilMusical()) {
			listaSons.add(sistema.getSom(somID));
		}
		return listaSons;
	}
	
	//Verifica se o usuário logado já é um seguidor do perfil vizualizado. 
	private boolean isSeguidor() {
		try {
			return sistema.isSeguidor(login.getID_Sessao(), usuario.getID());
		} catch (SessaoInexistenteException erro) {
			Messages.addMsgErro(erro.getMessage());
			return false;
		}
	}
	
	/**
	 * Favorita um som do usuario.
	 * @param som
	 */
	public void favoritaSom(Som som) {
		try {
			sistema.favoritarSom(login.getID_Sessao(), som.getID());
			Messages.addMsgSucesso(MSG_SOM_FAVORITADO);
		} catch (Exception e) {
			Messages.addMsgErro(e.getMessage());
		}
	}
	
	/**
	 * O usuário logado passa a seguir o usuário do perfil
	 * que está visualizando.
	 */
	public void seguir() {
		if (isSeguidor()) {
			System.out.println("ok o cara é seguidor...");
			Messages.addMsgErro(MSG_JA_EH_SEGUIDOR);
			return;
		}
		try {
			sistema.seguirUsuario(login.getID_Sessao(), usuario.getLogin());
			Messages.addMsgSucesso(MSG_SEGUINDO_SUCESSO);
		} catch (Exception e) {
			System.out.println("deu pau...");
			Messages.addMsgErro(e.getMessage());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getImagemPerfil() {
		return imagemPerfil;
	}

	public void setImagemPerfil(String imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}
}