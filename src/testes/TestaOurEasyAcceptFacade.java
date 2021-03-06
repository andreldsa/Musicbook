package testes;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dados.sistema.Sistema;
import exceptions.EAfacade.AtributoInexistenteException;
import exceptions.EAfacade.AtributoInvalidoException;
import exceptions.EAfacade.DataDeCriacaoInvalida;
import exceptions.EAfacade.RegraDeComposicaoInexistenteException;
import exceptions.EAfacade.RegraDeComposicaoInvalidaException;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SessaoInvalidaException;
import exceptions.system.SomInexistenteException;
import exceptions.system.SomInvalidoException;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;
import exceptions.user.EmailInvalidoException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;
import exceptions.user.NomeInvalidoException;
import exceptions.user.UsuarioInexistenteException;
import facade.easyAccept.OurEasyAcceptFacade;

public class TestaOurEasyAcceptFacade {

	private OurEasyAcceptFacade facade;

	@Before
	public void setUp() throws LoginInvalidoException, NomeInvalidoException,
			EmailInvalidoException, LoginJaExisteException,
			EmailJaExisteException {
		facade = new OurEasyAcceptFacade(Sistema.getInstance());
		facade.criarUsuario("Joao.Silva", "joao123", "Joao Silva Diniz",
				"Joao.Silva@gmail.com");
	}
	
	@After
	public void tearDown(){
		facade.encerrarSistema();
	}

	@Test
	public void setMainFeedRule() {
		assertEquals(
				"PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS",
				facade.getFirstCompositionRule());
		assertEquals("PRIMEIRO OS SONS COM MAIS FAVORITOS",
				facade.getSecondCompositionRule());
		assertEquals(
				"PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO",
				facade.getThirdCompositionRule());
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = LoginInvalidoException.class)
	public void testLoginInvalido1() throws LoginInvalidoException,
			UsuarioInexistenteException, AtributoInvalidoException,
			AtributoInexistenteException {
		facade.getAtributoUsuario(null, "nome");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testLoginInvalido2() throws LoginInvalidoException,
			UsuarioInexistenteException, AtributoInvalidoException,
			AtributoInexistenteException {
		facade.getAtributoUsuario("", "nome");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistente() throws LoginInvalidoException,
			UsuarioInexistenteException, AtributoInvalidoException,
			AtributoInexistenteException {
		facade.getAtributoUsuario("xpto", "nome");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = AtributoInvalidoException.class)
	public void testaAtributoInvalido1() throws LoginInvalidoException,
			UsuarioInexistenteException, AtributoInvalidoException,
			AtributoInexistenteException {
		facade.getAtributoUsuario("Joao.Silva", "");
	}

	@Test(expected = AtributoInvalidoException.class)
	public void testaAtributoInvalido2() throws LoginInvalidoException,
			UsuarioInexistenteException, AtributoInvalidoException,
			AtributoInexistenteException {
		facade.getAtributoUsuario("Joao.Silva", null);
	}

	@Test(expected = AtributoInexistenteException.class)
	public void testaAtributoInexistente() throws LoginInvalidoException,
			UsuarioInexistenteException, AtributoInvalidoException,
			AtributoInexistenteException {
		facade.getAtributoUsuario("Joao.Silva", "xpto");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida1() throws LoginInvalidoException,
			SessaoInexistenteException, UsuarioInexistenteException,
			SessaoInvalidaException {
		facade.seguirUsuario("", "Joao.Silva");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida2() throws LoginInvalidoException,
			SessaoInexistenteException, UsuarioInexistenteException,
			SessaoInvalidaException {
		facade.seguirUsuario(null, "Joao.Silva");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida3() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getListaDeSeguidores("");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida4() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getListaDeSeguidores(null);
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida5() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getNumeroDeSeguidores("");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida6() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getNumeroDeSeguidores(null);
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida7() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getFonteDeSons("");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida8() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getFonteDeSons(null);
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida9() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getSonsFavoritos("");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida10() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getSonsFavoritos(null);
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida11() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getFeedExtra("");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida12() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getFeedExtra(null);
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida13() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException,
			SomInvalidoException, DataDeCriacaoInvalidaException,
			SomInexistenteException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		int idSom = facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "13/08/2015");
		facade.favoritarSom("", String.valueOf(idSom));
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida14() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException,
			SomInvalidoException, DataDeCriacaoInvalidaException,
			SomInexistenteException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		int idSom = facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "13/08/2015");
		facade.favoritarSom(null, String.valueOf(idSom));
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida15() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getMainFeed("");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida16() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getMainFeed(null);
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida17() throws SessaoInexistenteException,
			SessaoInvalidaException, RegraDeComposicaoInexistenteException,
			RegraDeComposicaoInvalidaException {
		facade.setMainFeedRule("", "");
	}

	@Test(expected = SessaoInvalidaException.class)
	public void testaSessaoInvalida18() throws SessaoInexistenteException,
			SessaoInvalidaException, RegraDeComposicaoInexistenteException,
			RegraDeComposicaoInvalidaException {
		facade.setMainFeedRule(null, "");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente1() throws LoginInvalidoException,
			SessaoInexistenteException, UsuarioInexistenteException,
			SessaoInvalidaException {
		facade.seguirUsuario("xpto", "Joao.Silva");
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente2() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getListaDeSeguidores("xpto");
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente3() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getNumeroDeSeguidores("xpto");
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente4() throws SessaoInexistenteException,
			SessaoInvalidaException {
		facade.getFonteDeSons("xpto");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = DataDeCriacaoInvalida.class)
	public void testaDataDeCriacaoInvalida1() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "20/12/2012");
	}

	@Test(expected = DataDeCriacaoInvalida.class)
	public void testaDataDeCriacaoInvalida2() throws DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			LoginInvalidoException, UsuarioInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "12/30/2012");
	}

	@Test(expected = DataDeCriacaoInvalida.class)
	public void testaDataDeCriacaoInvalida3() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "32/11/2012");
	}

	@Test(expected = DataDeCriacaoInvalida.class)
	public void testaDataDeCriacaoInvalida4() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "29/02/2015");
	}

	@Test(expected = DataDeCriacaoInvalida.class)
	public void testaDataDeCriacaoInvalida5() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "31/04/2015");
	}

	@Test(expected = DataDeCriacaoInvalida.class)
	public void testaDataDeCriacaoInvalida6() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInvalidaException,
			DataDeCriacaoInvalida {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.postarSom(idSessao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "30/04/15");
	}

	@Test(expected = RegraDeComposicaoInvalidaException.class)
	public void testaRegraDeComposicaoInvalida1()
			throws SessaoInexistenteException, LoginInvalidoException,
			UsuarioInexistenteException, RegraDeComposicaoInexistenteException,
			RegraDeComposicaoInvalidaException, SessaoInvalidaException {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.setMainFeedRule(String.valueOf(idSessao), null);
	}

	@Test(expected = RegraDeComposicaoInvalidaException.class)
	public void testaRegraDeComposicaoInvalida2()
			throws SessaoInexistenteException, LoginInvalidoException,
			UsuarioInexistenteException, RegraDeComposicaoInexistenteException,
			RegraDeComposicaoInvalidaException, SessaoInvalidaException {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.setMainFeedRule(String.valueOf(idSessao), "");
	}

	@Test(expected = RegraDeComposicaoInexistenteException.class)
	public void testaRegraDeComposicaoInvalida3()
			throws SessaoInexistenteException, LoginInvalidoException,
			UsuarioInexistenteException, RegraDeComposicaoInexistenteException,
			RegraDeComposicaoInvalidaException, SessaoInvalidaException {
		int idSessao = facade.abrirSessao("Joao.Silva", "joao123");
		facade.setMainFeedRule(String.valueOf(idSessao), "xpto");
	}

}
