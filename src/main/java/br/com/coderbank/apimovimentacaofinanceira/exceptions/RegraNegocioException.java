package br.com.coderbank.apimovimentacaofinanceira.exceptions;

public class RegraNegocioException extends RuntimeException{
    public RegraNegocioException(String message){
        super(message);
    }
}
