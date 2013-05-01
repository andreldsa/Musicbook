package dados.usuario;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ListaDeUsuarios implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private List<Integer> usuarios;
	private String nome;

	public ListaDeUsuarios(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		usuarios = new LinkedList<Integer>();
	}

	/**
	 * Retorna o nome da lista
	 * 
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Retorna os usuarios da lista
	 * 
	 * @return
	 */
	public List<Integer> getUsuarios() {
		return this.usuarios;
	}

	/**
	 * Adiciona um usuario na lista
	 * 
	 * @param userId
	 */
	public void addUsuario(Integer userId) {
		usuarios.add(0, userId);
	}

	/**
	 * Metodo diz se a lista ja contem o usuario passado
	 * 
	 * @param idUsuario
	 * @return
	 */
	public boolean jaTemUsuario(int idUsuario) {
		return usuarios.contains(idUsuario);
	}
}
