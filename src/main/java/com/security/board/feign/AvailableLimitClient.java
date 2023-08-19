package com.security.board.feign;

import com.security.board.feign.decoder.LimitNotAvailableDecoder;
import com.security.board.model.request.CreateLimitRequest;
import com.security.board.model.request.RemoveOfferRequest;
import com.security.board.model.request.UpdateLimitRequest;
import com.security.board.model.request.UpdateOfferRequest;
import com.security.board.model.response.MessageResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "AvailableLimitClient",
        url = "${api.servicos.limit.url}",
        configuration = LimitNotAvailableDecoder.class)
public interface AvailableLimitClient {

    @PutMapping(value = "/credit/update-limit", consumes = "application/vnd.picpay.api.v1+json", produces = "application/vnd.picpay.api.v1+json")
    @ResponseBody
    MessageResponse updateLimit(UpdateLimitRequest updateLimitRequest);

    @PutMapping(value = "/credit/update-offer")
    @ResponseBody
    MessageResponse updateOffer(UpdateOfferRequest updateOfferRequest);

    @DeleteMapping(value = "/credit/sublimit-offer", consumes = "application/vnd.picpay.api.v1+json", produces = "application/vnd.picpay.api.v1+json")
    void removeSubLimitOffer(@RequestBody RemoveOfferRequest request);

    @PostMapping(value = "/credit/sublimit-offer")
    MessageResponse createOffer(@RequestBody CreateLimitRequest request);
}