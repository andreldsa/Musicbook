package data.util;

public class OurTimeInterval {

	/**
	 * Retorna um numero inteiro representando o intervalo em dias
	 * @return Intervalo em Dias
	 */
	public static int getIntervaloEmDias( OurTime time1, OurTime time2) {
		long diff = time1.getDiffEmMillis(time2);
		diff = diff/(1000*60*60*24);
		return (int) Math.abs(diff);
	}

	/**
	 * Retorna um numero inteiro representando o intervalo em horas
	 * @return Intervalo em Horas
	 */
	public static int getIntervaloEmHoras(OurTime time1, OurTime time2) {
		long diff = time1.getDiffEmMillis(time2);
		diff = diff/(1000*60*60);
		return (int) Math.abs(diff);
	}
	
	/**
	 * Retorna um numero inteiro representando o intervalo em minutos
	 * @return Intervalo em Minutos
	 */
	public static int getIntervaloEmMinutos(OurTime time1, OurTime time2) {
		long diff = time1.getDiffEmMillis(time2);
		diff = diff/(1000*60);
		return (int) Math.abs(diff);
	}
	
	/**
	 * Retorna um numero inteiro representando o intervalo em segundos
	 * @return Intervalo em Segundos
	 */
	public static int getIntervaloEmSegundos(OurTime time1, OurTime time2) {
		long diff = time1.getDiffEmMillis(time2);
		diff = diff/(1000);
		return (int) Math.abs(diff);
	}

	/**
	 * Retorna uma string representando o maior intervalo diferente de 0 no seguinte formato: "<Quantidade> <Medida de Tempo>"
	 * @return Intervalo de Tempo
	 */
	public static String getIntervalo(OurTime time1, OurTime time2) {
		if(getIntervaloEmDias(time1, time2) != 0)return getIntervaloEmDias(time1, time2) + " Dia" + (getIntervaloEmDias(time1, time2) >1? "s": "");
		else if(getIntervaloEmHoras(time1, time2) != 0)return getIntervaloEmHoras(time1, time2) + " Hora" + (getIntervaloEmHoras(time1, time2) >1? "s": "");
		else if(getIntervaloEmMinutos(time1, time2) != 0)return getIntervaloEmMinutos(time1, time2) + " Minuto" + (getIntervaloEmMinutos(time1, time2) >1? "s": "");
		return getIntervaloEmSegundos(time1, time2) + " Segundo" + (getIntervaloEmSegundos(time1, time2) >1? "s": "");

	}

}
