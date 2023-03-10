package br.com.code7.financasbackend.model.enums;

public enum TipoLancamento {

	RECEITA("RECEITA"), DESPESA("DESPESA");

	private final String nome;

	TipoLancamento(String nome) {
		this.nome = nome;
	}

	public String descricao() {
		return nome;
	}

	public String toString() {
		return nome;
	}
}
