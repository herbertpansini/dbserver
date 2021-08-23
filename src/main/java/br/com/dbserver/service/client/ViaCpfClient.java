package br.com.dbserver.service.client;

import br.com.dbserver.model.ViaCpf;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ViaCpfClient", url = "https://user-info.herokuapp.com/users")
public interface ViaCpfClient {

    @GetMapping("{cpf}")
    public ViaCpf consultarCpf(@PathVariable String cpf);
}
