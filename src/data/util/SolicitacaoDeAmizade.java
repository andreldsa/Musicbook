package data.util;

import data.user.Usuario;

public class SolicitacaoDeAmizade {
	private int ID;
	private Usuario remetente,destinatario;
	/**
	 * 
	 * @param iD id da solicitacao
	 * @param remetente usuario q enviou a solicitacao
	 * @param destinatario usuario q recebeu a solicitacao
	 */
	public SolicitacaoDeAmizade(int iD, Usuario remetente, Usuario destinatario) {
		super();
		ID = iD;
		this.remetente = remetente;
		this.destinatario = destinatario;
	}
	
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the remetente
	 */
	public Usuario getRemetente() {
		return remetente;
	}

	/**
	 * @return the destinatario
	 */
	public Usuario getDestinatario() {
		return destinatario;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SolicitacaoDeAmizade))
			return false;
		SolicitacaoDeAmizade other = (SolicitacaoDeAmizade) obj;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (remetente == null) {
			if (other.remetente != null)
				return false;
		} else if (!remetente.equals(other.remetente))
			return false;
		return true;
	}
	

}
