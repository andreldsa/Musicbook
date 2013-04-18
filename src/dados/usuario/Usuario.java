package dados.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dados.som.Som;
import dados.util.CompositionRule;
import dados.util.UserAlphabeticalComparator;
import exceptions.user.EmailInvalidoException;
import exceptions.user.ListaInvalidaException;
import exceptions.user.NomeInvalidoException;
import exceptions.user.UsuarioInvalidoException;

/**
 * Classa que representa um Usuário no sistema.
 */
public class Usuario {
	private String nome;
	private Conta conta;
	private String email;
	private List<Integer> fontesDeSons;
	private int ID;
	private List<Som> sons;
	private List<Usuario> seguidores;
	private List<Integer> visaoDosSons;
	private List<Integer> sonsFavoritos;
	private List<Integer> feedSecundario;
	private List<Integer> feedPrincipal;
	// Mapa que indica a quantidade de sons que favoritei(valor) de um dado
	// usuario(key);
	private Map<Integer, Integer> favoritagensDoUsuario;
	private Map<Integer, ListaDeUsuarios> listasDeUsuarios;
	private CompositionRule rule;

	/**
	 * Construtor a partir de um nome de usuario uma conta necessaria para se
	 * conectar ao site e seu email
	 * 
	 * @param nome
	 *            Nome do Usuario
	 * @param contaUsuario
	 *            Conta do Usuario contendo Nickname e Senha
	 * @param email
	 *            Email do Usuario
	 * @throws EmailInvalidoException
	 */
	public Usuario(String nome, Conta contaUsuario, String email, int ID)
			throws NomeInvalidoException, EmailInvalidoException {
		if (nome == null || nome.isEmpty()) {
			throw new NomeInvalidoException("Nome inválido");
		} else {
			this.nome = nome;
		}
		this.conta = contaUsuario;
		if (email == null || email.isEmpty()) {
			throw new EmailInvalidoException("Email inválido");
		} else {
			this.nome = nome;
		}
		this.ID = ID;
		this.email = email;
		this.fontesDeSons = new ArrayList<Integer>();
		this.sons = new LinkedList<Som>();
		this.seguidores = new ArrayList<Usuario>();
		this.visaoDosSons = new LinkedList<Integer>();
		this.sonsFavoritos = new ArrayList<Integer>();
		this.feedSecundario = new ArrayList<Integer>();
		this.feedPrincipal = new ArrayList<Integer>();
		this.favoritagensDoUsuario = new HashMap<Integer, Integer>();
		this.listasDeUsuarios = new HashMap<Integer, ListaDeUsuarios>();
		this.setRule(CompositionRule.Rule1);
	}

	/**
	 * Retorna o nome do Usuario
	 * 
	 * @return Nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Retorna o login do Usuario
	 * 
	 * @return Login
	 */
	public String getLogin() {
		return this.conta.getLogin();
	}

	/**
	 * Retorna o email do Usuario
	 * 
	 * @return Email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Adiciona uma fonte de som ao usuario
	 * 
	 * @param IdFonte
	 */
	public void addFonteDeSom(int IdFonte) {
		getFontesDeSons().add(IdFonte);
	}

	/**
	 * Retorna as fontes de sons do usuario
	 * 
	 * @return
	 */
	public List<Integer> getFontesDeSons() {
		return fontesDeSons;
	}

	/**
	 * Retorna a conta do Usuario
	 * 
	 * @return
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * Autentica a Conta do usuario
	 * 
	 * @param senha
	 *            o objeto Senha
	 * @return
	 */
	public boolean autenticaConta(Object senha) {
		return getConta().autentica(senha);
	}

	/**
	 * Retorna o ID do usuario
	 * 
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Posta um som
	 * 
	 * @param som
	 */
	public void postaSom(Som som) {
		sons.add(0, som);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (ID != other.ID)
			return false;
		if (email == null) { // Dupla guarda, não é possivel criar email null
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;

		// FIXME Por que a igualdade entre as contas não é checada?
	}

	/**
	 * Retorna o perfil musical do usuario
	 * 
	 * @return
	 */
	public List<Integer> getPerfilMusical() {
		List<Integer> perfilMusical = new ArrayList<Integer>();
		for (int i = 0; i < sons.size(); i++) {
			perfilMusical.add(sons.get(i).getID());
		}
		return perfilMusical;
	}

	/**
	 * Addiciona um seguidor ao usuario
	 * 
	 * @param seguidor
	 */
	public void addSeguidor(Usuario seguidor) {
		seguidores.add(seguidor);
		Collections.sort(seguidores, new UserAlphabeticalComparator());
	}

	/**
	 * Retorna a quantidade de seguidores do usuario
	 * 
	 * @return
	 */
	public int getNumeroDeSeguidores() {
		return seguidores.size();
	}

	/**
	 * Retorna a lista com os ids dos seguidores do usuario
	 * 
	 * @return
	 */
	public List<Integer> getListaDeSeguidores() {
		List<Integer> listaDeSeguidores = new ArrayList<Integer>();
		for (int i = 0; i < getNumeroDeSeguidores(); i++) {
			listaDeSeguidores.add(seguidores.get(i).getID());
		}
		return listaDeSeguidores;
	}

	/**
	 * Adiciona uma lista de ids de sons na visao de sons do usuario
	 * 
	 * @param perfilMusical
	 */
	public void addSonsNaVisao(List<Integer> perfilMusical) {
		visaoDosSons.addAll(0, perfilMusical);

	}

	/**
	 * Adiciona um som na visao de sons do usuario
	 * 
	 * @param somId
	 *            o id do som
	 */
	public void addSomNaVisao(int somId) {
		visaoDosSons.add(0, somId);
	}

	/**
	 * Adiciona um som aos favoritos
	 * 
	 * @param idSom
	 *            O ID do som a ser favoritado
	 */
	public void favoritaSom(int idSom) {
		sonsFavoritos.add(0, idSom);

	}

	/**
	 * Adiciona um som no feed secundario do usuario
	 * 
	 * @param idSom
	 *            O ID do som a ser adicionado
	 */
	public void addSomNoFeedSecundario(int idSom) {
		feedSecundario.add(0, idSom);
	}

	/**
	 * Retorna os ids dos sons do feed extra do usuario
	 * 
	 * @return
	 */
	public List<Integer> getFeedExtra() {
		return feedSecundario;
	}

	/**
	 * Retorna os ids sons favoritados pelo usuario
	 * 
	 * @return
	 */
	public List<Integer> getSonsFavoritos() {
		return sonsFavoritos;
	}

	/**
	 * Retorna a visao de sons do usuario
	 * 
	 * @return
	 */
	public List<Integer> getVisaoDeSons() {
		return visaoDosSons;
	}

	public List<Integer> getFeedPrincipal() {
		return feedPrincipal;
	}

	/**
	 * Adiciona uma lista de sons no feed secundario
	 * 
	 * @param sonsFavoritos
	 */
	public void addSonsNoFeedSecundario(List<Integer> sonsFavoritos) {
		feedSecundario.addAll(0, sonsFavoritos);
	}

	/**
	 * Atualiza a quantidade de sons que o usuario favoritou do usuario dado
	 * 
	 * @param userId
	 */
	public void atualizaFavoritagensDoUsuario(Integer userId) {
		// adicionado e apenas seria modificado uma variavel representativa
		if (favoritagensDoUsuario.containsKey(userId)) {
			favoritagensDoUsuario.put(userId,
					favoritagensDoUsuario.get(userId) + 1);
		} else {
			favoritagensDoUsuario.put(userId, 1);
		}
	}

	/**
	 * Retorna o numero de vezes que o Usuario favoritou um som do usuario que
	 * tem o dado Id
	 * 
	 * @param userId
	 *            o Id do usuario que teve sons favoritados
	 * @return o numero de vezes
	 */
	public Integer getFavoritagensDoUsuario(Integer userId) {
		return favoritagensDoUsuario.get(userId) == null ? 0
				: favoritagensDoUsuario.get(userId);

	}

	/**
	 * Retorna a regra de organizacao do feed principal
	 * 
	 * @return
	 */
	public CompositionRule getRule() {
		return rule;
	}

	/**
	 * Seta a regra de organizacao do feed principal
	 * 
	 * @param rule
	 */
	public void setRule(CompositionRule rule) {
		this.rule = rule;
	}

	/**
	 * Adiciona o som com o dado id no feed principal
	 * 
	 * @param somId
	 */
	public void addSomNoFeedPrincipal(int somId) {
		this.feedPrincipal.add(0, somId);
	}

	/**
	 * Adiciona uma lista de sons no Feed principal
	 * 
	 * @param sons
	 *            a lista de Id dos sons
	 */
	public void addSonsNoFeedPrincipal(List<Integer> sons) {
		this.feedPrincipal.addAll(0, sons);
	}

	public void criaListaDeUsuario(int IdListaDeUsuarios, String nomeDaLista)
			throws NomeInvalidoException {
		if (nomeDaLista == null || nomeDaLista.isEmpty()) {
			throw new NomeInvalidoException("Nome Inválido");
		} else if (nomeDeListaJaUsado(nomeDaLista)) {
			throw new NomeInvalidoException("Nome já escolhido");
		}
		listasDeUsuarios.put(IdListaDeUsuarios, new ListaDeUsuarios(
				IdListaDeUsuarios, nomeDaLista));
	}

	private boolean nomeDeListaJaUsado(String nomeDaLista) {
		for (ListaDeUsuarios lista : listasDeUsuarios.values()) {
			if (lista.getNome().equals(nomeDaLista)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adiciona o usuario na lista de usuarios
	 * 
	 * @param idLista
	 * @param idUsuario
	 * @throws ListaInvalidaException
	 * @throws UsuarioInvalidoException
	 */
	public void adicionaNaListaDeUsuarios(int idLista, int idUsuario)
			throws ListaInvalidaException, UsuarioInvalidoException {
		if (!listasDeUsuarios.containsKey(idLista)) {
			throw new ListaInvalidaException("Lista inexistente");
		}
		ListaDeUsuarios lista = listasDeUsuarios.get(idLista);
		if (lista.jaTemUsuario(idUsuario)) {
			throw new UsuarioInvalidoException("Usuário já existe nesta lista");
		}
		lista.addUsuario(idUsuario);
	}

	public ListaDeUsuarios getListaDeUsuarios(int idLista)
			throws ListaInvalidaException {
		if (!listasDeUsuarios.containsKey(idLista)) {
			throw new ListaInvalidaException("Lista inexistente");
		}
		return listasDeUsuarios.get(idLista);
	}
	
	public Collection<ListaDeUsuarios> getListaDeUsuarios() {
		return listasDeUsuarios.values();
	}
}