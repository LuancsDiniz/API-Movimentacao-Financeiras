package br.com.coderbank.apimovimentacaofinanceira.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException{

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
