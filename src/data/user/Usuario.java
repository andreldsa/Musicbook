package data.user;

import java.util.List;

import data.som.Som;

import exceptions.user.EmailInvalidoException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;
import exceptions.user.NomeInvalidoException;

public class Usuario {
	private String nome, email;
	private Conta conta;
	private List<Som> sons;
	
	/**
	 * Construtor a partir de um nome de usuario uma conta necessaria para se conectar ao site e sua data de aniversario
	 * @param nome Nome do Usuario
	 * @param contaUsuario Conta do Usuario contendo Nickname e Senha
	 * @param dataAniversario Data de Aniversario do Usuario
	 */
	public Usuario(String nome, Conta contaUsuario, String email)  throws EmailInvalidoException, 
	 NomeInvalidoException{
		if (nome == null || nome.isEmpty()) throw  new NomeInvalidoException("Nome inválido");
		if (email == null || email.isEmpty()) throw  new EmailInvalidoException("Email inválido");
		this.nome = nome;
		this.email = email;
		this.conta = contaUsuario;
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
		return conta.getLogin();
	}

	/**
	 * Retorna o email do Usuario
	 * @return Email
	 */
	public String getEmail() {
		return this.email;
	}



}
