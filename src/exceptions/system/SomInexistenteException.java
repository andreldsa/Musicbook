package exceptions.system;

@SuppressWarnings("serial")
public class SomInexistenteException extends Exception {
	public SomInexistenteException(String msg){
		super(msg);
	}
}
