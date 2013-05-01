package dados.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

import dados.som.Som;
import dados.usuario.Usuario;

public class SoundByFavoritedPersonComparator  implements Comparator<Integer>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer,Som> sons;
	private Usuario user;
	
	public SoundByFavoritedPersonComparator( Map<Integer,Som> sonsCadastrados,Usuario user ){
		this.sons = sonsCadastrados;
		this.user = user;
	}
	@Override
	public int compare(Integer somId1, Integer somId2) {
		int diff = -(user.getFavoritagensDoUsuario(sons.get(somId1).getPosterId())  -
				user.getFavoritagensDoUsuario(sons.get(somId2).getPosterId()));
		if (diff == 0){
			diff = -(user.getFontesDeSons().indexOf(sons.get(somId1).getPosterId()) -
					user.getFontesDeSons().indexOf(sons.get(somId2).getPosterId()));
			if(diff== 0){
				return  - sons.get(somId1).getDataCriacao().compareTo(sons.get(somId2).getDataCriacao());
			}else {
				return diff;
			}
		}else{
			return diff;
		}
	}
	
	public void setUser(Usuario user){
		this.user = user;
	}

}
