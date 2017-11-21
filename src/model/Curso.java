package model;

import java.io.Serializable;

public class Curso implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	
	public Curso(String descricao) {
		this.nome = descricao;
	}
	
	public Curso() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}