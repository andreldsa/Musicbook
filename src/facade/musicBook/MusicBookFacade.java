package facade.musicBook;

import java.util.List;

import dados.som.Som;
import dados.usuario.Usuario;
import dados.util.CompositionRule;

public interface MusicBookFacade {
	
	public void abrirSessao(String login, String senha);
	
	public void encerraSessao(int idSessao);
	
	public Usuario getUsuarioLogado(int idSessao);
	
	public void postarSom(int idSessao, String link, String dataFormatada);
	
	public void criarUsuario(String login, String senha, String nome, 
			String email);
	
	public List<Integer> getMainFeed(int idSessao);
	
	public Som getSom(int idSom);
	
	public List<Integer> getSonsFavoritos(int idSessao);
	
	public List<Integer> getPerfilMusical(int idSessao);
	
	public void favoritarSom(int idSessao, int idSom);
	
	public void setMainFeedRule(CompositionRule feedRule, int idSessao);
	
	public List<Integer> getFeedExtra(int idSessao);
	
	public List<Usuario> getListaDeSeguidores(int idSessao);
	
	public Usuario getUsuario(int idUsuario);
	
	public boolean isSeguidor(int idSessao, int idUsuario);
	
	public void seguirUsuario(int idSessao, String loginUsuario);
	
	public List<Usuario> pesquisaUsuario(String nomePesquisado);
}
