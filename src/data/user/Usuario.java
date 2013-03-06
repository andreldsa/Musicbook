package data.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import data.som.Som;
import data.util.UserAlphabeticalComparator;

import exceptions.user.EmailInvalidoException;
import exceptions.user.NomeInvalidoException;

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


	/**
	 * Construtor a partir de um nome de usuario uma conta necessaria para se conectar ao site e sua data de aniversario
	 * @param nome Nome do Usuario
	 * @param contaUsuario Conta do Usuario contendo Nickname e Senha
	 * @param dataAniversario Data de Aniversario do Usuario
	 * @throws EmailInvalidoException 
	 */
	public Usuario(String nome, Conta contaUsuario, String email) throws NomeInvalidoException, EmailInvalidoException {
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
		this.email = email;
		this.fontesDeSons = new ArrayList<Integer>();
		this.sons = new LinkedList<Som>();
		this.seguidores = new ArrayList<Usuario>();
		this.visaoDosSons = new LinkedList<Integer>();
		this.sonsFavoritos = new ArrayList<Integer>();
		this.feedSecundario = new ArrayList<Integer>();
		this.ID = 0;
	}
	/**
	 * 
	 * @param nome nome do usuario
	 * @param contaUsuario conta do usuario
	 * @param email email do usuario
	 * @param ID ID do usuario
	 * @throws NomeInvalidoException
	 * @throws EmailInvalidoException
	 */
	public Usuario(String nome, Conta contaUsuario, String email, int ID) throws NomeInvalidoException, EmailInvalidoException{
		this(nome, contaUsuario, email);
		this.ID = ID;
	}

	/**
	 * Retorna o nome do Usuario
	 * @return Nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Retorna o login do Usuario
	 * @return Login
	 */
	public String getLogin() {
		return this.conta.getLogin();
	}

	/**
	 * Retorna o email do Usuario
	 * @return Email
	 */
	public String getEmail() {
		return this.email;
	}
	/**
	 * Adiciona uma fonte de som ao usuario
	 * @param IdFonte
	 */
	public void addFonteDeSom(int IdFonte){
		getFontesDeSons().add(0,IdFonte);
	}
	/**
	 * Retorna as fontes de sons do usuario
	 * @return
	 */
	public List<Integer> getFontesDeSons() {
		return fontesDeSons;
	}
	/**
	 * Retorna a conta do Usuario
	 * @return
	 */
	public Conta getConta(){
		return conta;
	}
	/**
	 * Autentica a Conta do usuario
	 * @param senha o objeto Senha
	 * @return
	 */
	public boolean autenticaConta(Object senha){
		return getConta().autentica(senha);
	}
	/**
	 * Retorna o ID do usuario
	 * @return
	 */
	public int getID() {
		return ID;
	}
	/**
	 * Posta um som
	 * @param som
	 */
	public void postaSom(Som som){
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	/**
	 * Retorna o perfil musical do usuario
	 * @return
	 */
	public List<Integer> getPerfilMusical() {
		List<Integer> perfilMusical = new ArrayList<Integer>();
		for(int i = 0;i<sons.size(); i++){
			perfilMusical.add(sons.get(i).getID());
		}
		return perfilMusical;
	}
	/**
	 * Addiciona um seguidor ao usuario
	 * @param seguidor
	 */
	public void addSeguidor(Usuario seguidor) {
		seguidores.add(seguidor);
		Collections.sort(seguidores, new UserAlphabeticalComparator());
	}
	/**
	 * Retorna a quantidade de seguidores do usuario
	 * @return
	 */
	public int getNumeroDeSeguidores() {
		return seguidores.size();
	}
	/**
	 * Retorna a lista com os ids dos seguidores do usuario
	 * @return
	 */
	public List<Integer> getListaDeSeguidores() {
		List<Integer> listaDeSeguidores = new ArrayList<Integer>();
		for(int i = 0; i < getNumeroDeSeguidores(); i++){
			listaDeSeguidores.add(seguidores.get(i).getID());
		}
		return listaDeSeguidores;
	}
	/**
	 * Adiciona uma lista de ids de sons na visao de sons do usuario
	 * @param perfilMusical
	 */
	public void addSonsNaVisao(List<Integer> perfilMusical) {
		visaoDosSons.addAll(0, perfilMusical);

	}
	/**
	 * Adiciona um som na visao de sons do usuario
	 * @param somId o id do som
	 */
	public void addSomNaVisao(int somId) {
		visaoDosSons.add(0, somId);

	}
	/**
	 * Adiciona um som aos favoritos
	 * @param idSom O ID do som a ser favoritado
	 */
	public void favoritaSom(int idSom) {
		sonsFavoritos.add(0, idSom);
	}
	/**
	 * Adiciona um som no feed secundario do usuario
	 * @param idSom O ID do som a ser adicionado
	 */
	public void addSomNoFeedSecundario(int idSom) {
		feedSecundario.add(0, idSom);
	}
	/**
	 * Retorna os ids dos sons do feed extra do usuario
	 * @return
	 */
	public List<Integer> getFeedExtra() {
		return feedSecundario;		
	}
	/**
	 * Retorna os ids sons favoritados pelo usuario
	 * @return
	 */
	public List<Integer> getSonsFavoritos() {
		return sonsFavoritos;
	}


}
