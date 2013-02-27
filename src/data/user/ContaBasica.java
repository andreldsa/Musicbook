package data.user;

import exceptions.user.LoginInvalidoException;
import exceptions.user.NomeInvalidoException;


public class ContaBasica implements Conta {
	private String nickname;
	private String senha;
	
	/**
	 * Construtor a partir de uma String que representa um numero e uma String que representa uma senha
	 * @param login Nickname
	 * @param senha Senha
	 * @throws LoginInvalidoException 
	 */
	public ContaBasica(String nickname, String senha) throws LoginInvalidoException {
		if (nickname == null || nickname.isEmpty()) throw  new LoginInvalidoException("Login inválido");
		this.nickname = nickname;
		this.senha = senha;
	}


	@Override
	public boolean autentica(Object senha) {
		return senha instanceof String && this.senha.equals(senha);
	}

	@Override
	public boolean alteraSenha(Object senha, Object novaSenha) {
		if(senha instanceof String && this.senha.equals(senha) && novaSenha instanceof String){
			this.senha = (String)novaSenha;
			return true;
			}
		return false;
	}

	@Override
	public String getLogin() {
		return this.nickname;
	}

}
