package gui.util;

public class ValidaCampo {
	
	private static final String STRING_VAZIA = "";

	/**
	 * Valida um campo String e lan�a uma Mensagem de Erro caso
	 * esse seja null ou vazio.
	 * 
	 * @param campo Valor do Campo String
	 * @param msgErro Mensagem de Erro
	 * @return true se o campo � v�lido, false caso contr�rio.
	 */
	public static boolean valida(String campo, String msgErro) {
		if (campo == null || campo.equals(STRING_VAZIA)) {
			Messages.addMsgErro(msgErro);
			return false;
		}
		return true;
	}
}
