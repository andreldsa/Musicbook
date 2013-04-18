package gui;

import gui.util.Contexto;
import gui.util.Messages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dados.sistema.Sistema;
import dados.som.Som;
import dados.usuario.Usuario;
import dados.util.CompositionRule;
import exceptions.system.SessaoInexistenteException;

/**
 * Representa as ações da home do usuario.
 */
@ManagedBean(name = "home")
@ViewScoped
public class HomeBean {

	private static final String MSG_SOM_FAVORITADO = "Som favoritado com sucesso.";
	
	private Sistema sistema;
	private SessaoUsuario sessao;

	private List<Usuario> seguidores;
	private CompositionRule feedRule;
	
	private int idSessao;

	/**
	 * Construtor do Bean.
	 */
	public HomeBean() {
		sistema = (Sistema) Contexto
				.getInContext(Contexto.Variaveis.SISTEMA.getNome());
		sessao = (SessaoUsuario) (SessaoUsuario) Contexto
				.getInContext("sessao"+getSessao());
		if (sessao != null) {
			setIdSessao(sessao.getId());
		}
	}
	
	private String getSessao() {
		return Contexto.getExternalContext().getRequestParameterMap().get("sessao");
	}

	/**
	 * Mostra a lista de sons do feed principal do usuario logado.
	 * 
	 * @return lista de sons
	 */
	public List<Som> feedPrincipal() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for (Integer idSom : sessao.getFeedPrincipal()) {
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
	 * 
	 * @return lista de sons
	 */
	public List<Som> favoritos() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for (Integer idSom : sessao.getSonsFavoritos()) {
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
	 * 
	 * @return lista de sons
	 */
	public List<Som> meusSons() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for (Integer idSom : sessao.getPerfilMusical()) {
				listaSons.add(sistema.getSom(idSom));
			}
			return listaSons;
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
			return listaSons;
		}
	}
	
	/**
	 * Lista de sons recomendados
	 */
	public List<Som> getSonsRecomendados() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for (Integer idSom : sistema.getFontesDeSonsRecomendadas(idSessao)) {
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
	 * 
	 * @param som
	 */
	public String favoritaSom(Som som) {
		try {
			sistema.favoritarSom(getIdSessao(), som.getID());
			Messages.addMsgSucesso(MSG_SOM_FAVORITADO);
		} catch (Exception e) {
			Messages.addMsgErro(e.getMessage());
		}
		return "";
	}

	/**
	 * Muda ordenacao do feed principal.
	 * 
	 * @param rule
	 */
	public String setRuleMainFeed() {
		try {
			sistema.setMainFeedRule(feedRule, sessao.getId());
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
		}
		return Pages.HOME.getUrl()+"sessao="+sessao.getId();
	}

	public CompositionRule[] getCompositonRules() {
		return CompositionRule.values();
	}

	/**
	 * Mostra a lista de sons do feed extra do usuario logado.
	 * 
	 * @return lista de sons
	 */
	public List<Som> feedExtra() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for (Integer idSom : sessao.getFeedExtra()) {
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
	 * 
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
	 * 
	 * @return lista de usuarios
	 */
	public List<Usuario> getSeguidores() {
		seguidores = new ArrayList<Usuario>();
		try {
			for (Integer id : sistema.getListaDeSeguidores(idSessao)) {
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
	
	/**
	 * Verifica se existe um usuario autenticado no sistema.
	 * 
	 * @return True or False
	 * @throws IOException
	 */
	public boolean usuarioAutenticado() throws IOException {
		if (sessao == null) {
			return false;
		}
		return sessao.getId() != -1;
	}
	
	public String getHome() {
		return Pages.HOME.getUrl()+"sessao="+getIdSessao();
	}

	public int getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(int idSessao) {
		this.idSessao = idSessao;
	}
	

	public String getListasUsuarios() {
		return Pages.LISTAS.getUrl() + "sessao=" + sessao.getId();
	}
}