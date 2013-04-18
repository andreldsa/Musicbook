package facade.musicBook;

import java.util.List;

import dados.som.Som;
import dados.usuario.Usuario;
import dados.util.CompositionRule;


public class MusicBookFacadeImpl implements MusicBookFacade {

	@Override
	public void abrirSessao(String login, String senha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encerraSessao(int idSessao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario getUsuarioLogado(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postarSom(int idSessao, String link, String dataFormatada) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void criarUsuario(String login, String senha, String nome,
			String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getMainFeed(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Som getSom(int idSom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getSonsFavoritos(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getPerfilMusical(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void favoritarSom(int idSessao, int idSom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMainFeedRule(CompositionRule feedRule, int idSessao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getFeedExtra(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> getListaDeSeguidores(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario getUsuario(int idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSeguidor(int idSessao, int idUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void seguirUsuario(int idSessao, String loginUsuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> pesquisaUsuario(String nomePesquisado) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	XD
}
