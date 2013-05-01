package gui;

import java.util.ArrayList;
import java.util.List;

import gui.util.Contexto;
import gui.util.Messages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dados.sistema.Sistema;
import dados.som.Som;
import exceptions.system.SessaoInexistenteException;
import exceptions.user.ListaInvalidaException;
import exceptions.user.NomeInvalidoException;

@ManagedBean(name = "listas")
@SessionScoped
public class ListasUsuariosBean {

	private Sistema sistema;
	private SessaoUsuario sessao;
	private int idSessao;
	
	private String nomeLista;

	public ListasUsuariosBean() {
		sistema = (Sistema) Contexto.getInContext(Contexto.Variaveis.SISTEMA
				.getNome());
		sessao = (SessaoUsuario) (SessaoUsuario) Contexto.getInContext("sessao"
				+ getSessao());
		if (sessao != null) {
			setIdSessao(sessao.getId());
		}
	}
	
	private String getSessao() {
		return Contexto.getExternalContext().getRequestParameterMap().get("sessao");
	}

	public void setIdSessao(int id) {
		this.idSessao = id;
	}

	public int getIdSessao() {
		return sessao.getId();
	}

	public List<Som> getSonsEmLista() {
		List<Som> listaSons = new ArrayList<Som>();
		try {
			for (Integer idSom : sistema.getSonsEmLista(idSessao, 1)) {
				listaSons.add(sistema.getSom(idSom));
			}
		} catch (SessaoInexistenteException e) {
			Messages.addMsgErro(e.getMessage());
		} catch (ListaInvalidaException e) {
			Messages.addMsgErro(e.getMessage());
		}
		return listaSons;
	}
	
	public void criaLista() {
		int idLista;
		try {
			idLista = sistema.criarLista(idSessao, getNomeLista());
			sistema.getUsuarioLogado(idSessao).criaListaDeUsuario(idLista, getNomeLista());
			Messages.addMsgSucesso("Lista cadastrada com sucesso.");
		} catch (SessaoInexistenteException e) {
			e.printStackTrace();
		} catch (NomeInvalidoException e) {
			e.printStackTrace();
		}
	}

	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}
}
