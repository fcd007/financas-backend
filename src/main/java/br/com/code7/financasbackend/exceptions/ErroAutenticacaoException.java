package br.com.code7.financasbackend.exceptions;

public class ErroAutenticacaoException extends RuntimeException {

	public ErroAutenticacaoException(String mensagem) {
		super(mensagem);
	}
}
