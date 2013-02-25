package data.user;

import exceptions.user.LoginInvalidoException;
import exceptions.user.NomeInvalidoException;


public class ContaBasica implements Conta {

	private String login,senha;
	/**
	 * Construtor a partir de uma String que representa um numero e uma String que representa uma senha
	 * @param login Nickname
	 * @param senha Senha
	 * @throws LoginInvalidoException 
	 */
	public ContaBasica(String login, String senha) throws LoginInvalidoException {
		if (login == null || login.isEmpty()) throw  new LoginInvalidoException("Login inválido");
		this.login = login;
		this.senha = senha;
	}


	@Override
	public boolean autentica(Object senha) {
		return this.senha.equals(senha);
	}

	@Override
	public boolean alteraSenha(Object senha, Object novaSenha) {
		if(autentica(senha)){
			this.senha = (String)novaSenha;
			return true;
		}
		return false;
	}

	@Override
	public String getLogin() {
		return login;
	}

}
