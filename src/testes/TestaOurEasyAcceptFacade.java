package testes;

import org.junit.Before;
import org.junit.Test;

import exceptions.EAfacade.AtributoInexistenteException;
import exceptions.EAfacade.AtributoInvalidoException;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SessaoInvalidaException;
import exceptions.system.SomInvalidoException;
import exceptions.system.UsuarioInexistenteException;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;
import exceptions.user.LoginInvalidoException;

public class TestaOurEasyAcceptFacade {
	
	private OurEasyAcceptFacade facade;
	
	@Before
	public void setUp(){
		facade = new OurEasyAcceptFacade(new data.system.Sistema());
		facade.criarUsuario("Joao.Silva", "joao123", "Joao Silva Diniz", "Joao.Silva@gmail.com");
	}

	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = LoginInvalidoException.class)
	public void testLoginInvalido1() {
		facade.getAtributoUsuario(null, "nome");	
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testLoginInvalido2() {
		facade.getAtributoUsuario("", "nome");
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistnete() {
		facade.getAtributoUsuario("xpto", "nome");
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
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
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida1(){
		facade.seguirUsuario("", "Joao.Silva");	
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida2(){	
		facade.seguirUsuario(null, "Joao.Silva");
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida3(){
		facade.getListaDeSeguidores("");	
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida4(){
		facade.getListaDeSeguidores(null);
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida5(){
		facade.getNumeroDeSeguidores("");	
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida6(){
		facade.getNumeroDeSeguidores(null);
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida7(){
		facade.getFonteDeSons("");	
	}
	
	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida8(){
		facade.getFonteDeSons(null);
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente1() throws LoginInvalidoException, SessaoInexistenteException, UsuarioInexistenteException {
		facade.seguirUsuario("xpto", "Joao.Silva");	
	}
	
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente2() throws SessaoInexistenteException{
		facade.getListaDeSeguidores("xpto");	
	}
		
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente3() throws SessaoInexistenteException{
		facade.getNumeroDeSeguidores("xpto");
	}
	
	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente4() throws SessaoInexistenteException{
		facade.getFonteDeSons("xpto");	
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida1() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "20/12/2012");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida2() throws DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, LoginInvalidoException, UsuarioInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "12/30/2012");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida3() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "32/11/2012");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida4() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "29/02/2015");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida5() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "31/04/2015");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida6() throws LoginInvalidoException, UsuarioInexistenteException, DiaInvalidoException, MesInvalidoException, SessaoInexistenteException, NumberFormatException, AnoInvalidoException, SomInvalidoException, DataDeCriacaoInvalidaException{
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "30/04/15");
	}
	
	}
