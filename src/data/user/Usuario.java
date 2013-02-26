package data.user;

import exceptions.user.EmailInvalidoException;
import exceptions.user.NomeInvalidoException;

public class Usuario {
	private String nome;
	private Conta conta;
	private String email;


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



}
