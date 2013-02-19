package br.com.sys;

import java.util.HashMap;

import br.com.sys.exceptions.ErroDeAutenticacaoException;
import br.com.sys.exceptions.UsuarioNaoCadastradoException;

public class BancoDeUsuarios {
	
	private HashMap<String, Usuario> usuarios; //Mapa de Usuarios onde a chave � o username;
	
	public BancoDeUsuarios() {
		setUsuarios(new HashMap<String, Usuario>());
	}

	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(HashMap<String, Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	/**
	 * 
	 * @param login
	 * @param senha
	 * @return Usuario validado.
	 * @throws UsuarioNaoCadastrado
	 * @throws ErroDeAutenticacaoException 
	 */
	public Usuario validaPermissao(String login, String senha) throws UsuarioNaoCadastradoException, ErroDeAutenticacaoException {
		if(usuarios.containsKey(login)) {
			if(usuarios.get(login).verificaSenha(senha)) {
				return usuarios.get(login);
			} else {
				throw new ErroDeAutenticacaoException();
			}
		} else {
			throw new UsuarioNaoCadastradoException();
		}
	}
	
	public void cadastraUsuario(String login, String nome, String senha) {
		Usuario newUsuario = new Usuario(nome, senha);
		usuarios.put(login, newUsuario);
	}

}
