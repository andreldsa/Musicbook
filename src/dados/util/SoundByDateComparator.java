package dados.util;

import java.util.Comparator;
import java.util.Map;

import dados.som.Som;

public class SoundByDateComparator implements Comparator<Integer> {
	
	private Map<Integer,Som> sonsCadastrados;
	public SoundByDateComparator( Map<Integer,Som> sonsCadastrados ){
		this.sonsCadastrados = sonsCadastrados;
	}
	@Override
	public int compare(Integer somId1, Integer somId2) {
		return - sonsCadastrados.get(somId1).getDataCriacao().compareTo(sonsCadastrados.get(somId2).getDataCriacao());
	}

	
}
