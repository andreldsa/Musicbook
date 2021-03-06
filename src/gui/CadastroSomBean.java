package gui;

import gui.util.Contexto;
import gui.util.Messages;
import gui.util.ValidaCampo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dados.sistema.Sistema;

@ManagedBean(name = "cdSom")
@ViewScoped
public class CadastroSomBean {

	private Sistema sistema = (Sistema) Contexto
			.getInContext(Contexto.Variaveis.SISTEMA.getNome());

	private String som;

	private static final String SOM_NAO_DIGITADO = "Digite um link que aponta para o som.";

	private static final String MSG_DIGITE_SOM = "Digite aqui um link para uma m�sica!";
	private static final String MSG_SOM_POSTADO_COM_SUCESSO = "Som postado com sucesso!";

	/**
	 * Posta um novo som no sistema.
	 */
	public String postaSom(int idSessao) {
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formatador.format(new Date());
		if (ValidaCampo.valida(som, SOM_NAO_DIGITADO)) {
			try {
				sistema.postarSom(idSessao, som, dataFormatada);
				Messages.addMsgSucesso(MSG_SOM_POSTADO_COM_SUCESSO);
			} catch (Exception erro) {
				Messages.addMsgErro(erro.getMessage());
			}
		}
		return "";
	}

	/**
	 * Limpa o campo de Som.
	 */
	public String limpaCampos() {
		this.som = MSG_DIGITE_SOM;
		return "";
	}

	public String getSom() {
		return som;
	}

	public void setSom(String som) {
		this.som = som;
	}
}
