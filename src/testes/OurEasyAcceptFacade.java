package testes;

import java.util.List;

import data.system.Sistema;

public class OurEasyAcceptFacade {
	
	private final String NOME = "nome";
	private final String EMAIL = "email";
	
	private final String DATACRIACAO = "dataCriacao";
	
	public OurEasyAcceptFacade(Sistema sistema) {
		// TODO Auto-generated constructor stub
	}

	public void zerarSistema(){
		// TODO
	}
	
	public void criarUsuario(String login, String senha, String nome, String email){
		// TODO
	}
	
	public int abrirSessao(String login, String senha){
		// TODO
		return -1;
	}
	
	public String getAtributoUsuario(String login, String atributo){
		if(atributo == NOME){
			// TODO
		} else if(atributo == EMAIL){
			// TODO
		}
		return null;
	}
	
	public List<Integer> getPerfilMusical(int idSessao){
		//TODO
		return null;
	}
	
	public int postarSom(String idSessao, String link, String dataCriacao){
		//TODO
		return -1;
	}
	
	public int postarSom(int idSessao, String link, String dataCriacao){
		//TODO
		return -1;
	}
	
	
	public String getAtributoSom(String idSom, String atributo){
		if(atributo == DATACRIACAO){
			//TODO
		}
		
		return null;
	}

	public int seguirUsuario(String idSessao, String login) {
		// TODO Auto-generated method stub
		return -1;
	}

	public List<Integer> getListaDeSeguidores(String idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumeroDeSeguidores(String idSessao) {
		// TODO Auto-generated method stub
		return -1;
	}

	public List<Integer> getFonteDeSons(String idSessao) {
		// TODO Auto-generated method stub
		return null;
	}
}
