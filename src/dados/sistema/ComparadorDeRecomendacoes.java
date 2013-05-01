package dados.sistema;

import java.util.Comparator;
import java.util.Map;

import dados.usuario.Usuario;
import dados.util.ListUtils;

public class ComparadorDeRecomendacoes implements Comparator<Integer> {

	private Usuario usuarioLogado;
	private Map<Integer, Usuario> usuarios;

	public ComparadorDeRecomendacoes(Usuario logado,
			Map<Integer, Usuario> usuarios) {
				this.usuarioLogado = logado;
				this.usuarios = usuarios;
	}

	@Override
	public int compare(Integer idUsuario1, Integer idUsuario2) {
		int diff = 0;
		Usuario user1 = usuarios.get(idUsuario1);
		Usuario user2 = usuarios.get(idUsuario2);
		if(usuarioLogado.getSonsFavoritos().isEmpty() && usuarioLogado.getFontesDeSons().isEmpty()){
			diff = -(user1.getSonsFavoritos().size() - user2.getSonsFavoritos().size());
		}else {
			diff = - ((ListUtils.elementosEmComum(usuarioLogado.getSonsFavoritos(), user1.getSonsFavoritos()) + 
					ListUtils.elementosEmComum(usuarioLogado.getFontesDeSons(), user1.getFontesDeSons())) - 
					(ListUtils.elementosEmComum(usuarioLogado.getSonsFavoritos(), user2.getSonsFavoritos()) + 
							ListUtils.elementosEmComum(usuarioLogado.getFontesDeSons(), user2.getFontesDeSons())));
		}
		if(diff != 0) {
			return diff;
		}
		diff = -(usuarioLogado.getFavoritagensDoUsuario(idUsuario1) - usuarioLogado.getFavoritagensDoUsuario(idUsuario2));
		if(diff != 0) {
			return diff;
		}
		return user1.getNome().compareTo(user2.getNome());
	}

}
