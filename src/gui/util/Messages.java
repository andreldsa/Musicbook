package gui.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Classe que fornece servicos de mensagens na tela.
 */
public class Messages{
	
	/**
	 * Lanca uma mensagem de erro
	 * @param msg Mensagem de erro
	 */
	public static void addMsgErro(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						msg, ""));
	}
	
	/**
	 * Lanca uma mensagem de sucesso
	 * @param msg Mensagem de sucesso
	 */
	public static void addMsgSucesso(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, 
						msg, ""));
	}

}
