package data.som;

import data.util.OurTime;
import exceptions.system.SomInvalidoException;

public class Som implements Comparable<Som> {
	
	private String link;
	private OurTime dataDeCriacao;
	private int ID;
	private int favoritagens;
	private int idCriador;
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
		this.favoritagens = 0;
	}
	
	/**
	 * Contrutor a partir de um link e a data de criacao informada
	 * @param link Link
	 * @param dataCriacao Data de Criacao
	 * @throws SomInvalidoException 
	 */
	public Som(String link, OurTime dataCriacao , int ID, int idCriador) throws SomInvalidoException  {
		this(link, dataCriacao);
		this.ID = ID;
		this.idCriador = idCriador;
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
	
	/**
	 * Retorna o ID do som
	 * @return
	 */
	public int getID() {
		return ID;
	}
	/**
	 * Incrementa o numero de favoritagens recebidos por esse som em um.
	 */
	public void incrementaFavoritagens() {
		this.favoritagens++;
	}
	/**
	 * Retorna o numero de vezes que esse som foi favoritado
	 * @return
	 */
	public int getFavoritagens() {
		
		return this.favoritagens;
	}
	/**
	 * O id do usuario que postou esse som
	 * @return
	 */
	public Integer getPosterId() {
		return idCriador;
	}

}
