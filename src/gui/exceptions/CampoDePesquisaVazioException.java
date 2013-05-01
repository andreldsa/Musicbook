package gui.exceptions;

public class CampoDePesquisaVazioException extends Exception{

	private static final long serialVersionUID = -4709716082864033195L;
	private static final String MSG = "Campo de pesquisa vazio.";
	
	public CampoDePesquisaVazioException() {
		super(MSG);
	}

}
