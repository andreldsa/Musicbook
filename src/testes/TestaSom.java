package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dados.som.Som;
import dados.util.OurTime;
import exceptions.system.SomInvalidoException;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;

public class TestaSom {

	private Som som;
	private OurTime data;

	@Before
	public void setUp() throws DiaInvalidoException, MesInvalidoException,
			AnoInvalidoException, SomInvalidoException {
		data = new OurTime(14, 8, 2014);
		som = new Som("http://www.youtube.com/watch?v=r-fIOrUTIOQ", data, 1, 1);
	}

	@Test
	public void testaGetLink() {
		assertEquals("http://www.youtube.com/watch?v=r-fIOrUTIOQ",
				som.getLink());
	}

	@Test
	public void testaGetDataCriacao() {
		assertEquals(data, som.getDataCriacao());
	}

	@Test
	public void testaFavoritagens() {
		int contadorFavoritagens;

		for (contadorFavoritagens = 0; contadorFavoritagens < 10; contadorFavoritagens++)
			som.incrementaFavoritagens();

		assertEquals(10, som.getFavoritagens());
	}

	@Test
	public void testaIDs() {
		assertEquals(1, som.getID());
		assertEquals(1, (int) som.getPosterId());
	}

	@Test
	public void testaCompareTo() throws SomInvalidoException,
			DiaInvalidoException, MesInvalidoException, AnoInvalidoException {
		Som s1, s2;

		s1 = new Som(
				"http://www.youtube.com/watch?v=EkcnEwd1kL8&list=PL239AAED141C78529",
				new OurTime(27, 03, 2014), 1, 1);
		s2 = new Som(
				"http://www.youtube.com/watch?v=GQCQuQtop-M&list=PL239AAED141C78529",
				new OurTime(28, 03, 2014), 2, 1);

		assertTrue(s1.compareTo(s2) < 0);
		assertTrue(s1.compareTo(s1) == 0);
		assertTrue(s2.compareTo(s1) > 0);
	}

	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido1() throws SomInvalidoException {
		som = new Som("www.youtube.com/watch?v=r-fIOrUTIOQ", data, 0, 0);
	}

	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido2() throws SomInvalidoException {
		som = new Som("ftp://www.youtube.com/watch?v=r-fIOrUTIOQ", data, 1, 1);
	}
}
