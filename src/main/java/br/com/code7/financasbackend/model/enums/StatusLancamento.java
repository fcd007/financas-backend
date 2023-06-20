package br.com.code7.financasbackend.model.enums;

public enum StatusLancamento {

	PENDENTE("PENDENTE"), CANCELADO("CANCELADO"), FATURADO("FATURADO"), EFETIVADO("EFETIVADO");

	private final String nome;

	StatusLancamento(String nome) {
		this.nome = nome;
	}

	public String descricao() {
		return nome;
	}

	public String toString() {
		return nome;
	}

}
