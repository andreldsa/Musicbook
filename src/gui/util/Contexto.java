package gui.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Classe que fornece servicos do FacesContext.
 */
public class Contexto {
	
	/**
	 * Enum com os nomes das variáveis do contexto.
	 */
	public enum Variaveis {
		SISTEMA("sistema"),
		LOGIN("login"), 
		PERFIL("perfil"), 
		USER_PESQ("userPesquisa");
		
		private String nome;
		
		Variaveis(String nome) {
			this.setNome(nome);
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}
	}
	/**
	 * Recupera uma instancia do Contexto da aplicacao.
	 */
	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	/**
	 * Inseri um objeto no contexto passando como parametro
	 * uma string Name e o proprio objeto.
	 * @param obj Objeto
	 * @param name Nome do Objeto no contexto
	 */
	public static void insertInContext(Object obj, String name) {
		getExternalContext().getApplicationMap().put(name, obj);
	}
	
	/**
	 * Recupera um objeto no contexto passando como parametro
	 * uma string Name.
	 * @param name
	 * @return objeto recuperado Object
	 */
	public static Object getInContext(String name) {
		return getExternalContext().getApplicationMap().get(name);
	}
}
