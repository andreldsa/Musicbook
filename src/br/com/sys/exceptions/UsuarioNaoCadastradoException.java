package br.com.sys.exceptions;

public class UsuarioNaoCadastradoException extends Exception {
	
	public static final String MENSAGEM = "Usuário não cadastrado.";

	private static final long serialVersionUID = 6370689014144757626L;
	
	public UsuarioNaoCadastradoException() {
		
	}

}
