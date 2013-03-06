package data.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.som.Som;
import data.user.Conta;
import data.user.ContaBasica;
import data.user.Usuario;
import data.util.OurTime;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SomInvalidoException;
import exceptions.system.UsuarioInexistenteException;
import exceptions.user.EmailInvalidoException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;
import exceptions.user.NomeInvalidoException;


public class Sistema {
	//mapa com id de usuario para usuario
	private Map<Integer,Usuario> usuarios;
	//mapa com id de sessao para id de usuario
	private Map<Integer,Integer> sessoesAbertas;
	//mapa com os ids de sons para os sons cadastrados
	private Map<Integer,Som> sonsCadastrados;
	private int userCounter;
	private int sessionCounter;
	private int soundCounter;
	
	public Sistema(){
		this.usuarios = new HashMap<Integer, Usuario>();
		this.sessoesAbertas = new HashMap<Integer, Integer>();
		this.sonsCadastrados = new HashMap<Integer, Som>();
		this.sessionCounter = 0;
		this.userCounter = 0;
		this.soundCounter = 0;
	}
	
	/**
	 * Construtor a partir dos dados enviados da interface
	 * @param login Login do Usuario	
	 * @param senha Senha do Usuario
	 * @param nome Nome do Usuario
	 * @param email Email do Usuario
	 * @throws LoginInvalidoException 
	 * @throws EmailInvalidoException 
	 * @throws NomeInvalidoException 
	 * @throws LoginJaExisteException 
	 * @throws EmailJaExisteException 
	 */
	public void criarUsuario(String login, String senha, String nome, String email) throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException {
		if(getUser(login)!= null) throw  new LoginJaExisteException("Já existe um usuário com este login");
		if(emailJaUsado(email)) throw new EmailJaExisteException("Já existe um usuário com este email");
		Conta conta = new ContaBasica(login, senha);
		Usuario user = new Usuario(nome, conta, email,++userCounter);
		usuarios.put(user.getID(), user);
	}

	private Usuario getUser(String login) {
		for (Usuario user : usuarios.values()){
			if(user.getLogin().equalsIgnoreCase(login)) return user;
		}
		return null;
	}

	private boolean emailJaUsado(String email) {
		for(Integer ID: usuarios.keySet()){
			if(usuarios.get(ID).getEmail().equalsIgnoreCase(email)) return true;
		}
		return false;
	}

	/**
	 * Abri uma sessão para um usuario e retorna o ID da sessão aberta
	 * @param login Login do Usuario
	 * @param senha Senha do Usuario
	 * @return ID da sessão
	 * @throws LoginInvalidoException 
	 * @throws UsuarioInexistenteException 
	 */
	public int abrirSessao(String login, String senha) throws LoginInvalidoException, UsuarioInexistenteException{
		if(login == null || login.isEmpty()) throw new LoginInvalidoException("Login inválido");
		Usuario user =getUser(login);
		if(user==null){
			throw new UsuarioInexistenteException("Usuário inexistente");
		}
		else if (user.autenticaConta(senha)) {
			int newSessionId = generateNewSessionId();
			sessoesAbertas.put(newSessionId, user.getID());
			return newSessionId;
		}else{
			throw new LoginInvalidoException("Login inválido");
		}
	}

	private int generateNewSessionId() {
		return sessionCounter++;
	}

	/**
	 * Posta um Som no usuario da sessao criada
	 * @param idSessao o id da sessao do usuario	
	 * @param link o link do som a ser postado
	 * @param dataCriacao a data de criacao do sim
	 * @return ID do Som
	 * @throws SessaoInexistenteException
	 * @throws SomInvalidoException
	 * @throws DataDeCriacaoInvalidaException
	 */
	public int postarSom(int idSessao, String link, String dataCriacao) throws    SessaoInexistenteException, SomInvalidoException, DataDeCriacaoInvalidaException {
		Usuario user = getSessionUser(idSessao); 
		if(link==null || link.isEmpty()) throw new SomInvalidoException("Som inválido");
		String[] dataValores = dataCriacao.split("/");
		OurTime ourTime;
		try {
			ourTime = new OurTime(Integer.parseInt(dataValores[0]), Integer.parseInt(dataValores[1]), Integer.parseInt(dataValores[2]));
		} catch (Exception e) {
			throw new DataDeCriacaoInvalidaException("Data de Criação inválida");
		}
		Som som = new Som(link, ourTime,++soundCounter);
		user.postaSom(som);
		sonsCadastrados.put(som.getID(), som);
		atualizaVisaoDeSonsDosSeguidores(user , som.getID());
		return som.getID();
		
	}
	private void atualizaVisaoDeSonsDosSeguidores(Usuario user, int somId) {
		for(Integer userId : user.getListaDeSeguidores()){
			getUsuario(userId).addSomNaVisao(somId);
		}
		
	}

	/**
	 * O usuario da sessao dada segue o usuario com o login dado
	 * @param idSessao
	 * @param login
	 * @throws LoginInvalidoException
	 * @throws SessaoInexistenteException
	 * @throws UsuarioInexistenteException
	 */
	public void seguirUsuario(int idSessao,String login) throws LoginInvalidoException, SessaoInexistenteException, UsuarioInexistenteException{
		if(login == null || login.isEmpty()) throw new LoginInvalidoException("Login inválido");
		Usuario seguidor = getSessionUser(idSessao);
		Usuario seguido = getUser(login);
		if(seguido == null){
			throw new UsuarioInexistenteException("Login inexistente");
		}
		seguidor.addFonteDeSom(seguido.getID());
		seguido.addSeguidor(seguidor);
		seguidor.addSonsNaVisao(seguido.getPerfilMusical());
	}
	/**
	 * Retorna o numero de seguidores do usuario da sessao dada
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public int getNumeroDeSeguidores(int idSessao) throws SessaoInexistenteException{
		Usuario user = getSessionUser(idSessao);
		return user.getNumeroDeSeguidores();
	}

	/**
	 * Retorna o ID do Usuario cadastrado no sistema. IDs são numeros naturais por ordem de criacao 
	 * @param idSessao ID da Sessao do Usuario
	 * @return ID do Usuario
	 * @throws SessaoInexistenteException 
	 */
	public int getIDUsuario(int idSessao) throws SessaoInexistenteException {
		return getSessionUser(idSessao).getID();
	}
	/**
	 * Retorna a lista de seguidores do usuario da sessao dada
	 * @param idSessao
	 * @return
	 * @throws SessaoInexistenteException
	 */
	public List<Integer> getListaDeSeguidores(int idSessao) throws SessaoInexistenteException{
		return getSessionUser(idSessao).getListaDeSeguidores();
	}
	
	/**
	 * Retorna uma lista contendo os IDs dos Usuarios que pertencem a sua Fonte de Sons
	 * @param idSessao ID da Sessao do Usuario 
	 * @return Lista de IDs 
	 * @throws SessaoInexistenteException 
	 */
	public List<Integer> getFonteDeSons(int idSessao) throws SessaoInexistenteException {
		return getSessionUser(idSessao).getFontesDeSons();
	}
	
	// Retorna o usuario da sessao dada, SessaoInexistenteException caso ela nao exista
	private Usuario getSessionUser(int idSessao) throws SessaoInexistenteException {
		Usuario user = usuarios.get(sessoesAbertas.get(idSessao));
		if (user==null) {
			throw new SessaoInexistenteException("Sessão inexistente");
		}
		return user;
	}

	/**
	 * Retorna uma lista contendo os IDs dos sons do Usuario logado na Sessao passada
	 * @param idSessao ID da Sessao do Usuario
	 * @return Lista de IDs
	 * @throws SessaoInexistenteException 
	 */
	public List<Integer> getPerfilMusical(int idSessao) throws SessaoInexistenteException{
		Usuario user = getSessionUser(idSessao);
		return user.getPerfilMusical();			
	}

	/**
	 * Encerra a sessao com o ID passado como parametro.
	 * @param iD_Sessao da sessao a ser encerrada
	 */
	public void encerraSessao(int iD_Sessao) {
		sessoesAbertas.remove(iD_Sessao);
	}
	/**
	 * Retorna o usuario com o ID dado
	 * @param userID
	 * @return
	 */
	public Usuario getUsuario(int userID){
		return usuarios.get(userID);
	}
	/**
	 * Retorna o som com o dado ID
	 * @param somID
	 * @return
	 */
	public Som getSom(int somID){
		return sonsCadastrados.get(somID);
	}
	/**
	 * Limpa todos os dados do sistema
	 */
	public void zerarSistema(){
		this.usuarios = new HashMap<Integer, Usuario>();
		this.sessoesAbertas = new HashMap<Integer, Integer>();
		this.sonsCadastrados = new HashMap<Integer, Som>();
		this.sessionCounter = 0;
		this.userCounter = 0;
		this.soundCounter = 0;
	}

	/**
	 * Retorna uma lista contendo os ids dos sons da visão de sons do usuario que representado pelo id de sessao passado
	 * @param idSessao ID da sessao do usuario
	 * @return Lista dos Sons da Visao de Sons
	 */
	public List<Integer> getVisaoDosSons(int idSessao) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
