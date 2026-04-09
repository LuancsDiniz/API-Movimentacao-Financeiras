package br.com.coderbank.apimovimentacaofinanceira.controllers;

import br.com.coderbank.apimovimentacaofinanceira.exceptions.RecursoNaoEncontradoException;
import br.com.coderbank.apimovimentacaofinanceira.exceptions.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({RecursoNaoEncontradoException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail notFound(final RecursoNaoEncontradoException exception) {

        final var exceptionMessage = exception.getMessage();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exceptionMessage);

        problemDetail.setTitle("Not found");
        problemDetail.setType(URI.create("https://www.coderbank.com.br/fordevs/docs/erros/notfound"));

        return problemDetail;
    }

    @ExceptionHandler({RegraNegocioException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail conflict(final RegraNegocioException exception){

        final var exceptionMessage = exception.getMessage();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exceptionMessage);

        problemDetail.setTitle("Conflict");
        problemDetail.setType(URI.create("https://www.coderbank.com.br/fordevs/docs/erros/conflict"));

        return problemDetail;
    }
}
