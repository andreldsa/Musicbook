package data.user;

import java.util.ArrayList;
import java.util.List;

import exceptions.user.EmailInvalidoException;
import exceptions.user.NomeInvalidoException;

public class Usuario {
	private String nome;
	private Conta conta;
	private String email;
	private List<Integer> solicitacoesDeAmizade;
	private List<Integer> fontesDeSons;


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
		this.solicitacoesDeAmizade = new ArrayList<Integer>();
		this.fontesDeSons = new ArrayList<Integer>();
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
	
	public void addSolicitacaoDeAmizade(int solicitacaoId){
		getSolicitacoesDeAmizade().add(solicitacaoId);
	}

	public List<Integer> getSolicitacoesDeAmizade() {
		return solicitacoesDeAmizade;
	}

	public List<Integer> getFontesDeSons() {
		return fontesDeSons;
	}
	
	public Conta getConta(){
		return conta;
	}

}
