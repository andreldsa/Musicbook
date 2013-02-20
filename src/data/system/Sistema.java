package data.system;

import java.util.List;

import data.util.OurTime;


public class Sistema {

	
	/**
	 * Construtor a partir dos dados enviados da interface
	 * @param login Login do Usuario	
	 * @param senha Senha do Usuario
	 * @param nome Nome do Usuario
	 * @param email Email do Usuario
	 */
	public void criarUsuario(String login, String senha, String nome, String email) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Abri uma sessão para um usuario e retorna o ID da sessão aberta
	 * @param login Login do Usuario
	 * @param senha Senha do Usuario
	 * @return ID da sessão
	 */
	public int abrirSessao(String login, String senha){
		
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * Posta um som para todos aqueles que tem como fonte de som o usuario do ID de sessao provido
	 * @param idSessao Id da Sessao do Usuario
	 * @param link Link do Som
	 * @param dataCriacao Data da Criacao do Post
	 */
	public void postarSom(int idSessao, String link, String dataCriacao) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Retorna o ID do Usuario cadastrado no sistema. IDs são numeros naturais por ordem de criacao 
	 * @param idSessao ID da Sessao do Usuario
	 * @return ID do Usuario
	 */
	public int getIDUsuario(int idSessao) {
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * Envia uma solicitacao de amizade de um usuario para outro
	 * @param idSessao ID da Sessao do Usuario que enviou a solicitacao
	 * @param login Login do usuario a receber a solicitacao
	 * @return ID da Solicitacao
	 */
	public int enviarSolicitacaoAmizade(int idSessao, String login) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	/**
	 * Aceita uma solicitacao de amizade de um usuario
	 * @param idSessao Usuario a aceitar a solicitacao
	 * @param solicitacaoID ID da solicitacao de amizade
	 */
	public void aceitarSolicitacaoAmizade(int idSessao, int solicitacaoID) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Retorna uma lista contendo os IDs dos Usuarios que pertencem a sua Fonte de Sons
	 * @param idSessao ID da Sessao do Usuario 
	 * @return Lista de IDs 
	 */
	public List<Integer> getFonteDeSons(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

}
