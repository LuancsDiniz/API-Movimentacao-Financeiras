package br.com.coderbank.apimovimentacaofinanceira.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "clienteClient", url = "${cliente.api.url}")
public interface ClienteClient {
}
