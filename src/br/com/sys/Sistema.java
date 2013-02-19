package br.com.sys;

import br.com.sys.exceptions.ErroDeAutenticacaoException;
import br.com.sys.exceptions.UsuarioNaoCadastradoException;

public class Sistema {
	
	private BancoDeUsuarios usuarios;
	private Usuario usuarioLogado;
	
	public Sistema() {
		usuarios = new BancoDeUsuarios();
		
		populaBancoDeUsuarios();
	}

	private void populaBancoDeUsuarios() {
		cadastraUsuario("andre", "Andr� L Abrantes", "senha");
	}


	public void efetuaLogin(String login, String senha) throws UsuarioNaoCadastradoException, ErroDeAutenticacaoException {
		this.usuarioLogado = usuarios.validaPermissao(login, senha);
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public void cadastraUsuario(String login, String nome, String senha) {
		usuarios.cadastraUsuario(login, nome, senha);
	}

	public void logout() {
		this.usuarioLogado = null;
	}

}
