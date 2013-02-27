package data.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import data.som.Som;
import data.user.Conta;
import data.user.ContaBasica;
import data.user.Usuario;
import data.util.OurTime;
import data.util.SolicitacaoDeAmizade;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SolicitacaoInexistenteException;
import exceptions.system.SolicitacaoInvalidaException;
import exceptions.system.UsuarioInexistenteException;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;
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
	//mapa com os logins de usuarios para as solicitacoes enviadas a eles
	private Map <String,List<SolicitacaoDeAmizade>> solicitacoes;
	private int userCounter;
	private int sessionCounter;
	private int soundCounter;
	private int solicitationsCounter;
	
	public Sistema(){
		this.usuarios = new HashMap<Integer, Usuario>();
		this.sessoesAbertas = new HashMap<Integer, Integer>();
		this.solicitacoes = new HashMap<String, List<SolicitacaoDeAmizade>>();
		this.sonsCadastrados = new HashMap<Integer, Som>();
		this.sessionCounter = 0;
		this.userCounter = 0;
		this.soundCounter = 0;
		this.solicitationsCounter =0;
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
		solicitacoes.put(login, new ArrayList<SolicitacaoDeAmizade>());
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
	 * @throws AnoInvalidoException 
	 * @throws NumberFormatException 
	 * @throws SessaoInexistenteException 
	 * Posta um som para todos aqueles que tem como fonte de som o usuario do ID de sessao provido
	 * @param idSessao Id da Sessao do Usuario
	 * @param link Link do Som
	 * @param dataCriacao Data da Criacao do Post
	 * @throws MesInvalidoException 
	 * @throws DiaInvalidoException 
	 * @throws  
	 */
	public int postarSom(int idSessao, String link, String dataCriacao) throws  DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException {
		Usuario user = getSessionUser(idSessao);
		String[] dataValores = dataCriacao.split("/");
		OurTime ourTime = new OurTime(Integer.parseInt(dataValores[0]), Integer.parseInt(dataValores[1]), Integer.parseInt(dataValores[2]));
		Som som = new Som(link, ourTime,++soundCounter);
		user.postaSom(som);
		sonsCadastrados.put(som.getID(), som);
		return som.getID();
		
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
	 * Envia uma solicitacao de amizade de um usuario para outro
	 * @param idSessao ID da Sessao do Usuario que enviou a solicitacao
	 * @param login Login do usuario a receber a solicitacao
	 * @return ID da Solicitacao
	 * @throws SessaoInexistenteException 
	 * @throws SolicitacaoInvalidaException 
	 * @throws LoginInvalidoException 
	 */
	public int enviarSolicitacaoAmizade(int idSessao, String login) throws SessaoInexistenteException, SolicitacaoInvalidaException, LoginInvalidoException {
		Usuario remetente = getSessionUser(idSessao);
		if(login == null || login.isEmpty()) throw new LoginInvalidoException("Login inválido");
		Usuario destinatario = getUser(login);
		if(destinatario == null) throw new LoginInvalidoException("Login inválido");
		SolicitacaoDeAmizade solicitacao = new SolicitacaoDeAmizade(solicitationsCounter++, remetente, destinatario);
		if(solicitacoes.get(login).contains(solicitacao)) throw new SolicitacaoInvalidaException("Solicitação já existente");
		solicitacoes.get(login).add(solicitacao);
		return solicitacao.getID();
	}
	
	/**
	 * Aceita uma solicitacao de amizade de um usuario
	 * @param idSessao Usuario a aceitar a solicitacao
	 * @param solicitacaoID ID da solicitacao de amizade
	 * @throws SessaoInexistenteException 
	 * @throws SolicitacaoInvalidaException 
	 * @throws SolicitacaoInexistenteException 
	 */
	public void aceitarSolicitacaoAmizade(int idSessao, int solicitacaoID) throws SessaoInexistenteException, SolicitacaoInvalidaException, SolicitacaoInexistenteException {
		Usuario destinatario = getSessionUser(idSessao);
		String login = destinatario.getLogin();
		SolicitacaoDeAmizade solicitacao = solicitacaoRecebida(login,solicitacaoID);
		if(solicitacao == null) throw new SolicitacaoInexistenteException("Solicitação inválida");
		Usuario remetente = solicitacao.getRemetente();
		destinatario.addFonteDeSom(remetente.getID());
		remetente.addFonteDeSom(destinatario.getID());
		solicitacoes.get(login).remove(solicitacao);
		
	}

	private SolicitacaoDeAmizade solicitacaoRecebida(String login,
			int solicitacaoID) {
		for(SolicitacaoDeAmizade sol : solicitacoes.get(login)){
			if (sol.getID() == solicitacaoID) return sol;
		}
		return null;
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
	 * Retorna as solicitacoes do usuario com dado login
	 * @param login
	 * @return
	 */
	public List<SolicitacaoDeAmizade> getSolicitacoesDeAmizade(String login){
		return solicitacoes.get(login);
	}
	

}
