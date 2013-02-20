package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.som.Som;
import data.util.OurTime;
import exceptions.system.SomInvalidoException;

public class TestaSom {
	
	private Som som;
	private OurTime data;

	@Before
	public void setUp(){
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
	public void testaSomInvalido1(){
		som = new Som("www.youtube.com/watch?v=r-fIOrUTIOQ", data);
	}
	
	@Test(expected = SomInvalidoException.class)
	public void testaSomInvalido2(){
		som = new Som("ftp://www.youtube.com/watch?v=r-fIOrUTIOQ", data);
	}

}