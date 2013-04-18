package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dados.sistema.Sistema;
import exceptions.system.DataDeCriacaoInvalidaException;
import exceptions.system.SessaoInexistenteException;
import exceptions.system.SomInexistenteException;
import exceptions.system.SomInvalidoException;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;
import exceptions.user.EmailInvalidoException;
import exceptions.user.EmailJaExisteException;
import exceptions.user.ListaInvalidaException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.LoginJaExisteException;
import exceptions.user.NomeInvalidoException;
import exceptions.user.UsuarioInexistenteException;
import exceptions.user.UsuarioInvalidoException;

public class TestaSistema {

	private Sistema sistema;

	@After
	public void tearDown() {
		sistema.encerrar();
	}

	@Before
	public void setUp() throws LoginInvalidoException, NomeInvalidoException,
			EmailInvalidoException, LoginJaExisteException,
			EmailJaExisteException {
		sistema = Sistema.getInstance();
		sistema.criarUsuario("Joao.Silva", "joao123", "Joao Silva Diniz",
				"Joao.Silva@gmail.com");
	}

	@Test
	public void testaGetIDUsuario() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException,
			UsuarioInexistenteException, SessaoInexistenteException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");
		sistema.criarUsuario("Ze.Silva", "ze123", "Ze Silva Diniz",
				"Ze.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		int idSessaoZe = sistema.abrirSessao("Ze.Silva", "ze123");

		assertEquals(1, sistema.getIDUsuario(idSessaoJoao));
		assertEquals(2, sistema.getIDUsuario(idSessaoJiao));
		assertEquals(3, sistema.getIDUsuario(idSessaoZe));
	}

	@Test
	public void testaGetListaDeSeguidores() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException,
			UsuarioInexistenteException, SessaoInexistenteException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");

		List<Integer> seguidoresDeJoao = new ArrayList<Integer>();

		assertEquals(seguidoresDeJoao,
				sistema.getListaDeSeguidores(idSessaoJoao));

		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");

		seguidoresDeJoao.add(sistema.getIDUsuario(idSessaoJiao));

		assertEquals(seguidoresDeJoao,
				sistema.getListaDeSeguidores(idSessaoJoao));
	}

	@Test
	public void testaSonsFavoritos() throws SessaoInexistenteException,
			SomInexistenteException, SomInvalidoException,
			DataDeCriacaoInvalidaException, LoginInvalidoException,
			UsuarioInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");

		int idSom = sistema.postarSom(idSessaoJoao,
				"https://www.youtube.com/watch?v=pVuGR0faZm4", "03/04/2014");

		sistema.favoritarSom(idSessaoJoao, idSom);

		List<Integer> sonsFavoritos = new ArrayList<Integer>();
		sonsFavoritos.add(idSom);

		assertEquals(sonsFavoritos, sistema.getSonsFavoritos(idSessaoJoao));
	}

	@Test
	public void testaFeedSecundario() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException,
			UsuarioInexistenteException, SessaoInexistenteException,
			SomInexistenteException, SomInvalidoException,
			DataDeCriacaoInvalidaException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");

		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");

		int idSom = sistema.postarSom(idSessaoJoao,
				"https://www.youtube.com/watch?v=pVuGR0faZm4", "03/04/2014");

		sistema.favoritarSom(idSessaoJoao, idSom);

		List<Integer> feedSecundario = new ArrayList<Integer>();
		feedSecundario.add(idSom);

		assertEquals(feedSecundario, sistema.getFeedExtra(idSessaoJiao));
	}

	@Test
	public void testaMainFeed() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException,
			UsuarioInexistenteException, SessaoInexistenteException,
			SomInvalidoException, DataDeCriacaoInvalidaException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");

		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");

		int idSom = sistema.postarSom(idSessaoJoao,
				"https://www.youtube.com/watch?v=X82FrnpBxNY", "03/04/2014");

		List<Integer> mainFeed = new ArrayList<Integer>();
		mainFeed.add(idSom);

		assertEquals(mainFeed, sistema.getMainFeed(idSessaoJiao));
	}

	@Test
	public void testaEncerraSessão() throws SomInvalidoException,
			DataDeCriacaoInvalidaException, SessaoInexistenteException,
			LoginInvalidoException, UsuarioInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idJoao = sistema.getIDUsuario(idSessaoJoao);
		assertEquals("Joao.Silva@gmail.com", sistema.getEmailUsuario(idJoao));
		assertEquals("Joao Silva Diniz", sistema.getNomeUsuario(idJoao));

		int numeroQualquer = 39123;

		assertTrue(null == sistema.getEmailUsuario(numeroQualquer));
		assertTrue(null == sistema.getNomeUsuario(numeroQualquer));

		sistema.encerraSessao(idSessaoJoao);

		try {
			sistema.postarSom(idSessaoJoao,
					"https://www.youtube.com/watch?v=SmOeqLHwn5g", "03/04/2014");
			fail();
		} catch (SessaoInexistenteException e) {
			// ok
		}

		idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.encerraSessao("Joao.Silva");

		try {
			sistema.postarSom(idSessaoJoao,
					"https://www.youtube.com/watch?v=SmOeqLHwn5g", "03/04/2014");
			fail();
		} catch (SessaoInexistenteException e) {
			// ok
		}
	}

	@Test
	public void testaPerfilMusical() throws SessaoInexistenteException,
			SomInvalidoException, DataDeCriacaoInvalidaException,
			LoginInvalidoException, UsuarioInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");

		int idSom = sistema.postarSom(idSessaoJoao,
				"https://www.youtube.com/watch?v=pVuGR0faZm4", "03/04/2014");

		assertEquals("03/04/2014", sistema.getDataSom(idSom));

		List<Integer> perfilMusical = new ArrayList<Integer>();
		perfilMusical.add(idSom);

		assertEquals(perfilMusical, sistema.getPerfilMusical(idSessaoJoao));
	}

	@Test
	public void testagetVisaoDosSons() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException,
			UsuarioInexistenteException, SessaoInexistenteException,
			SomInvalidoException, DataDeCriacaoInvalidaException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");

		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");

		List<Integer> listaVisaoDeSonsJiao = new ArrayList<Integer>();
		assertEquals(listaVisaoDeSonsJiao,
				sistema.getVisaoDosSons(idSessaoJiao));

		int idSom1 = sistema.postarSom(idSessaoJoao,
				"http://www.youtube.com/watch?v=r-fIOrUTIOQ", "13/08/2015");
		listaVisaoDeSonsJiao.add(idSom1);
		assertEquals(listaVisaoDeSonsJiao,
				sistema.getVisaoDosSons(idSessaoJiao));
	}

	@Test
	public void testaGetNumeroDeSeguidores() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException,
			UsuarioInexistenteException, SessaoInexistenteException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");

		assertEquals(0, sistema.getNumeroDeSeguidores(idSessaoJoao));

		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");

		assertEquals(1, sistema.getNumeroDeSeguidores(idSessaoJoao));
	}

	@Test
	public void testaSeguirUsuario() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException,
			UsuarioInexistenteException, SessaoInexistenteException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");

		sistema.seguirUsuario(idSessaoJoao, "Jiao.Silva");
		sistema.seguirUsuario(idSessaoJiao, "Joao.Silva");

		assertTrue(sistema.isSeguidor(idSessaoJoao,
				sistema.getUsuarioID("Jiao.Silva")));
		assertTrue(sistema.isSeguidor(idSessaoJiao,
				sistema.getUsuarioID("Joao.Silva")));

		List<Integer> fonteDeSonsJiao = new ArrayList<Integer>();
		List<Integer> fonteDeSonsJoao = new ArrayList<Integer>();

		fonteDeSonsJiao.add(sistema.getIDUsuario(idSessaoJoao));
		fonteDeSonsJoao.add(sistema.getIDUsuario(idSessaoJiao));

		assertEquals(fonteDeSonsJiao, sistema.getFonteDeSons(idSessaoJiao));
		assertEquals(fonteDeSonsJoao, sistema.getFonteDeSons(idSessaoJoao));
	}

	@Test
	public void testaListaDeUsuarios() throws SessaoInexistenteException,
			NomeInvalidoException, LoginInvalidoException,
			UsuarioInexistenteException, SomInvalidoException,
			DataDeCriacaoInvalidaException, UsuarioInvalidoException,
			ListaInvalidaException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");

		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");

		int idLista = sistema.criarLista(idSessaoJoao, "Nome da Lista");

		int idSom1 = sistema.postarSom(idSessaoJiao,
				"https://www.youtube.com/watch?v=pVuGR0faZm4", "07/04/2014");

		sistema.adicionaUsuarioNaLista(idSessaoJoao, idLista,
				sistema.getUsuarioID("Jiao.Silva"));

		List<Integer> sonsNaLista = new ArrayList<Integer>();
		sonsNaLista.add(idSom1);

		assertEquals(sonsNaLista, sistema.getSonsEmLista(idSessaoJoao, idLista));

		int idSom2 = sistema.postarSom(idSessaoJiao,
				"https://www.youtube.com/watch?v=urs3ZcFNjgg", "07/04/2014");
		int idSom3 = sistema.postarSom(idSessaoJiao,
				"https://www.youtube.com/watch?v=-7XGSmdrEjk", "07/04/2014");

		sonsNaLista.add(idSom2);
		sonsNaLista.add(idSom3);

		Collections.sort(sonsNaLista, new Comparator<Integer>() {

			@Override
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
		});

		assertEquals(sonsNaLista, sistema.getSonsEmLista(idSessaoJoao, idLista));
	}
	
	
	@Test
	public void testaRecomendacoes() throws LoginInvalidoException, NomeInvalidoException, EmailInvalidoException, LoginJaExisteException, EmailJaExisteException, UsuarioInexistenteException, SessaoInexistenteException, SomInvalidoException, DataDeCriacaoInvalidaException, SomInexistenteException{
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");
		sistema.criarUsuario("Ze.Silva", "ze123", "Ze Silva Diniz",
				"Ze.Silva@gmail.com");
		sistema.criarUsuario("Chico.Silva", "chico123", "Francisco Silva Diniz",
				"Chico.Silva@gmail.com");
		sistema.criarUsuario("Ze.Ninguem", "ninguem123", "Ninguem Silva Diniz",
				"Ninguem.Silva@gmail.com");


		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idSessaoJiao = sistema.abrirSessao("Jiao.Silva", "jiao123");
		int idSessaoZe = sistema.abrirSessao("Ze.Silva", "ze123");
		int idSessaoChico = sistema.abrirSessao("Chico.Silva", "chico123");
		
		int idJoao = sistema.getIDUsuario(idSessaoJoao);
		int idJiao = sistema.getIDUsuario(idSessaoJiao);
		int idZe = sistema.getIDUsuario(idSessaoZe);
		int idChico = sistema.getIDUsuario(idSessaoChico);
			
		int idSomJoao1 = sistema.postarSom(idSessaoJoao, "https://www.youtube.com/watch?v=X82FrnpBxNY", "07/04/2013");
		int idSomJoao2 = sistema.postarSom(idSessaoJoao, "https://www.youtube.com/watch?v=duK0-VBs6NQ", "07/04/2013");
		int idSomJiao1 = sistema.postarSom(idSessaoJiao,"https://www.youtube.com/watch?v=X82FrnpBxNY", "07/04/2013");
		int idSomJiao2 = sistema.postarSom(idSessaoJiao, "https://www.youtube.com/watch?v=duK0-VBs6NQ", "07/04/2013");
		int idSomZe1 = sistema.postarSom(idSessaoZe, "https://www.youtube.com/watch?v=X82FrnpBxNY", "07/04/2013");
		int idSomZe2 = sistema.postarSom(idSessaoZe, "https://www.youtube.com/watch?v=duK0-VBs6NQ", "07/04/2013");
		int idSomChico1 = sistema.postarSom(idSessaoChico, "https://www.youtube.com/watch?v=vJVZAvrg-ts", "07/04/2013");
		int idSomChico2 = sistema.postarSom(idSessaoChico, "https://www.youtube.com/watch?v=vJVZAvrg-ts", "07/04/2013");
		
		sistema.favoritarSom(idSessaoJoao, idSomJoao1);
		sistema.favoritarSom(idSessaoJoao, idSomJoao2);
		
		sistema.favoritarSom(idSessaoJiao, idSomZe1);
		sistema.favoritarSom(idSessaoJiao, idSomJiao1);
		sistema.favoritarSom(idSessaoJiao, idSomJoao1);
		sistema.favoritarSom(idSessaoJiao, idSomChico1);

		sistema.favoritarSom(idSessaoZe, idSomZe2);
		sistema.favoritarSom(idSessaoZe, idSomJoao1);
		sistema.favoritarSom(idSessaoZe, idSomJoao2);
		
		sistema.favoritarSom(idSessaoChico, idSomZe1);
		sistema.favoritarSom(idSessaoChico, idSomZe2);
		
		
		List<Integer> usuariosRecomendadosAJoao = new ArrayList<Integer>();
		List<Integer> usuariosRecomendadosAJiao = new ArrayList<Integer>();
		List<Integer> usuariosRecomendadosAZe = new ArrayList<Integer>();
		List<Integer> usuariosRecomendadosAChico = new ArrayList<Integer>();
		
		
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoJoao, idJiao));
		assertEquals(2,sistema.getNumeroDeFavoritosEmComum(idSessaoJoao, idZe));
		assertEquals(0,sistema.getNumeroDeFavoritosEmComum(idSessaoJoao, idChico));
		
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoJiao, idJoao));
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoJiao, idZe));
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoJiao, idChico));
		
		assertEquals(2,sistema.getNumeroDeFavoritosEmComum(idSessaoZe, idJoao));
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoZe, idJiao));
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoZe, idChico));
		
		assertEquals(0,sistema.getNumeroDeFavoritosEmComum(idSessaoChico, idJoao));
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoChico, idJiao));
		assertEquals(1,sistema.getNumeroDeFavoritosEmComum(idSessaoChico, idZe));
		
		
		usuariosRecomendadosAJoao.add(idZe);
		usuariosRecomendadosAJoao.add(idJiao);
		usuariosRecomendadosAJoao.add(idChico);
		
		usuariosRecomendadosAJiao.add(idChico);
		usuariosRecomendadosAJiao.add(idJoao);
		usuariosRecomendadosAJiao.add(idZe);
		
		usuariosRecomendadosAZe.add(idJoao);
		usuariosRecomendadosAZe.add(idChico);
		usuariosRecomendadosAZe.add(idJiao);
		
		usuariosRecomendadosAChico.add(idZe);
		usuariosRecomendadosAChico.add(idJiao);
		usuariosRecomendadosAChico.add(idJoao);
		
		
		assertEquals(usuariosRecomendadosAJoao, sistema.getFontesDeSonsRecomendadas(idSessaoJoao));
		assertEquals(usuariosRecomendadosAJiao, sistema.getFontesDeSonsRecomendadas(idSessaoJiao));
		assertEquals(usuariosRecomendadosAZe, sistema.getFontesDeSonsRecomendadas(idSessaoZe));
		assertEquals(usuariosRecomendadosAChico, sistema.getFontesDeSonsRecomendadas(idSessaoChico));
		
		sistema.seguirUsuario(idSessaoJoao, "Ze.Silva");
		sistema.seguirUsuario(idSessaoJiao, "Chico.Silva");
		sistema.seguirUsuario(idSessaoZe, "Joao.Silva");
		sistema.seguirUsuario(idSessaoChico, "Ze.Silva");
		
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoJoao, idJiao));
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoJoao, idZe));
		assertEquals(1, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoJoao, idChico));
		
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoJiao, idJoao));
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoJiao, idZe));
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoJiao, idChico));
		
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoZe, idJoao));
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoZe, idJiao));
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoZe, idChico));
		
		assertEquals(1, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoChico, idJoao));
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoChico, idJiao));
		assertEquals(0, sistema.getNumeroDeFontesDeSonsEmComum(idSessaoChico, idZe));
		
	
		
		usuariosRecomendadosAJoao.clear();
		usuariosRecomendadosAJoao.add(idChico);
		usuariosRecomendadosAJoao.add(idJiao);
		
		usuariosRecomendadosAJiao.clear();
		usuariosRecomendadosAJiao.add(idJoao);
		usuariosRecomendadosAJiao.add(idZe);
		
		usuariosRecomendadosAZe.clear();
		usuariosRecomendadosAZe.add(idChico);
		usuariosRecomendadosAZe.add(idJiao);
		
		usuariosRecomendadosAChico.clear();
		usuariosRecomendadosAChico.add(idJiao);
		usuariosRecomendadosAChico.add(idJoao);
		
		assertEquals(usuariosRecomendadosAJoao, sistema.getFontesDeSonsRecomendadas(idSessaoJoao));
		assertEquals(usuariosRecomendadosAJiao, sistema.getFontesDeSonsRecomendadas(idSessaoJiao));
		assertEquals(usuariosRecomendadosAZe, sistema.getFontesDeSonsRecomendadas(idSessaoZe));
		assertEquals(usuariosRecomendadosAChico, sistema.getFontesDeSonsRecomendadas(idSessaoChico));

	}
	

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente1() throws LoginInvalidoException,
			SessaoInexistenteException, UsuarioInexistenteException {
		sistema.seguirUsuario(39482, "Joao.Silva");
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente2() throws SessaoInexistenteException {
		sistema.getListaDeSeguidores(39482);
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente3() throws SessaoInexistenteException {
		sistema.getNumeroDeSeguidores(39482);
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente4() throws SessaoInexistenteException {
		sistema.getFonteDeSons(39482);
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente5() throws SessaoInexistenteException {
		sistema.getSonsFavoritos(39482);
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente6() throws SessaoInexistenteException {
		sistema.getFeedExtra(39482);
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente7() throws SessaoInexistenteException {
		sistema.getSonsFavoritos(39482);
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente8() throws SessaoInexistenteException,
			NomeInvalidoException {
		int idSessaoInvalido = 182721;
		sistema.criarLista(idSessaoInvalido, "Nome da Lista");
	}

	@Test(expected = SessaoInexistenteException.class)
	public void testaSessaoInexistente9() throws LoginInvalidoException,
			SessaoInexistenteException, UsuarioInexistenteException,
			NomeInvalidoException, UsuarioInvalidoException,
			ListaInvalidaException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.criarLista(idSessaoJoao, "Nome da Lista");

		int idSessaoInexistente = 124124;
		sistema.adicionaUsuarioNaLista(idSessaoInexistente, 1,
				sistema.getUsuarioID("Jiao.Silva"));
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido1() throws SessaoInexistenteException,
			NomeInvalidoException, LoginInvalidoException,
			UsuarioInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.criarLista(idSessaoJoao, "");
	}

	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido2() throws SessaoInexistenteException,
			NomeInvalidoException, LoginInvalidoException,
			UsuarioInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.criarLista(idSessaoJoao, null);
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido1() throws LoginInvalidoException,
			UsuarioInexistenteException {
		sistema.abrirSessao("Joao.Silva", "jiao123");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido2() throws LoginInvalidoException,
			UsuarioInexistenteException {
		sistema.abrirSessao("Joao.Silva", "ze123");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido3() throws LoginInvalidoException,
			UsuarioInexistenteException {
		sistema.abrirSessao(null, "joao123");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido4() throws LoginInvalidoException,
			UsuarioInexistenteException {
		sistema.abrirSessao("", "joao123");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido5() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");

		sistema.seguirUsuario(idSessaoJoao, null);
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido6() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");

		sistema.seguirUsuario(idSessaoJoao, "");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido8() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");

		sistema.seguirUsuario(idSessaoJoao, "Joao.Silva");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInvalido9() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException {
		sistema.getUsuarioID("");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistente1() throws LoginInvalidoException,
			UsuarioInexistenteException {
		sistema.abrirSessao("Jiao.Silva", "joao123");
	}

	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistente2() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");

		sistema.seguirUsuario(idSessaoJoao, "Josefino");
	}

	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistente3() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException,
			UsuarioInvalidoException, ListaInvalidaException,
			NomeInvalidoException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.criarLista(idSessaoJoao, "Nome da Lista");

		int IDUsuarioInexistente = 1231231421;
		sistema.adicionaUsuarioNaLista(idSessaoJoao, 1, IDUsuarioInexistente);
	}
	
	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistente4() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException,
			UsuarioInvalidoException, ListaInvalidaException,
			NomeInvalidoException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idInvalido = 124124;
		sistema.getNumeroDeFontesDeSonsEmComum(idSessaoJoao, idInvalido);
	
	}
	
	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioInexistente5() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException,
			UsuarioInvalidoException, ListaInvalidaException,
			NomeInvalidoException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		int idInvalido = 124124;
		sistema.getNumeroDeFavoritosEmComum(idSessaoJoao, idInvalido);
	
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = UsuarioInvalidoException.class)
	public void testaUsuarioInvalido1() throws SessaoInexistenteException,
			UsuarioInexistenteException, UsuarioInvalidoException,
			ListaInvalidaException, NomeInvalidoException,
			LoginInvalidoException {
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.criarLista(idSessaoJoao, "Nome da Lista");

		sistema.adicionaUsuarioNaLista(idSessaoJoao, 1,
				sistema.getIDUsuario(idSessaoJoao));
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = EmailJaExisteException.class)
	public void testaEmailJaExiste() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Joao.Silva@gmail.com");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = LoginJaExisteException.class)
	public void testaLoginJaExisteException() throws LoginInvalidoException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException {
		sistema.criarUsuario("Joao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = ListaInvalidaException.class)
	public void testaListaInvalida1() throws LoginInvalidoException,
			UsuarioInexistenteException, SessaoInexistenteException,
			UsuarioInvalidoException, ListaInvalidaException,
			NomeInvalidoException, EmailInvalidoException,
			LoginJaExisteException, EmailJaExisteException {
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz",
				"Jiao.Silva@gmail.com");
		int idSessaoJoao = sistema.abrirSessao("Joao.Silva", "joao123");

		sistema.adicionaUsuarioNaLista(idSessaoJoao, 1,
				sistema.getUsuarioID("Jiao.Silva"));
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido1() throws DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			LoginInvalidoException, UsuarioInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException {
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao, "", "99/99/9999");
	}

	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido2() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException {
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao, null, "00/00/0000");
	}

	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido3() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException {
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao, "www.youtube.com/watch?v=r-fIOrUTIOQ",
				"31/02/2014");
	}

	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido4() throws LoginInvalidoException,
			UsuarioInexistenteException, DiaInvalidoException,
			MesInvalidoException, SessaoInexistenteException,
			NumberFormatException, AnoInvalidoException, SomInvalidoException,
			DataDeCriacaoInvalidaException {
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,
				"ftp://www.youtube.com/watch?v=r-fIOrUTIOQ", "31/02/2014");
	}

	@Test(expected = SomInexistenteException.class)
	public void testaSomInvalido5() throws SessaoInexistenteException,
			SomInvalidoException, LoginInvalidoException,
			UsuarioInexistenteException, SomInexistenteException {
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.favoritarSom(idSessao, 39482);
	}
	
}
