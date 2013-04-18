package gui;

import gui.util.Contexto;
import gui.util.Messages;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import dados.sistema.Sistema;
import dados.som.Som;
import dados.usuario.Usuario;
import exceptions.system.SessaoInexistenteException;

@ManagedBean(name = "perfil")
@RequestScoped
public class PerfilBean {

	private static final String MSG_SEGUINDO_SUCESSO = "Seguindo usuário com sucesso.";
	private static final String MSG_JA_EH_SEGUIDOR = "Você já está seguindo este usuário.";
	private static final String MSG_SOM_FAVORITADO = "Som favoritado com sucesso";
	private Sistema sistema;
	private Usuario usuario;
	private String imagemPerfil;
	
	private int idSessao;

	/**
	 * Construtor
	 */
	public PerfilBean() {
		sistema = (Sistema) Contexto.getInContext(Contexto.Variaveis.SISTEMA
				.getNome());
		usuario = (Usuario) Contexto.getInContext(Contexto.Variaveis.USER_PESQ
				.getNome());
		imagemPerfil = "imgs/foto.jpg";
	}

	/**
	 * Mostra a lista com a visão de sons do usuário.
	 * 
	 * @return lista de sons
	 */
	public List<Som> visaoDeSons() {
		List<Som> listaSons = new ArrayList<Som>();
		System.out.println("visao de sons: "+usuario);
		for (Integer somID : usuario.getPerfilMusical()) {
			listaSons.add(sistema.getSom(somID));
		}
		return listaSons;
	}

	// Verifica se o usuário logado já é um seguidor do perfil visualizado.
	private boolean isSeguidor() {
		try {
			return sistema.isSeguidor(idSessao, usuario.getID());
		} catch (SessaoInexistenteException erro) {
			Messages.addMsgErro(erro.getMessage());
			return false;
		}
	}

	/**
	 * Favorita um som do usuario.
	 * 
	 * @param som
	 */
	public String favoritaSom(Som som) {
		try {
			sistema.favoritarSom(idSessao, som.getID());
			Messages.addMsgSucesso(MSG_SOM_FAVORITADO);
		} catch (Exception e) {
			Messages.addMsgErro(e.getMessage());
		}
		return "sessao=" + idSessao;
	}

	/**
	 * O usuário logado passa a seguir o usuário do perfil que está
	 * visualizando.
	 */
	public String seguir(int idSessao) {
		if (isSeguidor()) {
			Messages.addMsgErro(MSG_JA_EH_SEGUIDOR);
		}
		try {
			System.out.println("seguir :---- "+idSessao);
			System.out.println("seguir ---2-- "+usuario.getLogin());
			sistema.seguirUsuario(idSessao, usuario.getLogin());
			Messages.addMsgSucesso(MSG_SEGUINDO_SUCESSO);
		} catch (Exception e) {
			Messages.addMsgErro(e.getMessage());
		}
		return "sessao=" + idSessao;
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
	
	/**
	 * Mostra pagina do perfil do usuario passado como parametro.
	 * 
	 * @param usuario
	 * @return string url da pagina do perfil
	 */
	public String verPerfil(Object usuario, boolean withUrl, int idSessao) {
		this.idSessao = idSessao;
		System.out.println("ver perfil =::::: "+idSessao);
		Contexto.insertInContext(usuario,
				Contexto.Variaveis.USER_PESQ.getNome());
		if (withUrl) {
			return Pages.PERFIL.getUrl()+"sessao="+idSessao;
		}
		return Pages.PERFIL.getPagina()+"sessao="+idSessao;
	}
	
	/**
	 * Mostra pagina do perfil do usuario logado.
	 * 
	 * @return string url da pagina do perfil
	 */
	public String perfilUsuario(int idSessao) {
		try {
			return verPerfil(sistema.getUsuarioLogado(idSessao), false, idSessao);
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return "";
		}
	}
}