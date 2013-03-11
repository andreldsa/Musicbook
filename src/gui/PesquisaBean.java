package gui;

import gui.exceptions.CampoDePesquisaVazioException;
import gui.util.Contexto;
import gui.util.Messages;
import gui.util.ValidaCampo;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import data.system.Sistema;
import data.user.Usuario;
/**
 *	Controlador da pagina de pesquisa de usuario. 
 */
@ManagedBean(name="pesquisa")
@ViewScoped
public class PesquisaBean {

	private static final String CAMPO_DE_PESQUISA_VAZIO = "Campo de pesquisa vazio!";

	private Sistema sistema = (Sistema) Contexto.getInContext(
			Contexto.Variaveis.SISTEMA.getNome());
	
	private String input;
	
	private List<Usuario> resultado;
	
	/**
	 * Efetua uma pesquisa por usuario.
	 * @return string Pagina resultado da pesquisa
	 * @throws CampoDePesquisaVazioException 
	 */
	public void efetuaPesquisa() {
		if (ValidaCampo.valida(input, CAMPO_DE_PESQUISA_VAZIO)) {
			setResultado(sistema.pesquisaUsuario(input));
		}
		Messages.addMsgErro(CAMPO_DE_PESQUISA_VAZIO);
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public List<Usuario> getResultado() {
		//TODO remover usuario logado do resultado da pesquisa.
		return resultado;
	}

	public void setResultado(List<Usuario> resultado) {
		this.resultado = resultado;
	}
}
