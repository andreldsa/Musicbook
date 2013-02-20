package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.sys.Sistema;

import exceptions.EAfacade.AtributoInexistenteException;
import exceptions.EAfacade.AtributoInvalidoException;
import exceptions.system.SessaoInvalidaException;
import exceptions.system.SolicitacaoInexistenteException;
import exceptions.system.SolicitacaoInvalidaException;
import exceptions.system.UsuarioInexistenteException;
import exceptions.user.LoginInvalidoException;

public class TestaOurEasyAcceptFacade {
	
	private OurEasyAcceptFacade facade;
	
	@Before
	public void setUp(){
		facade = new OurEasyAcceptFacade(new data.system.Sistema());
		facade.criarUsuario("Joao.Silva", "joao123", "Joao Silva Diniz", "Joao.Silva@gmail.com");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testLoginInvalido1() {
		facade.getAtributoUsuario(null, "nome");	
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testLoginInvalido2() {
		facade.getAtributoUsuario("", "nome");
	}
	
	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistnete() {
		facade.getAtributoUsuario("xpto", "nome");
	}
	
	@Test(expected = AtributoInvalidoException.class)
	public void testaAtributoInvalido1() {
		facade.getAtributoUsuario("Joao.Silva", "");
	}
	
	@Test(expected = AtributoInvalidoException.class)
	public void testaAtributoInvalido2() {
		facade.getAtributoUsuario("Joao.Silva", null );
	}
	
	@Test(expected = AtributoInexistenteException.class)
	public void testaAtributoInexistente() {
		facade.getAtributoUsuario("Joao.Silva", "xpto");
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida1(){
		facade.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		
		
		facade.enviarSolicitacaoAmizade("", "Joao.Silva");	
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida2(){
		facade.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		
		
		facade.enviarSolicitacaoAmizade(null , "Jiao.Silva");
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida3(){
		facade.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		

		int idSessaoJoao = facade.abrirSessao("Joao.Silva", "joao123");
		int idSolicitacao1 = facade.enviarSolicitacaoAmizade(idSessaoJoao + "", "Jiao.Silva");	
		
		facade.aceitarSolicitacaoAmizade("", idSolicitacao1 + "");
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida4(){
		facade.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		

		int idSessaoJoao = facade.abrirSessao("Joao.Silva", "joao123");
		int idSolicitacao1 = facade.enviarSolicitacaoAmizade(idSessaoJoao + "", "Jiao.Silva");	
		
		facade.aceitarSolicitacaoAmizade(null, idSolicitacao1 + "");
	}
	
	@Test(expected = SolicitacaoInvalidaException.class)
	public void  testaSolicitacaoInvalida1(){
		facade.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		

		int idSessaoJoao = facade.abrirSessao("Joao.Silva", "joao123");
		
		facade.aceitarSolicitacaoAmizade(idSessaoJoao + "", "");
	}
	
	@Test(expected = SolicitacaoInvalidaException.class)
	public void  testaSolicitacaoInvalida2(){
		facade.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		

		int idSessaoJoao = facade.abrirSessao("Joao.Silva", "joao123");
		
		facade.aceitarSolicitacaoAmizade(idSessaoJoao + "", null);
	}
}
