package facade.easyAccept;

import dados.sistema.Sistema;
import dados.util.CompositionRule;
import dados.util.OurTime;
import exceptions.EAfacade.AtributoInexistenteException;
import exceptions.EAfacade.AtributoInvalidoException;
import exceptions.EAfacade.DataDeCriacaoInvalida;
import exceptions.EAfacade.RegraDeComposicaoInexistenteException;
import exceptions.EAfacade.RegraDeComposicaoInvalidaException;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SessaoInvalidaException;
import exceptions.system.SomInexistenteException;
import exceptions.system.SomInvalidoException;
import exceptions.user.EmailInvalidoException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.ListaInvalidaException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;
import exceptions.user.NomeInvalidoException;
import exceptions.user.UsuarioInexistenteException;
import exceptions.user.UsuarioInvalidoException;

public class OurEasyAcceptFacade {

	private final String NOME = "nome";
	private final String EMAIL = "email";

	private final String DATACRIACAO = "dataCriacao";

	private Sistema sistema;

	public OurEasyAcceptFacade(Sistema sistema) {
		this.sistema = sistema;
	}

	public void zerarSistema() {
		sistema.zerarSistema();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws LoginInvalidoException, NomeInvalidoException,
			EmailInvalidoException, LoginJaExisteException,
			EmailJaExisteException {
		sistema.criarUsuario(login, senha, nome, email);
	}

	public int abrirSessao(String login, String senha)
			throws LoginInvalidoException, UsuarioInexistenteException {
		return sistema.abrirSessao(login, senha);
	}

	public String getAtributoUsuario(String login, String atributo)
			throws LoginInvalidoException, UsuarioInexistenteException,
			AtributoInvalidoException, AtributoInexistenteException {
		int userId = sistema.getUsuarioID(login);
		if (userId == -1)
			throw new UsuarioInexistenteException("Usuário inexistente");
		if (atributo == null || atributo.isEmpty())
			throw new AtributoInvalidoException("Atributo inválido");
		if (atributo.equals("nome")) {
			return sistema.getNomeUsuario(sistema.getUsuarioID(login));
		} else if (atributo.equals("email")) {
			return sistema.getEmailUsuario(sistema.getUsuarioID(login));
		} else {
			throw new AtributoInexistenteException("Atributo inexistente");
		}
	}

	public String getPerfilMusical(int idSessao)
			throws SessaoInexistenteException {
		return sistema.getPerfilMusical(idSessao).toString().replace('[', '{')
				.replace(']', '}').replace(" ", "");
	}

	public int postarSom(String idSessao, String link, String dataCriacao)
			throws SessaoInexistenteException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessaoint = idSessaoCheck(idSessao);
		if (link == null || link.isEmpty())
			throw new SomInvalidoException("Som inválido");
		String[] dataValores = dataCriacao.split("/");
		try {
			OurTime ourTime = new OurTime(Integer.parseInt(dataValores[0]),
					Integer.parseInt(dataValores[1]),
					Integer.parseInt(dataValores[2]));
		} catch (Exception e) {
			throw new DataDeCriacaoInvalida(e);
		}
		return sistema.postarSom(idSessaoint, link, dataCriacao);
	}

	public int postarSom(int idSessao, String link, String dataCriacao)
			throws SessaoInexistenteException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		return this.postarSom(idSessao + "", link, dataCriacao);
	}

	public String getAtributoSom(String idSom, String atributo)
			throws AtributoInvalidoException, AtributoInexistenteException,
			SomInvalidoException {
		if (idSom == null || idSom.isEmpty())
			throw new SomInvalidoException("Som inválido");
		Integer idSomInt = Integer.parseInt(idSom);
		if (atributo == null || atributo.isEmpty())
			throw new AtributoInvalidoException("Atributo inválido");
		if (atributo.equals("dataCriacao")) {
			return sistema.getDataSom(idSomInt);
		} else {
			throw new AtributoInexistenteException("Atributo inexistente");
		}
	}

	public void seguirUsuario(String idSessao, String login)
			throws LoginInvalidoException, SessaoInexistenteException,
			UsuarioInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		sistema.seguirUsuario(idSessaoint, login);
	}

	public String getListaDeSeguidores(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		return sistema.getListaDeSeguidores(idSessaoint).toString()
				.replace('[', '{').replace(']', '}').replace(" ", "");
	}

	public int getNumeroDeSeguidores(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		return sistema.getNumeroDeSeguidores(idSessaoint);
	}

	public String getFonteDeSons(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		return sistema.getFonteDeSons(idSessaoint).toString().replace('[', '{')
				.replace(']', '}').replace(" ", "");
	}

	public String getSonsFavoritos(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		return sistema.getSonsFavoritos(idSessaoint).toString()
				.replace('[', '{').replace(']', '}').replace(" ", "");
	}

	public String getFeedExtra(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		return sistema.getFeedExtra(idSessaoint).toString().replace('[', '{')
				.replace(']', '}').replace(" ", "");
	}

	public void favoritarSom(String idSessao, String idSom)
			throws SessaoInexistenteException, SomInvalidoException,
			SomInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		if (idSom == null || idSom.isEmpty())
			throw new SomInvalidoException("Som inválido");
		int idSomint = Integer.parseInt(idSom);
		sistema.favoritarSom(idSessaoint, idSomint);
	}

	public String getMainFeed(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		return sistema.getMainFeed(idSessaoint).toString().replace('[', '{')
				.replace(']', '}').replace(" ", "");
	}

	public void setMainFeedRule(String idSessao, String rule)
			throws SessaoInexistenteException, SessaoInvalidaException,
			RegraDeComposicaoInexistenteException,
			RegraDeComposicaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		if (rule == null || rule.isEmpty())
			throw new RegraDeComposicaoInvalidaException(
					"Regra de composição inválida");
		if (rule.equals("PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS")) {
			sistema.setMainFeedRule(CompositionRule.Rule1, idSessaoint);
		} else if (rule.equals("PRIMEIRO OS SONS COM MAIS FAVORITOS")) {
			sistema.setMainFeedRule(CompositionRule.Rule2, idSessaoint);
		} else if (rule
				.equals("PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO")) {
			sistema.setMainFeedRule(CompositionRule.Rule3, idSessaoint);
		} else {
			throw new RegraDeComposicaoInexistenteException(
					"Regra de composição inexistente");
		}
	}

	public String getFirstCompositionRule() {
		return CompositionRule.Rule1.toString();
	}

	public String getSecondCompositionRule() {
		return CompositionRule.Rule2.toString();
	}

	public String getThirdCompositionRule() {
		return CompositionRule.Rule3.toString();
	}

	private int idSessaoCheck(String idSessao) throws SessaoInvalidaException,
			SessaoInexistenteException {
		if (idSessao == null || idSessao.isEmpty()) {
			throw new SessaoInvalidaException("Sessão inválida");
		}
		int idSessaoInt;
		try {
			idSessaoInt = Integer.parseInt(idSessao);
			sistema.getIDUsuario(idSessaoInt);
		} catch (Exception e) {
			throw new SessaoInexistenteException("Sessão inexistente");
		}
		return idSessaoInt;
	}

	public void encerrarSessao(String login) throws LoginInvalidoException,
			SessaoInexistenteException {
		sistema.encerraSessao(login);
	}

	public void encerrarSistema() {
		sistema.encerrar();
	}

	public int getIDUsuario(String idSessao) throws SessaoInexistenteException,
			SessaoInvalidaException {
		return sistema.getIDUsuario(idSessaoCheck(idSessao));
	}

	public String getFontesDeSons(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		return sistema.getFonteDeSons(idSessaoCheck(idSessao)).toString()
				.replace('[', '{').replace(']', '}').replace(" ", "");
	}

	public String getVisaoDosSons(String idSessao)
			throws SessaoInexistenteException, SessaoInvalidaException {
		return sistema.getVisaoDosSons(idSessaoCheck(idSessao)).toString()
				.replace('[', '{').replace(']', '}').replace(" ", "");
	}

	public String criarLista(String idSessao, String nome)
			throws NomeInvalidoException, SessaoInexistenteException,
			SessaoInvalidaException {
		int idSessaoint = idSessaoCheck(idSessao);
		if (nome == null || nome.isEmpty()) {
			throw new NomeInvalidoException("Nome inválido");
		}
		return String.valueOf(sistema.criarLista(idSessaoint, nome));

	}

	public void adicionarUsuario(String idSessao, String idLista,
			String idUsuario) throws NumberFormatException,
			SessaoInexistenteException, UsuarioInexistenteException,
			UsuarioInvalidoException, ListaInvalidaException,
			SessaoInvalidaException {
		int idsessaoint = idSessaoCheck(idSessao);
		if (idUsuario == null || idUsuario.isEmpty()) {
			throw new UsuarioInvalidoException("Usuário inválido");
		} else if (idLista == null || idLista.isEmpty()) {
			throw new ListaInvalidaException("Lista inválida");
		}
		sistema.adicionaUsuarioNaLista(idsessaoint, Integer.parseInt(idLista),
				Integer.parseInt(idUsuario));
	}

	public String getSonsEmLista(String idSessao, String idLista)
			throws SessaoInvalidaException, SessaoInexistenteException,
			ListaInvalidaException {
		int idSessaoInt = idSessaoCheck(idSessao);
		if (idLista == null || idLista.isEmpty()) {
			throw new ListaInvalidaException("Lista inválida");
		}
		return sistema.getSonsEmLista(idSessaoInt, Integer.parseInt(idLista))
				.toString().replace('[', '{').replace(']', '}')
				.replace(" ", "");
	}

	public int getNumFavoritosEmComum(String idSessao, String idUsuario)
			throws SessaoInvalidaException, SessaoInexistenteException,
			UsuarioInvalidoException, NumberFormatException,
			UsuarioInexistenteException {
		int idSessaoInt = idSessaoCheck(idSessao);
		if (idUsuario == null || idUsuario.isEmpty()) {
			throw new UsuarioInvalidoException("Usuário inválido");
		}
		return sistema.getNumeroDeFavoritosEmComum(idSessaoInt,
				Integer.parseInt(idUsuario));
	}

	public int getNumFontesEmComum(String idSessao, String idUsuario)
			throws SessaoInvalidaException, SessaoInexistenteException,
			UsuarioInvalidoException, NumberFormatException,
			UsuarioInexistenteException {
		int idSessaoInt = idSessaoCheck(idSessao);
		if (idUsuario == null || idUsuario.isEmpty()) {
			throw new UsuarioInvalidoException("Usuário inválido");
		}
		return sistema.getNumeroDeFontesDeSonsEmComum(idSessaoInt,
				Integer.parseInt(idUsuario));
	}

	public String getFontesDeSonsRecomendadas(String idSessao)
			throws SessaoInvalidaException, SessaoInexistenteException {
		int idSessaoInt = idSessaoCheck(idSessao);
		return sistema.getFontesDeSonsRecomendadas(idSessaoInt).toString()
				.replace('[', '{').replace(']', '}').replace(" ", "");
	}
}
