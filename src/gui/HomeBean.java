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
import data.util.CompositionRule;
import exceptions.system.SessaoInexistenteException;

/**
 * Representa as ações da home do usuario.
 */
@ManagedBean(name="home")
@ViewScoped
public class HomeBean {
	
	private static final String 
		MSG_SOM_FAVORITADO = "Som favoritado com sucesso.";
	private Sistema sistema;
	private LoginBean login;
	
	private List<Usuario> seguidores;
	private CompositionRule feedRule;
	
	/**
	 * Construtor do Bean.
	 */
	public HomeBean() {
		sistema = (Sistema) Contexto.getInContext(
				Contexto.Variaveis.SISTEMA.getNome());
		login = (LoginBean) Contexto.getInContext(
				Contexto.Variaveis.LOGIN.getNome());
	}
	
	/**
	 * Mostra pagina do perfil do usuario passado como
	 * parametro.
	 * @param usuario
	 * @return string url da pagina do perfil
	 */
	public String verPerfil(Object usuario, boolean withUrl) {
		Contexto.insertInContext(usuario, 
				Contexto.Variaveis.USER_PESQ.getNome());
		if (withUrl) {
			return Pages.PERFIL.getUrl();
		}
		return Pages.PERFIL.getPagina();
	}

	/**
	 * Mostra pagina do perfil do usuario logado.
	 * @return string url da pagina do perfil
	 */
	public String perfilUsuario() {
		try {
			return verPerfil(
					sistema.getUsuarioLogado(login.getID_Sessao()), false);
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return "";
		}
	}
	
	/**
	 * Mostra a lista de sons do feed principal do usuario logado.
	 * @return lista de sons
	 */
	public List<Som> feedPrincipal() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for(Integer idSom: sistema.getMainFeed(login.getID_Sessao())) {
				listaSons.add(sistema.getSom(idSom));
			}
			return listaSons;
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return listaSons;
		}
	}
	
	/**
	 * Mostra a lista de sons favoritos usuario logado.
	 * @return lista de sons
	 */
	public List<Som> favoritos() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for(Integer idSom: sistema.getSonsFavoritos(login.getID_Sessao())) {
				listaSons.add(sistema.getSom(idSom));
			}
			return listaSons;
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return listaSons;
		}
	}
	
	/**
	 * Mostra a lista de sons do usuario logado.
	 * @return lista de sons
	 */
	public List<Som> meusSons() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for(Integer idSom: sistema.getPerfilMusical(login.getID_Sessao())) {
				listaSons.add(sistema.getSom(idSom));
			}
			return listaSons;
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return listaSons;
		}
	}
	
	/**
	 * Favorita um som do usuario.
	 * @param som
	 */
	public String favoritaSom(Som som) {
		try {
			sistema.favoritarSom(login.getID_Sessao(), som.getID());
			Messages.addMsgSucesso(MSG_SOM_FAVORITADO);
		} catch (Exception e) {
			Messages.addMsgErro(e.getMessage());
		}
		return "";
	}
	
	/**
	 * Muda ordenacao do feed principal.
	 * @param rule
	 */
	public String setRuleMainFeed() {
		try {
			sistema.setMainFeedRule(feedRule, login.getID_Sessao());
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
		}
		return Pages.HOME.getUrl();
	}
	
	public CompositionRule[] getCompositonRules() {
		return CompositionRule.values();
	}
	
	/**
	 * Mostra a lista de sons do feed extra do usuario logado.
	 * @return lista de sons
	 */
	public List<Som> feedExtra() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for(Integer idSom: sistema.getFeedExtra(login.getID_Sessao())) {
				listaSons.add(sistema.getSom(idSom));
			}
			return listaSons;
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return listaSons;
		}
	}
	
	/**
	 * Mostra o nome do usuario que postou o som.
	 * @param som
	 * @return string nome
	 */
	public String getCriadorSom(Som som) {
		return sistema.getUsuario(som.getPosterId()).getNome();
	}
	
	public String foto() {
		return "imgs/foto.jpg";
	}

	/**
	 * Mostra a lista de seguidores de um usuario.
	 * @return lista de usuarios
	 */
	public List<Usuario> getSeguidores() {
		seguidores = new ArrayList<Usuario>();
		try {
			for(Integer id: sistema.getListaDeSeguidores(login.getID_Sessao())) {
				seguidores.add(sistema.getUsuario(id));
			}
			return seguidores;
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return seguidores;
		}
	}

	public void setSeguidores(List<Usuario> seguidores) {
		this.seguidores = seguidores;
	}

	public CompositionRule getFeedRule() {
		return feedRule;
	}

	public void setFeedRule(CompositionRule feedRule) {
		this.feedRule = feedRule;
	}
}