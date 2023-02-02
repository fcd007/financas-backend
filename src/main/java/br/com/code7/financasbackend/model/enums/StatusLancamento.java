package br.com.code7.financasbackend.model.enums;

public enum StatusLancamento {

	PENDENTE("Pendente"), CANCELADO("Cancelado"), FATURADO("Faturado"), EFETIVADO("Efetivado");

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
