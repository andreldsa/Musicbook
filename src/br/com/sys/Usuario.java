package br.com.sys;

import java.io.Serializable;

public class Usuario implements Serializable{

	private static final long serialVersionUID = 6665091982820265528L;
	
	private String nome;
	private String senha;
	
	public Usuario(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
	}

	public String getSenha() {
		return null;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean verificaSenha(String senha) {
		return this.senha.equals(senha);
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Usuario)) {
			return false;
		}
		Usuario use = (Usuario) obj;
		return use.getNome().equals(this.nome) &&
				use.verificaSenha(this.senha);
	}
	

}
