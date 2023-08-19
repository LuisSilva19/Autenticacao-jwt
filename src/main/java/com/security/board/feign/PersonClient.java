package com.security.board.feign;


import com.security.board.model.response.PersonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "PersonClient",
        url = "${picpay.servicos.person.url}")
public interface PersonClient {

    @GetMapping(value = "/person")
    @ResponseBody
    PersonResponse getPersonInfo(@RequestParam(name = "cpf") String document);
}