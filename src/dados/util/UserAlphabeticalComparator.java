package dados.util;
import java.util.Comparator;

import dados.usuario.Usuario;
public class UserAlphabeticalComparator implements Comparator<Usuario>{

	@Override
	public int compare(Usuario arg0, Usuario arg1) {
		return arg0.getNome().compareTo(arg1.getNome());
	}

}
