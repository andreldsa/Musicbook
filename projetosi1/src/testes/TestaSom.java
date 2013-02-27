package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.som.Som;
import data.util.OurTime;
import exceptions.system.SomInvalidoException;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;

public class TestaSom {
	
	private Som som;
	private OurTime data;

	@Before
	public void setUp() throws DiaInvalidoException, MesInvalidoException, SomInvalidoException, AnoInvalidoException{
		data = new OurTime(14,8,2014);
		som = new Som("http://www.youtube.com/watch?v=r-fIOrUTIOQ", data);
	}

	@Test
	public void testaGetLink() {
		assertEquals("http://www.youtube.com/watch?v=r-fIOrUTIOQ", som.getLink());
	}
	
	@Test
	public void testaGetDataCriacao() {
		assertEquals(data, som.getDataCriacao());
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido1() throws SomInvalidoException{
		som = new Som("www.youtube.com/watch?v=r-fIOrUTIOQ", data);
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido2() throws SomInvalidoException{
		som = new Som("ftp://www.youtube.com/watch?v=r-fIOrUTIOQ", data);
	}

}
