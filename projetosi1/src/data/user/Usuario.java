package data.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.som.Som;

import exceptions.user.EmailInvalidoException;
import exceptions.user.NomeInvalidoException;

public class Usuario {
	private String nome;
	private Conta conta;
	private String email;
	private List<Integer> fontesDeSons;
	private Map<Integer,Som> sons;
	private int ID;
	private List<Integer> perfilMusical;


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
		this.perfilMusical = new ArrayList<Integer>();
		this.sons = new HashMap<Integer, Som>();
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

	public void addFonteDeSom(int IdFonte){
		getFontesDeSons().add(IdFonte);
	}

	public List<Integer> getFontesDeSons() {
		return fontesDeSons;
	}
	
	public Conta getConta(){
		return conta;
	}
	
	public boolean autenticaConta(Object senha){
		return getConta().autentica(senha);
	}
	public int getID() {
		return ID;
	}
	public void postaSom(Som som){
		sons.put(som.getID(), som);
		perfilMusical.add(som.getID());
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
	public List<Integer> getPerfilMusical() {
		return perfilMusical;
	}
	

}
