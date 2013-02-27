package data.user;

public interface Conta {
	
	
	/**
	 * Autentica uma conta se o Object senha passado como parametro for aceito pelo sistema 
	 * @param senha Senha
	 * @return True se for autenticado
	 */
	public boolean autentica(Object senha);
	
	/**
	 * Altera a senha da cpmta
	 * @param senha Senha Antiga
	 * @param novaSenha Senha Nova
	 * @return True se a alteração for feita
	 */
	public boolean alteraSenha(Object senha, Object novaSenha);
	
	/**
	 * Retorna o login da conta
	 * @return Login
	 */
	public String getLogin();
}
