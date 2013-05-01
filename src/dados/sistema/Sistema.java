package dados.sistema;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.Deserializador;
import persistencia.Serializador;
import dados.som.Som;
import dados.usuario.Conta;
import dados.usuario.ContaBasica;
import dados.usuario.ListaDeUsuarios;
import dados.usuario.Usuario;
import dados.util.CompositionRule;
import dados.util.ListUtils;
import dados.util.OurTime;
import dados.util.SoundByDateComparator;
import dados.util.SoundByFavoritedPersonComparator;
import dados.util.SoundByFavoritesComparator;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SomInexistenteException;
import exceptions.system.SomInvalidoException;
import exceptions.system.TagInvalidaException;
import exceptions.user.EmailInvalidoException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.ListaInvalidaException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;
import exceptions.user.NomeInvalidoException;
import exceptions.user.UsuarioInexistenteException;
import exceptions.user.UsuarioInvalidoException;

/**
 * Classe que controla a logica do sistema.
 */
public class Sistema implements Serializable {

	private static final long serialVersionUID = 1L;

	// mapa com id de usuario para usuario
	private Map<Integer, Usuario> usuarios;
	// mapa com id de sessao para id de usuario
	private Map<Integer, Integer> sessoesAbertas;
	// mapa com os ids de sons para os sons cadastrados
	private Map<Integer, Som> sonsCadastrados;
	// mapa apontando dos logins dos usuarios para seus ids
	private Map<String, Integer> logins;
	private int contadorDeUsuarios;
	private int contadorDeSessao;
	private int contadorDeSons;
	private int contadorTags;
	private int listaDeUsuariosContador;

	private static Sistema sistema;

	public static Sistema getInstance() {
		// Já existe uma instância do sistema na execução atual
		if (sistema != null) {
			return sistema;
		}
		sistema = deserializa();
		// O sistema ainda não foi persistido
		if (sistema == null) {
			sistema = new Sistema();
		}
		return sistema;
	}

	/*
	 * Pede a classe gerenciadora de persistencia a instância do sistema que foi
	 * persistido na última execução.
	 */
	private static Sistema deserializa() {
		try {
			sistema = (Sistema) Deserializador.deserializar();
			criaThreadPersistencia();
			return sistema;
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	private Sistema() {
		this.usuarios = new HashMap<Integer, Usuario>();
		this.sessoesAbertas = new HashMap<Integer, Integer>();
		this.sonsCadastrados = new HashMap<Integer, Som>();
		this.logins = new HashMap<String, Integer>();
		this.contadorDeSessao = 0;
		this.contadorDeUsuarios = 0;
		this.contadorDeSons = 0;
		this.contadorTags = 0;

		criaThreadPersistencia();
	}

	/*
	 * Cria uma trhead que persiste o sistema a cada 10 minutos.
	 */
	private static void criaThreadPersistencia() {
		new Thread(new Runnable() {

			private static final long DEZ_MINUTOS = 600000;

			@Override
			public void run() {
				while (true) {
					persiste();
					try {
						Thread.sleep(DEZ_MINUTOS);
					} catch (InterruptedException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}).start();
	}

	/*
	 * Passa a instância do sistema atual para o gerenciador de persistencia.
	 */
	private static void persiste() {
		try {
			Serializador.serializar(sistema);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private Comparator<Integer> getCompositionRuleComparator(
			CompositionRule rule, Usuario user) {
		switch (rule) {
		case Rule1:
			return new SoundByDateComparator(sonsCadastrados);
		case Rule2:
			return new SoundByFavoritesComparator(sonsCadastrados);
		case Rule3:
			return new SoundByFavoritedPersonComparator(sonsCadastrados, user);
		default:
			return new SoundByDateComparator(sonsCadastrados);
		}
	}

	/**
	 * Construtor a partir dos dados enviados da interface
	 * 
	 * @param login
	 *            Login do Usuario
	 * @param senha
	 *            Senha do Usuario
	 * @param nome
	 *            Nome do Usuario
	 * @param email
	 *            Email do Usuario
	 * @throws LoginInvalidoException
	 * @throws EmailInvalidoException
	 * @throws NomeInvalidoException
	 * @throws LoginJaExisteException
	 * @throws EmailJaExisteException
	 */
	public void criarUsuario(String login, String senha, String nome,
			String email) throws LoginInvalidoException, NomeInvalidoException,
			EmailInvalidoException, LoginJaExisteException,
			EmailJaExisteException {
		if (getUsuarioID(login) != -1) {
			throw new LoginJaExisteException(
					"Já existe um usuário com este login");
		}
		if (emailJaUsado(email)) {
			throw new EmailJaExisteException(
					"Já existe um usuário com este email");
		}
		Conta conta = new ContaBasica(login, senha);
		Usuario user = new Usuario(nome, conta, email, ++contadorDeUsuarios);
		usuarios.put(user.getID(), user);
		logins.put(login, user.getID());
	}

	private boolean emailJaUsado(String email) {
		for (Integer ID : usuarios.keySet()) {
			if (usuarios.get(ID).getEmail().equalsIgnoreCase(email))
				return true;
		}
		return false;
	}

	/**
	 * Abri uma sessão para um usuario e retorna o ID da sessão aberta
	 * 
	 * @param login
	 *            Login do Usuario
	 * @param senha
	 *            Senha do Usuario
	 * @return ID da sessão
	 * @throws LoginInvalidoException
	 * @throws UsuarioInexistenteException
	 */
	public int abrirSessao(String login, String senha)
			throws LoginInvalidoException, UsuarioInexistenteException {
		if (login == null || login.isEmpty())
			throw new LoginInvalidoException("Login inválido");
		Usuario user = getUsuario(getUsuarioID(login));
		if (user == null) {
			throw new UsuarioInexistenteException("Usuário inexistente");
		} else if (user.autenticaConta(senha)) {
			int newSessionId = generateNewSessionId();
			sessoesAbertas.put(newSessionId, user.getID());
			return newSessionId;
		} else {
			throw new LoginInvalidoException("Login inválido");
		}
	}

	public int abrirSessaoLogadoNoFacebook(String login)
			throws LoginInvalidoException {
		Usuario user = getUsuario(getUsuarioID(login));
		if (user == null) {
			throw new LoginInvalidoException("Login inválido");
		}
		int newSessionId = generateNewSessionId();
		sessoesAbertas.put(newSessionId, user.getID());
		return newSessionId;
	}

	private int generateNewSessionId() {
		return contadorDeSessao++;
	}

	/**
	 * Posta um Som no usuario da sessao criada
	 * 
	 * @param idSessao
	 *            o id da sessao do usuario
	 * @param link
	 *            o link do som a ser postado
	 * @param dataCriacao
	 *            a data de criacao do sim
	 * @return ID do Som
	 * @throws SessaoInexistenteException
	 * @throws SomInvalidoException
	 * @throws DataDeCriacaoInvalidaException
	 */
	public int postarSom(int idSessao, String link, String dataCriacao)
			throws SessaoInexistenteException, SomInvalidoException,
			DataDeCriacaoInvalidaException {
		Usuario user = getUsuarioLogado(idSessao);
		if (link == null || link.isEmpty())
			throw new SomInvalidoException("Som inválido");
		String[] dataValores = dataCriacao.split("/");
		OurTime ourTime;
		try {
			ourTime = new OurTime(Integer.parseInt(dataValores[0]),
					Integer.parseInt(dataValores[1]),
					Integer.parseInt(dataValores[2]));
		} catch (Exception e) {
			throw new SomInvalidoException("Som inválido");
		}
		Som som = new Som(link, ourTime, ++contadorDeSons, user.getID());
		user.postaSom(som);
		sonsCadastrados.put(som.getID(), som);
		atualizaVisaoDeSonsDosSeguidores(user, som.getID());
		atualizaMainFeedDosSeguidores(user, som.getID());
		return som.getID();
	}

	private void atualizaMainFeedDosSeguidores(Usuario user, int somId) {
		for (Integer seguidor : user.getListaDeSeguidores()) {
			usuarios.get(seguidor).addSomNoFeedPrincipal(somId);
		}
	}

	private void atualizaVisaoDeSonsDosSeguidores(Usuario user, int somId) {
		for (Integer userId : user.getListaDeSeguidores()) {
			getUsuario(userId).addSomNaVisao(somId);
		}
	}

	/**
	 * O usuario da sessao dada segue o usuario com o login dado
	 * 
	 * @param idSessao
	 * @param login
	 * @throws LoginInvalidoException
	 * @throws SessaoInexistenteException
	 * @throws UsuarioInexistenteException
	 */
	public void seguirUsuario(int idSessao, String login)
			throws LoginInvalidoException, SessaoInexistenteException,
			UsuarioInexistenteException {
		if (login == null || login.isEmpty())
			throw new LoginInvalidoException("Login inválido");
		Usuario seguidor = getUsuarioLogado(idSessao);
		Usuario seguido = getUsuario(getUsuarioID(login));
		if (seguidor.getLogin().equals(login))
			throw new LoginInvalidoException("Login inválido");
		if (seguido == null) {
			throw new UsuarioInexistenteException("Login inexistente");
		}
		seguidor.addFonteDeSom(seguido.getID());
		seguido.addSeguidor(seguidor);
		seguidor.addSonsNaVisao(seguido.getPerfilMusical());
		seguidor.addSonsNoFeedSecundario(seguido.getSonsFavoritos());
		seguidor.addSonsNoFeedPrincipal(seguido.getPerfilMusical());
	}

	/**
	 * Retorna o numero de seguidores do usuario da sessao dada
	 * 
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public int getNumeroDeSeguidores(int idSessao)
			throws SessaoInexistenteException {
		Usuario user = getUsuarioLogado(idSessao);
		return user.getNumeroDeSeguidores();
	}

	/**
	 * Retorna o ID do Usuario cadastrado no sistema. IDs são numeros naturais
	 * por ordem de criacao
	 * 
	 * @param idSessao
	 *            ID da Sessao do Usuario
	 * @return ID do Usuario
	 * @throws SessaoInexistenteException
	 */
	public int getIDUsuario(int idSessao) throws SessaoInexistenteException {
		return getUsuarioLogado(idSessao).getID();
	}

	/**
	 * Retorna a lista de seguidores do usuario da sessao dada
	 * 
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getListaDeSeguidores(int idSessao)
			throws SessaoInexistenteException {
		return getUsuarioLogado(idSessao).getListaDeSeguidores();
	}

	/**
	 * Retorna uma lista contendo os IDs dos Usuarios que pertencem a sua Fonte
	 * de Sons
	 * 
	 * @param idSessao
	 *            ID da Sessao do Usuario
	 * @return Lista de IDs
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getFonteDeSons(int idSessao)
			throws SessaoInexistenteException {
		return getUsuarioLogado(idSessao).getFontesDeSons();
	}

	/**
	 * Retorna o usuario logado na sessao dada
	 * 
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public Usuario getUsuarioLogado(int idSessao)
			throws SessaoInexistenteException {
		Usuario user = usuarios.get(sessoesAbertas.get(idSessao));
		if (user == null) {
			throw new SessaoInexistenteException("Sessão inexistente");
		}
		return user;
	}

	/**
	 * Retorna uma lista contendo os IDs dos sons do Usuario logado na Sessao
	 * passada
	 * 
	 * @param idSessao
	 *            ID da Sessao do Usuario
	 * @return Lista de IDs
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getPerfilMusical(int idSessao)
			throws SessaoInexistenteException {
		Usuario user = getUsuarioLogado(idSessao);
		return user.getPerfilMusical();
	}

	/**
	 * Encerra a sessao com o ID passado como parametro.
	 * 
	 * @param iD_Sessao
	 *            da sessao a ser encerrada
	 */
	public void encerraSessao(int iD_Sessao) {
		sessoesAbertas.remove(iD_Sessao);
		persiste();
	}

	/**
	 * Retorna o usuario com o ID dado
	 * 
	 * @param userID
	 * @return usuario logado
	 */
	public Usuario getUsuario(int userID) {
		return usuarios.get(userID);
	}

	/**
	 * Retorna o som com o dado ID
	 * 
	 * @param somID
	 * @return
	 */
	public Som getSom(int somID) {
		return sonsCadastrados.get(somID);
	}

	/**
	 * Limpa todos os dados do sistema
	 */
	public void zerarSistema() {
		this.usuarios = new HashMap<Integer, Usuario>();
		this.sessoesAbertas = new HashMap<Integer, Integer>();
		this.sonsCadastrados = new HashMap<Integer, Som>();
		this.logins = new HashMap<String, Integer>();
		this.contadorDeSessao = 0;
		this.contadorDeUsuarios = 0;
		this.contadorDeSons = 0;
	}

	/**
	 * Retorna uma lista contendo os ids dos sons da visão de sons do usuario
	 * que representado pelo id de sessao passado
	 * 
	 * @param idSessao
	 *            ID da sessao do usuario
	 * @return Lista dos Sons da Visao de Sons
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getVisaoDosSons(int idSessao)
			throws SessaoInexistenteException {
		return getUsuarioLogado(idSessao).getVisaoDeSons();
	}

	/**
	 * O usuario da sessao passada favorita o som dado
	 * 
	 * @param idSessao
	 *            o id da sessao do usuario
	 * @param idSom
	 *            o id do som
	 * @throws SessaoInexistenteException
	 * @throws SomInvalidoException
	 */
	public void favoritarSom(int idSessao, int idSom)
			throws SessaoInexistenteException, SomInexistenteException {
		Usuario user = getUsuarioLogado(idSessao);
		if (!sonsCadastrados.containsKey((Integer) idSom))
			throw new SomInexistenteException("Som inexistente");
		user.favoritaSom(idSom);
		user.atualizaFavoritagensDoUsuario(sonsCadastrados.get((Integer) idSom)
				.getPosterId());
		getSom(idSom).incrementaFavoritagens();
		atualizaFeedsSecundarios(user, idSom);
	}

	private void atualizaFeedsSecundarios(Usuario user, int idSom) {
		for (int seguidorId : user.getListaDeSeguidores()) {
			getUsuario(seguidorId).addSomNoFeedSecundario(idSom);
		}

	}

	/**
	 * Pesquisa usuario pelo nome;
	 * 
	 * @param nome
	 *            String
	 * @return usuario Usuario encontrado.
	 */
	public List<Usuario> pesquisaUsuario(String nome) {
		nome = nome.toLowerCase();
		List<Usuario> resultado = new ArrayList<Usuario>();
		for (Usuario use : usuarios.values()) {
			if (use.getNome().toLowerCase().contains(nome)) {
				resultado.add(use);
			}
		}
		return resultado;
	}

	/**
	 * Retorna o feed extra do usuario da sessao dada
	 * 
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getFeedExtra(int idSessao)
			throws SessaoInexistenteException {
		return getUsuarioLogado(idSessao).getFeedExtra();
	}

	/**
	 * Retorna os sons favoritos do usuario da sessao dada
	 * 
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getSonsFavoritos(int idSessao)
			throws SessaoInexistenteException {
		return getUsuarioLogado(idSessao).getSonsFavoritos();
	}

	/**
	 * Retorna o Feed principal do usuario da sessao
	 * 
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getMainFeed(int idSessao)
			throws SessaoInexistenteException {
		Usuario user = getUsuarioLogado(idSessao);
		reorganizaMainFeed(user);
		return user.getFeedPrincipal();
	}

	/**
	 * Altera a regra de organizacao do feed principal
	 * 
	 * @param rule
	 * @param idSessao
	 * @throws SessaoInexistenteException
	 */
	public void setMainFeedRule(CompositionRule rule, int idSessao)
			throws SessaoInexistenteException {
		Usuario user = getUsuarioLogado(idSessao);
		user.setRule(rule);
		reorganizaMainFeed(user);
	}

	private void reorganizaMainFeed(Usuario user) {
		Collections.sort(user.getFeedPrincipal(),
				getCompositionRuleComparator(user.getRule(), user));
	}

	/**
	 * Retorna o ID do Usuario a partir do login
	 * 
	 * @param login
	 *            Login do Usuario
	 * @return ID do Usuario
	 * @throws LoginInvalidoException
	 */
	public int getUsuarioID(String login) throws LoginInvalidoException {
		if (login == null || login.isEmpty())
			throw new LoginInvalidoException("Login inválido");
		if (logins.get(login) == null)
			return -1;
		return logins.get(login);
	}

	/**
	 * Retorna o nome do usuario a partir do ID
	 * 
	 * @param usuarioID
	 *            ID do Usuario
	 * @return Nome do Usuario
	 */
	public String getNomeUsuario(int usuarioID) {
		Usuario user = getUsuario(usuarioID);
		if (user != null)
			return user.getNome();
		return null;
	}

	/**
	 * Retorna o email do usuario a partir do ID
	 * 
	 * @param usuarioID
	 *            ID do Usuario
	 * @return Email do Usuario
	 */
	public String getEmailUsuario(int usuarioID) {
		Usuario user = getUsuario(usuarioID);
		if (user != null)
			return user.getEmail();
		return null;
	}

	/**
	 * Retorna a data de postagem do som a partir do ID
	 * 
	 * @param idSom
	 *            ID do Som
	 * @return Data de Postagem do Som
	 */
	public String getDataSom(Integer idSom) {
		Som som = getSom(idSom);
		if (som != null)
			return som.getDataCriacao().getDataToString();
		return null;
	}

	/**
	 * Verifica se um usuario segue o outro.
	 * 
	 * @param idSessaoSeguidor
	 * @param idSeguido
	 * @return true se o usuario da de sessao e seguidor do usuario seguido.
	 * @throws SessaoInexistenteException
	 */
	public boolean isSeguidor(Integer idSessaoSeguidor, Integer idSeguido)
			throws SessaoInexistenteException {
		return getFonteDeSons(idSessaoSeguidor).contains(idSeguido);
	}

	/**
	 * Encerra o sistema
	 */
	public void encerrar() {
		persiste();
		zerarSistema();
	}

	/**
	 * Finaliza uma sessao
	 * 
	 * @param login
	 *            login do usuario
	 * @throws SessaoInexistenteException
	 */
	public void encerraSessao(String login) throws SessaoInexistenteException {
		encerraSessao(getUsuarioIdByLogin(login));
		persiste();

	}

	private int getUsuarioIdByLogin(String login)
			throws SessaoInexistenteException {
		for (Integer id_sessao : sessoesAbertas.keySet()) {
			if (getUsuarioLogado(id_sessao).getLogin().equals(login))
				return id_sessao;
		}
		return -1;
	}

	/**
	 * Cria uma lista de usuarios
	 * 
	 * @param iDSessao
	 *            a sessao do usuario logado
	 * @param nomeDaLista
	 *            o nome da lista de usuarios
	 * @return O id da lista de usuarios
	 * @throws SessaoInexistenteException
	 * @throws NomeInvalidoException
	 */
	public int criarLista(int iDSessao, String nomeDaLista)
			throws SessaoInexistenteException, NomeInvalidoException {
		Usuario usuario = getUsuarioLogado(iDSessao);
		usuario.criaListaDeUsuario(listaDeUsuariosContador, nomeDaLista);
		return listaDeUsuariosContador++;
	}

	/**
	 * Adiciona um usuario na lista de usuarios com o id passado
	 * 
	 * @param iDSessao
	 *            a sessao do usuario logado
	 * @param iDLista
	 *            o id da lista
	 * @param idUsuario
	 *            o id do ususario a ser adiocionado
	 * @throws SessaoInexistenteException
	 * @throws UsuarioInexistenteException
	 * @throws UsuarioInvalidoException
	 * @throws ListaInvalidaException
	 */
	public void adicionaUsuarioNaLista(int iDSessao, int iDLista, int idUsuario)
			throws SessaoInexistenteException, UsuarioInexistenteException,
			UsuarioInvalidoException, ListaInvalidaException {
		Usuario donoDalista, adicionado;
		donoDalista = getUsuarioLogado(iDSessao);
		adicionado = getUsuario(idUsuario);
		if (adicionado == null) {
			throw new UsuarioInexistenteException("Usuário inexistente");
		} else if (adicionado.equals(donoDalista)) {
			throw new UsuarioInvalidoException(
					"Usuário não pode adicionar-se a própria lista");
		}
		donoDalista.adicionaNaListaDeUsuarios(iDLista, idUsuario);

	}

	/**
	 * Retorna os sons de uma lista de usuarios
	 * 
	 * @param idSessao
	 *            a sessao atual
	 * @param idLista
	 *            o id da lista
	 * @return
	 * @throws SessaoInexistenteException
	 * @throws ListaInvalidaException
	 */
	public List<Integer> getSonsEmLista(int idSessao, int idLista)
			throws SessaoInexistenteException, ListaInvalidaException {
		Usuario usuario = getUsuarioLogado(idSessao);
		return getSonsDaLista(usuario.getListaDeUsuarios(idLista));
	}

	/**
	 * Retorna os sons de uma ListaDeUsuarios
	 * 
	 * @param listaDeUsuarios
	 *            a lista a ser passada
	 * @return
	 */
	private List<Integer> getSonsDaLista(ListaDeUsuarios listaDeUsuarios) {
		List<Integer> lista = new ArrayList<Integer>();
		for (Integer userId : listaDeUsuarios.getUsuarios()) {
			lista.addAll(getUsuario(userId).getPerfilMusical());
		}
		return lista;
	}

	/**
	 * Retorna o numero de favoritos em comum entre o usuario da sessao e o
	 * usuario passado
	 * 
	 * @param idSessao
	 *            id da sessao
	 * @param idUsuario
	 *            id do usuario
	 * @return
	 * @throws SessaoInexistenteException
	 * @throws UsuarioInexistenteException
	 */
	public int getNumeroDeFavoritosEmComum(int idSessao, int idUsuario)
			throws SessaoInexistenteException, UsuarioInexistenteException {
		Usuario logado, outro;
		logado = getUsuarioLogado(idSessao);
		outro = getUsuario(idUsuario);
		if (outro == null) {
			throw new UsuarioInexistenteException("Usuário inexistente");
		}
		return ListUtils.elementosEmComum(outro.getSonsFavoritos(),
				logado.getSonsFavoritos());
	}

	/**
	 * Retorna o numero de fontes de sons em comum entre o usuario da sessao e o
	 * usuario passado
	 * 
	 * @param idSessao
	 *            id da sessao
	 * @param idUsuario
	 *            id do usuario
	 * @return
	 * @throws SessaoInexistenteException
	 * @throws UsuarioInexistenteException
	 */
	public int getNumeroDeFontesDeSonsEmComum(int idSessao, int idUsuario)
			throws SessaoInexistenteException, UsuarioInexistenteException {
		Usuario logado, outro;
		logado = getUsuarioLogado(idSessao);
		outro = getUsuario(idUsuario);
		if (outro == null) {
			throw new UsuarioInexistenteException("Usuário inexistente");
		}
		return ListUtils.elementosEmComum(outro.getFontesDeSons(),
				logado.getFontesDeSons());
	}

	/**
	 * Retorna a lista com os ids das fontes de sons recomendadas para o usuario
	 * da sessao
	 * 
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getFontesDeSonsRecomendadas(int idSessao)
			throws SessaoInexistenteException {
		Usuario logado = getUsuarioLogado(idSessao);
		List<Integer> recomendados = getUsuariosRecomendaveis(logado);
		Collections.sort(recomendados, new ComparadorDeRecomendacoes(logado,
				usuarios));
		return recomendados;
	}

	private List<Integer> getUsuariosRecomendaveis(Usuario logado) {
		List<Integer> recomendaveis = new ArrayList<Integer>();
		for (Usuario usuario : usuarios.values()) {
			if (!(usuario.equals(logado)
					|| logado.getFontesDeSons().contains(usuario.getID()) || (usuario
					.getSonsFavoritos().isEmpty() && usuario.getFontesDeSons()
					.isEmpty()))) {
				recomendaveis.add(usuario.getID());
			}
		}
		return recomendaveis;
	}

	public int criarTag(int idSessao, String tag) throws TagInvalidaException,
			SessaoInexistenteException {
		if (tag == null || tag.isEmpty()) {
			throw new TagInvalidaException("Tag inválida");
		}
		return getUsuarioLogado(idSessao).criarTag(tag, contadorTags++);
	}

	public void postarTagNoSom(int idSessao, String tag, int idSom)
			throws SessaoInexistenteException, SomInvalidoException,
			TagInvalidaException {
		Usuario user = getUsuarioLogado(idSessao);
		Som som = getSom(idSom);
		if (user.temTag(tag)) {
			if (som != null) {
				som.adicionaTag(user.getIdTag(tag));
			} else {
				throw new SomInvalidoException("Som inválido");
			}
		} else {
			throw new TagInvalidaException("Tag inexistente");
		}
	}

	public List<Integer> getListaTagsEmSom(int idSessao, int idSom)
			throws SessaoInexistenteException, SomInvalidoException {
		getUsuarioLogado(idSessao);
		Som som = getSom(idSom);
		if (som == null) {
			throw new SomInvalidoException("Som inválido");
		}
		return som.getListaTags();
	}

	public String getNomeTag(int idSessao, int idTag)
			throws SessaoInexistenteException, TagInvalidaException {
		Usuario user = getUsuarioLogado(idSessao);
		return user.getTagNome(idTag);
	}

	public List<Integer> getTagsDisponiveis(int idSessao)
			throws SessaoInexistenteException {
		Usuario user = getUsuarioLogado(idSessao);
		return user.getTagsDisponiveis();
	}

	public void reiniciar() {
		sistema = deserializa();
	}
}