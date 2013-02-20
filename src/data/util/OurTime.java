package data.util;

public class OurTime implements Comparable<OurTime> {


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
		// TODO Auto-generated constructor stub
	}
	/**
	 * Construtor a partir da hora e data atual
	 */
	public OurTime() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Construtor a partir de uma data
	 * @param dia
	 * @param mes
	 * @param ano
	 */
	public OurTime(int dia, int mes, int ano) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retorna o horario formatado a seguir: <Hora>:<Minuto>:<Segundo>
	 * @return Horario Formatado
	 */
	public String getHorarioToString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retorna a data formatada a seguir: <Dia>/<Mes>/<
	 * @return 
	 */
	public String getDataToString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retorna o segundo OurTime
	 * @return Segundo
	 */
	public int getSegundo() {
		// TODO Auto-generated method stub
		return -1;
	}
    /**
     * Retorna o minuto OurTime
     * @return Minuto
     */
	public int getMinuto() {
		// TODO Auto-generated method stub
		return -1;
	}
 
	/**
	 * Retorna a hora OurTime
	 * @return Hora
	 */
	public int getHora() {
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * Retorna o ano OurTime
	 * @return Ano
	 */
	public int getAno() {
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * Retorna o mes OurTime
	 * @return Mes
	 */
	public int getMes() {
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * Retorna o dia OurTime
	 * @return Dia
	 */
	public int getDia() {
		// TODO Auto-generated method stub
		return -1;
	}
	
	@Override
	public int compareTo(OurTime o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
