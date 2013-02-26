package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import data.util.OurTime;
import data.util.OurTimeInterval;
import exceptions.time.DiaInvalidoException;
import exceptions.time.MesInvalidoException;

public class TestaOurTimeInterval {
	OurTime data1,data2,data3,data4;

	@Before
	public void testaIntervaloDeTempo() throws DiaInvalidoException, MesInvalidoException {


		data1 = new OurTime(10,12,1993,14,23,45);
		data2 = new OurTime(2,4,2003,8,59,15);

		data3 = new OurTime(10,2,2013,22,31,56);
		data4 = new OurTime(10,2,2013,21,12,23);
	}

	@Test
	public void testaGetIntervaloEmDias(){
		assertEquals(3398, OurTimeInterval.getIntervaloEmDias(data1,data2));
		assertEquals(0, OurTimeInterval.getIntervaloEmDias(data3,data4));
	}

	@Test
	public void testaGetIntervaloEmHoras(){
		assertEquals(81570, OurTimeInterval.getIntervaloEmHoras(data1,data2));
		assertEquals(1, OurTimeInterval.getIntervaloEmHoras(data3,data4));
	}

	@Test
	public void testaGetIntervaloEmMinutos(){
		assertEquals(4894235, OurTimeInterval.getIntervaloEmMinutos(data1,data2));
		assertEquals(79, OurTimeInterval.getIntervaloEmMinutos(data3,data4));
	}

	@Test
	public void testaGetIntervaloEmSegundos(){
		assertEquals(293654130, OurTimeInterval.getIntervaloEmSegundos(data1,data2));
		assertEquals(4773, OurTimeInterval.getIntervaloEmSegundos(data3,data4));
	}

	@Test
	public void testaGetIntervalo(){
		assertEquals("3398 Dias", OurTimeInterval.getIntervalo(data1,data2));
		assertEquals("1 Hora", OurTimeInterval.getIntervalo(data3,data4));
	}

}
