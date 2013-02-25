package data.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class OurTime implements Comparable<OurTime> {

private Calendar cal;
	/**
	 * Construtor a partir de um horario e uma data
	 * @param dia 
	 * @param mes
	 * @param ano
	 * @param hora
	 * @param minuto
	 * @param segundo
	 */
	public OurTime(int dia, int mes, int ano, int hora, int minuto, int segundo) {
		//TODO olha essa praga
		this.cal = new GregorianCalendar(ano,mes,dia,hora,minuto,segundo);
		
	}
	/**
	 * Construtor a partir da hora e data atual
	 */
	public OurTime() {
		this.cal = new GregorianCalendar();
	}
	/**
	 * Construtor a partir de uma data
	 * @param dia
	 * @param mes
	 * @param ano
	 */
	public OurTime(int dia, int mes, int ano) {
		this.cal = new GregorianCalendar(ano, mes, dia);
	}

	/**
	 * Retorna o horario formatado a seguir: <Hora>:<Minuto>:<Segundo>
	 * @return Horario Formatado
	 */
	public String getHorarioToString() {
		String horario = cal.get(Calendar.HOUR) + ":" +
				cal.get(Calendar.MINUTE) +":" +
				cal.get(Calendar.SECOND); 
		return horario;
	}

	/**
	 * Retorna a data formatada a seguir: <Dia>/<Mes>/<
	 * @return 
	 */
	public String getDataToString() {
		String data = cal.get(Calendar.DAY_OF_MONTH) + ":" +
				cal.get(Calendar.MONTH) +":" +
				cal.get(Calendar.YEAR); 
		return data;
	}

	/**
	 * Retorna o segundo OurTime
	 * @return Segundo
	 */
	public int getSegundo() {
		return cal.get(Calendar.SECOND);
	}
    /**
     * Retorna o minuto OurTime
     * @return Minuto
     */
	public int getMinuto() {
		return cal.get(Calendar.MINUTE);
	}
 
	/**
	 * Retorna a hora OurTime
	 * @return Hora
	 */
	public int getHora() {
		return cal.get(Calendar.HOUR);
	}

	/**
	 * Retorna o ano OurTime
	 * @return Ano
	 */
	public int getAno() {
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Retorna o mes OurTime
	 * @return Mes
	 */
	public int getMes() {
		return cal.get(Calendar.MONTH);
	}

	/**
	 * Retorna o dia OurTime
	 * @return Dia
	 */
	public int getDia() {
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	@Override
	public int compareTo(OurTime o) {
		return (int)( cal.getTimeInMillis() - o.cal.getTimeInMillis());
	}

}
