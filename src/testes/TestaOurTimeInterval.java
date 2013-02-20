package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.util.OurTime;
import data.util.OurTimeInterval;

public class TestaOurTimeInterval {
	
	private OurTimeInterval intervalo1, intervalo2;

	@Before
	public void testaIntervaloDeTempo() {
		OurTime data1,data2,data3,data4;
	
		data1 = new OurTime(10,12,1993,14,23,45);
		data2 = new OurTime(2,4,2003,8,59,15);
		intervalo1 = new OurTimeInterval(data1,data2);
		
		data3 = new OurTime(10,2,2013,22,31,56);
		data4 = new OurTime(10,2,2013,21,12,23);
		intervalo2 = new OurTimeInterval(data3,data4);
	}
	
	@Test
	public void testaGetIntervaloEmDias(){
		assertEquals(3399, intervalo1.getIntervaloEmDias());
		assertEquals(0, intervalo2.getIntervaloEmDias());
	}
	
	@Test
	public void testaGetIntervaloEmHoras(){
		assertEquals(81594, intervalo1.getIntervaloEmHoras());
		assertEquals(1, intervalo2.getIntervaloEmHoras());
	}
	
	@Test
	public void testaGetIntervaloEmMinutos(){
		assertEquals(4895674, intervalo1.getIntervaloEmMinutos());
		assertEquals(79, intervalo2.getIntervaloEmMinutos());
	}
	
	@Test
	public void testaGetIntervaloEmSegundos(){
		assertEquals(293740470, intervalo1.getIntervaloEmSegundos());
		assertEquals(4773, intervalo2.getIntervaloEmSegundos());
	}
	
	@Test
	public void testaGetIntervalo(){
		assertEquals("293740470 Dias", intervalo1.getIntervalo());
		assertEquals("1 Hora", intervalo2.getIntervalo());
	}

}
