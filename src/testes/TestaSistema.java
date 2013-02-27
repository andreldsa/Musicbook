package testes;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.system.Sistema;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SolicitacaoInexistenteException;
import exceptions.system.SomInvalidoException;
import exceptions.system.UsuarioInexistenteException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;

public class TestaSistema {
	
	private Sistema sistema;

	
	@Before
	public void setUp(){
		sistema = new Sistema();
		sistema.criarUsuario("Joao.Silva", "joao123", "Joao Silva Diniz", "Joao.Silva@gmail.com");
	}
	
	@Test
	public void testaGetIDUsuario(){
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
		sistema.criarUsuario("Ze.Silva", "ze123", "Ze Silva Diniz", "Ze.Silva@gmail.com");
		
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		int idSessaoZe = sistema.abrirSessao("Ze.Silva", "ze123");
		
		assertEquals(1, sistema.getIDUsuario(idSessaoJoao));
		assertEquals(2, sistema.getIDUsuario(idSessaoJiao));
		assertEquals(3, sistema.getIDUsuario(idSessaoZe));
	}
	
	@Test
	public void testaSolicitacaoAmizade(){
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
		
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		
		int solicitacao1ID = sistema.enviarSolicitacaoAmizade(idSessaoJoao, "Jiao.Silva");
		
		sistema.aceitarSolicitacaoAmizade(idSessaoJiao, solicitacao1ID);
		
		List<Integer> fonteDeSonsJiao = new ArrayList<Integer>();
		List<Integer> fonteDeSonsJoao = new ArrayList<Integer>();
		
		fonteDeSonsJiao.add(sistema.getIDUsuario(idSessaoJoao));
		fonteDeSonsJoao.add(sistema.getIDUsuario(idSessaoJiao));
		
		assertEquals(fonteDeSonsJiao, sistema.getFonteDeSons(idSessaoJiao));
		assertEquals(fonteDeSonsJoao, sistema.getFonteDeSons(idSessaoJoao));
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido1(){
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		
		sistema.enviarSolicitacaoAmizade(idSessaoJoao, null);
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido2(){
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		
		sistema.enviarSolicitacaoAmizade(idSessaoJoao, "");
	}
	
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente(){
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSolicitacao1 = sistema.enviarSolicitacaoAmizade(idSessaoJoao, "Jiao.Silva");	
		
		sistema.aceitarSolicitacaoAmizade(idSessaoJoao + 1, idSolicitacao1);
	}
	
	@Test(expected = SolicitacaoInexistenteException.class)
	public void testaSolicitacaoInexistente1(){
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");		

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		
		final int idSolicitacaoInexistente = 143;
		
		sistema.aceitarSolicitacaoAmizade(idSessaoJoao, idSolicitacaoInexistente);
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInv�lido1(){
		sistema.abrirSessao(null,"joao123");
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInv�lido2(){
		sistema.abrirSessao("","joao123");
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInv�lido3(){
		sistema.abrirSessao("Joao.Silva","jiao123");
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInv�lido4(){
		sistema.abrirSessao("Joao.Silva","ze123");
	}
	
	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioINexistente() {
		sistema.abrirSessao("Jiao.Silva","joao123");
	}
	
	@Test(expected = EmailJaExisteException.class)
	public void testaEmailJaExiste(){
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Joao.Silva@gmail.com");
	}
	
	@Test(expected = LoginJaExisteException.class )
	public void testaLoginJaExisteException(){
		sistema.criarUsuario("Joao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida1(){
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "20/12/2012");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida2(){
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "12/30/2012");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida3(){
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "32/11/2012");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida4(){
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "29/02/2015");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida5(){
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "31/04/2015");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida6(){
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "30/04/15");
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido1(){
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao,"", "99/99/9999");
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido2(){
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao, null, "00/00/0000");
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido3(){
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao, "www.youtube.com/watch?v=r-fIOrUTIOQ", "01/01/2014");
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido4(){
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao, "ftp://www.youtube.com/watch?v=r-fIOrUTIOQ", "01/01/2014");
	}
}
	
	
	

