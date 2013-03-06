package data.som;

import data.util.OurTime;
import exceptions.system.SomInvalidoException;

public class Som implements Comparable<Som> {
	
	private String link;
	private OurTime dataDeCriacao;
	private int ID;
	/**
	 * Contrutor a partir de um link e a data de criacao informada
	 * @param link Link
	 * @param dataCriacao Data de Criacao
	 * @throws SomInvalidoException 
	 */
	public Som(String link, OurTime dataCriacao) throws SomInvalidoException  {
		if(!link.startsWith("http://") && !link.startsWith("https://")){
			throw new SomInvalidoException("Som inválido.");
		}
		this.link = link;
		this.dataDeCriacao = dataCriacao;
	}
	
	/**
	 * Contrutor a partir de um link e a data de criacao informada
	 * @param link Link
	 * @param dataCriacao Data de Criacao
	 * @throws SomInvalidoException 
	 */
	public Som(String link, OurTime dataCriacao , int ID) throws SomInvalidoException  {
		this(link, dataCriacao);
		this.ID = ID;
	}

	/**
	 * Retorna o link do Som
	 * @return Link do Som
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Retorna a data de criacao
	 * @return Data de Criacao
	 */
	public OurTime getDataCriacao() {
		return dataDeCriacao;
	}
	
	@Override
	public int compareTo(Som o) {
		return dataDeCriacao.compareTo(o.dataDeCriacao);
	}

	public int getID() {
		return ID;
	}

}
