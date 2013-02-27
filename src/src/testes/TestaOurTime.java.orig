package testes;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import data.util.OurTime;
import exceptions.time.AnoInvalidoException;
import exceptions.time.DiaInvalidoException;
import exceptions.time.HorarioNaoInformadoException;
import exceptions.time.MesInvalidoException;

public class TestaOurTime {
	OurTime data1,data2,data3;
	
	
	@Before
	public void setUp() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(20,5,1982);
		data2 = new OurTime(25,3,1995,18,20,45);
		data3 = new OurTime();
	}
	
	// Dia Invalido Tests
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido1() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(32,5,2000);	
	}
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido2() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(32,5,2000,16,34,45);
	}
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido3() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(29,2,2015);
	}
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido4() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(29,2,2015,16,34,45);
	}
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido5() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(31,4,2015);
	}
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido6() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(31,4,2015,16,34,45);
		
	}
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido7() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(-1,2,2015);
	}
	
	@Test(expected = DiaInvalidoException.class)
	public void testaDiaInvalido8() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(-1,5,2015,16,34,45);
	}
	
	 // Mes Invalido
	
	
	@Test(expected = MesInvalidoException.class)
	public void testaMesInvalido1() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(12,30,2000);
	}
	
	@Test(expected = MesInvalidoException.class)
	public void testaMesInvalido2() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(12,30,2000,16,34,45);
	}
	
	@Test(expected = MesInvalidoException.class)
	public void testaMesInvalido3() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(13,-1,2015);
	}
	
	@Test(expected = MesInvalidoException.class)
	public void testaMesInvalido4() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(13,-1,2015,16,34,45);
	}
	
	// Ano Invalido
	
	@Test(expected = AnoInvalidoException.class)
	public void testaAnoInvalido1() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(12,4,15);
	}
	
	@Test(expected = AnoInvalidoException.class)
	public void testaAnoInvalido2() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(12,4,12,16,34,45);
	}
	
	@Test(expected = AnoInvalidoException.class)
	public void testaAnoInvalido3() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(13,4,11);
	}

	@Test(expected = AnoInvalidoException.class)
	public void testaAnoInvalido4() throws DiaInvalidoException, MesInvalidoException{
		data1 = new OurTime(13,4,12,16,34,45);
	}
	
	
	
	@Test
	public void testaDia() {
		assertEquals(20, data1.getDia());
		assertEquals(25, data2.getDia());
		
		int diaAtual = new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH);
		assertEquals(diaAtual, data3.getDia());
	}
	
	@Test
	public void testaMes() {
		assertEquals(5, data1.getMes());
		assertEquals(3, data2.getMes());
		
		int mesAtual = new GregorianCalendar().get(GregorianCalendar.MONTH);	
		assertEquals(mesAtual, data3.getMes());
	}
	
	@Test
	public void testaAno() {
		assertEquals(1982, data1.getAno());
		assertEquals(1995, data2.getAno());
		
		int anoAtual = new GregorianCalendar().get(GregorianCalendar.YEAR);	
		assertEquals(anoAtual, data3.getAno());
	}
	
	@Test
	public void testaHora() {
		assertEquals(18, data2.getHora());
		
		int horaAtual = new GregorianCalendar().get(GregorianCalendar.HOUR_OF_DAY);	
		assertEquals(horaAtual, data3.getHora());	
	}
	
	@Test
	public void testaMinuto() {
		assertEquals(18, data2.getMinuto());
		
		int minutoAtual = new GregorianCalendar().get(GregorianCalendar.MINUTE);	
		assertEquals(minutoAtual, data3.getMinuto());	
	}
	
	@Test
	public void testaSegundo() {
		assertEquals(45, data2.getSegundo());
		
		int segundoAtual = new GregorianCalendar().get(GregorianCalendar.SECOND);	
		assertEquals(segundoAtual, data3.getSegundo());	
	}
	
	@Test
	public void testaHorarioToString(){
		assertEquals("18:20:45", data2.getHorarioToString());
		
		StringBuilder horarioAtual = new StringBuilder();
		horarioAtual.append(new GregorianCalendar().get(GregorianCalendar.HOUR_OF_DAY));
		horarioAtual.append(":");
		horarioAtual.append(new GregorianCalendar().get(GregorianCalendar.MINUTE));
		horarioAtual.append(":");
		horarioAtual.append(new GregorianCalendar().get(GregorianCalendar.SECOND));
		assertEquals(horarioAtual.toString(), data3.getHorarioToString());	
	}
	
	@Test
	public void testaDataToString(){
		assertEquals("20/05/1982", data1.getDataToString());
		assertEquals("25/03/1995", data2.getDataToString());
		//fica 25/1/2013 , sem o 0 do mes
		/*StringBuilder dataAtual = new StringBuilder();
		dataAtual.append(new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH));
		dataAtual.append("/");
		dataAtual.append(new GregorianCalendar().get(GregorianCalendar.MONTH));
		dataAtual.append("/");
		dataAtual.append(new GregorianCalendar().get(GregorianCalendar.YEAR));
		assertEquals(dataAtual.toString(), data3.getDataToString());	*/
		
	}
	
	@Test(expected = HorarioNaoInformadoException.class)
	public void testaHorarioToStringException(){
		data1.getHorarioToString();
	}
	
	@Test(expected = HorarioNaoInformadoException.class)
	public void testaGetHoraExeption(){
		data1.getHora();
	}
	
	@Test(expected = HorarioNaoInformadoException.class)
	public void testaGetMinutoExeption(){
		data1.getMinuto();
	}
	
	@Test(expected = HorarioNaoInformadoException.class)
	public void testaGetSegundoExeption(){
		data1.getSegundo();
	}
}
