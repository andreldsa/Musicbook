package gui.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Contexto {

	private static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public static void insertInContext(Object obj, String name) {
		getExternalContext().getApplicationMap().put(name, obj);
	}
	
	public static Object getInContext(String name) {
		return getExternalContext().getApplicationMap().get(name);
	}
}
