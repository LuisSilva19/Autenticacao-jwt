package com.security.board.feign;


import com.security.board.feign.decoder.LimitNotAvailableDecoder;
import com.security.board.model.response.AvailableLimitResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "MiddlewareLimitClient",
        url = "${picpay.servicos.middleware.url}",
        configuration = LimitNotAvailableDecoder.class)
public interface MiddlewareLimitClient {

    @GetMapping(value = "/credit/customer-view")
    @ResponseBody
    AvailableLimitResponse getAvailableLimit(@RequestHeader String document);
}