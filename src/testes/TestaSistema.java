package testes;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.system.Sistema;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SessaoInvalidaException;
import exceptions.system.SomInvalidoException;
import exceptions.system.UsuarioInexistenteException;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;
import exceptions.user.EmailInvalidoException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;
import exceptions.user.NomeInvalidoException;

public class TestaSistema {
	
	private Sistema sistema;

	
	@Before
	public void setUp() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException{
		sistema = new Sistema();
		sistema.criarUsuario("Joao.Silva", "joao123", "Joao Silva Diniz", "Joao.Silva@gmail.com");
	}
	
	@Test
	public void testaGetIDUsuario() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException, UsuarioInexistenteException, SessaoInexistenteException{
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
	public void testaGetListaDeSeguidores() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException, UsuarioInexistenteException, SessaoInexistenteException{
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
		
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		
		List<Integer> seguidoresDeJoao = new ArrayList<Integer>();
		
		assertEquals(seguidoresDeJoao, sistema.getListaDeSeguidores(idSessaoJoao));
		
		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");
		
		seguidoresDeJoao.add(sistema.getIDUsuario(idSessaoJiao));
		
		assertEquals(seguidoresDeJoao, sistema.getListaDeSeguidores(idSessaoJoao));
	}
	
	@Test
	public void testagetVisaoDosSons() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException, UsuarioInexistenteException, SessaoInexistenteException, SomInvalidoException, DataDeCriacaoInvalidaException{
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
		
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		
		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");
		
		List<Integer> listaVisaoDeSonsJiao = new ArrayList<Integer>();
		assertEquals(listaVisaoDeSonsJiao, sistema.getVisaoDosSons(idSessaoJiao));
		
		int idSom1 = sistema.postarSom(idSessaoJoao, "http://www.youtube.com/watch?v=r-fIOrUTIOQ", "13/08/2015");
		listaVisaoDeSonsJiao.add(idSom1);
		assertEquals(listaVisaoDeSonsJiao, sistema.getVisaoDosSons(idSessaoJiao));
	}

	
	@Test
	public void testaGetNumeroDeSeguidores() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException, UsuarioInexistenteException, SessaoInexistenteException{
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
		
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		
		assertEquals(0, sistema.getNumeroDeSeguidores(idSessaoJoao));
		
		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");
		
		assertEquals(1, sistema.getNumeroDeSeguidores(idSessaoJoao));
	}
	
	
	@Test
	public void testaSeguirUsuario() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException, UsuarioInexistenteException, SessaoInexistenteException{
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
		
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		
		sistema.seguirUsuario(idSessaoJoao, "Jiao.Silva");
		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");
		
		List<Integer> fonteDeSonsJiao = new ArrayList<Integer>();
		List<Integer> fonteDeSonsJoao = new ArrayList<Integer>();
		
		fonteDeSonsJiao.add(sistema.getIDUsuario(idSessaoJoao));
		fonteDeSonsJoao.add(sistema.getIDUsuario(idSessaoJiao));
		
		assertEquals(fonteDeSonsJiao, sistema.getFonteDeSons(idSessaoJiao));
		assertEquals(fonteDeSonsJoao, sistema.getFonteDeSons(idSessaoJoao));
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente1() throws LoginInvalidoException, SessaoInexistenteException, UsuarioInexistenteException {
		sistema.seguirUsuario(39482, "Joao.Silva");	
	}
	
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente2() throws SessaoInexistenteException{
		sistema.getListaDeSeguidores(39482);	
	}
		
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente3() throws SessaoInexistenteException{
		sistema.getNumeroDeSeguidores(39482);
	}
	
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente4() throws SessaoInexistenteException{
		sistema.getFonteDeSons(39482);	
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido1() throws LoginInvalidoException, UsuarioInexistenteException, SessaoInexistenteException{
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		
		sistema.seguirUsuario(idSessaoJoao, null);
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido2() throws LoginInvalidoException, UsuarioInexistenteException, SessaoInexistenteException{
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		
		sistema.seguirUsuario(idSessaoJoao, "");
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInválido1() throws LoginInvalidoException, UsuarioInexistenteException{
		sistema.abrirSessao(null,"joao123");
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInválido2() throws LoginInvalidoException, UsuarioInexistenteException{
		sistema.abrirSessao("","joao123");
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInválido3() throws LoginInvalidoException, UsuarioInexistenteException{
		sistema.abrirSessao("Joao.Silva","jiao123");
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInválido4() throws LoginInvalidoException, UsuarioInexistenteException{
		sistema.abrirSessao("Joao.Silva","ze123");
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioINexistente() throws LoginInvalidoException, UsuarioInexistenteException {
		sistema.abrirSessao("Jiao.Silva","joao123");
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = EmailJaExisteException.class)
	public void testaEmailJaExiste() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException{
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Joao.Silva@gmail.com");
	}

	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Test(expected = LoginJaExisteException.class )
	public void testaLoginJaExisteException() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException{
		sistema.criarUsuario("Joao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido1() throws DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, LoginInvalidoException, UsuarioInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao,"", "99/99/9999");
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido2() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao, null, "00/00/0000");
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido3() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao, "www.youtube.com/watch?v=r-fIOrUTIOQ", "31/02/2014");
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido4() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = sistema.abrirSessao("Joao.Silva","joao123");
		sistema.postarSom(idSessao, "ftp://www.youtube.com/watch?v=r-fIOrUTIOQ", "31/02/2014");
	}
}
	
	
	

