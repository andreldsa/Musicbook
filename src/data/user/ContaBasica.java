package data.user;


public class ContaBasica implements Conta {

	
	/**
	 * Construtor a partir de uma String que representa um numero e uma String que representa uma senha
	 * @param nickname Nickname
	 * @param senha Senha
	 */
	public ContaBasica(String nickname, String senha) {
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean autentica(Object senha) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alteraSenha(Object senha, Object novaSenha) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}

}
