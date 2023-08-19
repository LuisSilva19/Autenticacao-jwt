package com.security.board.service;

import com.security.board.configuration.security.JwtService;
import com.security.board.exception.CustomException;
import com.security.board.feign.AvailableLimitClient;
import com.security.board.feign.MiddlewareLimitClient;
import com.security.board.feign.PersonClient;
import com.security.board.model.request.CreateLimitRequest;
import com.security.board.model.request.RemoveOfferRequest;
import com.security.board.model.request.UpdateLimitRequest;
import com.security.board.model.request.UpdateOfferRequest;
import com.security.board.model.response.AvailableLimitResponse;
import com.security.board.model.response.MessageResponse;
import com.security.board.model.response.PersonResponse;
import feign.FeignException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditBoardServiceTest {

    @InjectMocks
    CreditBoardService creditBoardService;
    @Mock
    MiddlewareLimitClient middlewareLimitClient;
    @Mock
    PersonClient personFeingClient;
    @Mock
    AvailableLimitClient availableLimitClient;
    @Mock
    CreationHistoryService creationHistoryService;

    @Mock
    JwtService jwt;

    @Test
    void should_get_limit_by_document(){
        AvailableLimitResponse availableLimitResponse = new EasyRandom().nextObject(AvailableLimitResponse.class);
        availableLimitResponse.setDocument("12345678910");
        availableLimitResponse.getSubLimits().forEach(sub -> {
            sub.setSubLimitModelId(3);

            sub.getProducts().forEach(product -> {
                product.setProductId("1001010004");
                product.setSubProductId("00001");
            });
        });

        when(middlewareLimitClient.getAvailableLimit(Mockito.anyString())).thenReturn(availableLimitResponse);
        when(personFeingClient.getPersonInfo(Mockito.anyString())).thenReturn(new PersonResponse());

       var response = creditBoardService.getLimitByDocumentNumber("12345678910");

       Assertions.assertEquals("12345678910", response.getDocument());
    }

    @Test
    void should_get_limit_by_document2(){
        AvailableLimitResponse availableLimitResponse = new EasyRandom().nextObject(AvailableLimitResponse.class);
        availableLimitResponse.setDocument("12345678910");
        availableLimitResponse.getSubLimits().forEach(sub -> {
            sub.setSubLimitModelId(1000);

            sub.getProducts().forEach(product -> {
                product.setProductId("1001010004");
                product.setSubProductId("00001");
            });
        });

        when(middlewareLimitClient.getAvailableLimit(Mockito.anyString())).thenReturn(availableLimitResponse);
        when(personFeingClient.getPersonInfo(Mockito.anyString())).thenReturn(new PersonResponse());

        var response = creditBoardService.getLimitByDocumentNumber("12345678910");

        Assertions.assertEquals("12345678910", response.getDocument());
    }

    @Test
    void should_get_limit_by_document_no_name(){
        AvailableLimitResponse availableLimitResponse = new EasyRandom().nextObject(AvailableLimitResponse.class);
        availableLimitResponse.setDocument("12345678910");

        when(middlewareLimitClient.getAvailableLimit(Mockito.anyString())).thenReturn(availableLimitResponse);
        when(personFeingClient.getPersonInfo(Mockito.anyString())).thenThrow(FeignException.FeignClientException.class);

        var response = creditBoardService.getLimitByDocumentNumber("12345678910");

        Assertions.assertEquals("12345678910", response.getDocument());
    }

    @Test
    void should_update_limit(){
        when(availableLimitClient.updateLimit(any())).thenReturn(new MessageResponse("teste"));
        when(jwt.extractEmailFromToken(any())).thenReturn("test@test.com");
        var response = creditBoardService.updateLimit(new EasyRandom().nextObject(UpdateLimitRequest.class), "123456789");

        Assertions.assertEquals("teste", response.getMessage());
    }

    @Test
    void should_update_offer(){
        when(availableLimitClient.updateOffer(any())).thenReturn(new MessageResponse("teste"));
        when(jwt.extractEmailFromToken(any())).thenReturn("test@test.com");

        var response = creditBoardService.updateOffer(new EasyRandom().nextObject(UpdateOfferRequest.class), "123456789");

        Assertions.assertEquals("teste", response.getMessage());
    }

    @Test
    void should_create_offer(){
        CreateLimitRequest createLimitRequest = new EasyRandom().nextObject(CreateLimitRequest.class);
        createLimitRequest.setProductId("3004020007");
        createLimitRequest.setSubProductId("00001");

        when(availableLimitClient.createOffer(any())).thenReturn(new MessageResponse("teste"));
        when(jwt.extractEmailFromToken(any())).thenReturn("test@test.com");

        var response = creditBoardService.createOffer(createLimitRequest,"123456789");

        Assertions.assertEquals("teste", response.getMessage());
    }

    @Test
    void should_create_offer2(){
        CreateLimitRequest createLimitRequest = new EasyRandom().nextObject(CreateLimitRequest.class);
        createLimitRequest.setProductId("3004020008");
        createLimitRequest.setSubProductId("00001");

        Assertions.assertThrows(CustomException.class, () -> creditBoardService.createOffer(createLimitRequest,"123456789"));
    }

    @Test
    void should_remove_offer(){
        when(jwt.extractEmailFromToken(any())).thenReturn("test@test.com");
        Assertions.assertDoesNotThrow(() -> creditBoardService.removeOffer(new EasyRandom().nextObject(RemoveOfferRequest.class), "123456789"));
    }

}
