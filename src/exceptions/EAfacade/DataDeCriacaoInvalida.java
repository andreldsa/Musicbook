package exceptions.EAfacade;

public class DataDeCriacaoInvalida extends Exception{
	private Exception exception;

	public DataDeCriacaoInvalida(Exception exc){
		super("Data de Cria��o inv�lida");
		this.exception = exc;
	}

}
