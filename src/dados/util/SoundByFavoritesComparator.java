package dados.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

import dados.som.Som;

public class SoundByFavoritesComparator implements Comparator<Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer,Som> sons;
	public SoundByFavoritesComparator( Map<Integer,Som> sonsCadastrados ){
		this.sons = sonsCadastrados;
	}
	@Override
	public int compare(Integer somId1, Integer somId2) {
		int diff = sons.get(somId1).getFavoritagens() - sons.get(somId2).getFavoritagens();
		if(diff == 0)
			return - sons.get(somId1).getDataCriacao().compareTo(sons.get(somId2).getDataCriacao());
		return - diff;
	}

}
