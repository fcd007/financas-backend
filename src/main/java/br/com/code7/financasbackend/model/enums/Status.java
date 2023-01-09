package br.com.code7.financasbackend.model.enums;

public enum Status {

	CAN("CANCELADO"), DES("DESPESA");

	private final String nome;

	Status(String nome) {
		this.nome = nome;
	}

	public String descricao() {
		return nome;
	}

	public String toString() {
		return nome;
	}
}
