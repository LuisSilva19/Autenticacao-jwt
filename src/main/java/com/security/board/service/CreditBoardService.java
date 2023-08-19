package com.security.board.service;

import com.security.board.configuration.security.JwtService;
import com.security.board.exception.CustomException;
import com.security.board.feign.AvailableLimitClient;
import com.security.board.feign.MiddlewareLimitClient;
import com.security.board.feign.PersonClient;
import com.security.board.model.enun.SubLimitsCreate;
import com.security.board.model.enun.SubLimitId;
import com.security.board.model.request.CreateLimitRequest;
import com.security.board.model.request.CreationHistoryRequest;
import com.security.board.model.request.RemoveOfferRequest;
import com.security.board.model.request.UpdateLimitRequest;
import com.security.board.model.request.UpdateOfferRequest;
import com.security.board.model.response.AvailableLimitResponse;
import com.security.board.model.response.MessageResponse;
import com.security.board.model.response.PersonResponse;
import com.security.board.model.response.ProductResponse;
import feign.FeignException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.security.board.exception.ExceptionCodes.SUBLIMIT_NOT_SHOULD_CREATE_OFFER;

@Service
@AllArgsConstructor
public class CreditBoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditBoardService.class);
    private final MiddlewareLimitClient middlewareLimitClient;
    private final PersonClient personFeingClient;
    private final AvailableLimitClient availableLimitClient;
    private final CreationHistoryService creationHistoryService;
    private final JwtService jwtService;

    public AvailableLimitResponse getLimitByDocumentNumber(String documentNumber){
        LOGGER.info("Iniciando metodo CreditBoardService.getLimitByDocumentNumber, entrada: {}", documentNumber);

        AvailableLimitResponse availableLimitResponse = middlewareLimitClient.getAvailableLimit(documentNumber);
        availableLimitResponse.setCustomerName(getCustomerName(documentNumber).getName());

        availableLimitResponse = getSourceCreditLimitBySublimitId(availableLimitResponse);

        LOGGER.info("Finalizando metodo CreditBoardService.getLimitByDocumentNumber, retorno: {}", availableLimitResponse);

        return availableLimitResponse;
    }

    private AvailableLimitResponse getSourceCreditLimitBySublimitId(AvailableLimitResponse availableLimitResponse) {
        availableLimitResponse.getSubLimits().forEach(subLimit -> {
            Optional<SubLimitId> optionalSubLimits = Arrays.stream(SubLimitId.values())
                    .filter(p -> p.getSublimitModel().equals(subLimit.getSubLimitModelId())).findFirst();

            if(optionalSubLimits.isPresent()){
                Optional<ProductResponse> optionalProduct = subLimit.getProducts().stream()
                        .filter(product -> product.getProductId().equals(optionalSubLimits.get().getProduct())
                                && product.getSubProductId().equals(optionalSubLimits.get().getSubProduct())).findFirst();

                subLimit.setSourceCreditLimit(optionalProduct.get().getSourceCreditLimit());
            }
        });

        return availableLimitResponse;
    }

    private PersonResponse getCustomerName(String documentNumber) {
        PersonResponse personResponse = new PersonResponse("");

        try {
            personResponse = personFeingClient.getPersonInfo(documentNumber);

            LOGGER.info("Informações pessoais, personReponse {}", personResponse);

        } catch (FeignException.FeignClientException ex) {
            LOGGER.error("CustomerName não encontrado na api Person");
        }

        return personResponse;
    }

    public MessageResponse updateLimit(UpdateLimitRequest updateLimitRequest, String bearerToken){
        LOGGER.info("Iniciando metodo CreditBoardService.updateLimit, entrada: {}", updateLimitRequest);

        var response = availableLimitClient.updateLimit(updateLimitRequest);

       var historic = CreationHistoryRequest.builder()
                .approvedAmount(BigDecimal.valueOf(updateLimitRequest.getApprovedAmount()))
                .username(getUserName(bearerToken))
                .reason(updateLimitRequest.getResultReason())
                .product(updateLimitRequest.getProductId())
                .updateDate(LocalDateTime.now())
                .documentNumber(updateLimitRequest.getDocument())
                .build();

        creationHistoryService.createCreationHistory(historic);

        LOGGER.info("Finalizando metodo CreditBoardService.updateLimit, saida: {}", response);

        return response;
    }

    public MessageResponse updateOffer(UpdateOfferRequest updateOfferRequest, String bearerToken){
        LOGGER.info("Iniciando metodo CreditBoardService.updateOffer, entrada: {}", updateOfferRequest);

        var response = availableLimitClient.updateOffer(updateOfferRequest);

        var historic = CreationHistoryRequest.builder()
                .approvedAmount(BigDecimal.valueOf(updateOfferRequest.getApprovedAmount()))
                .username(getUserName(bearerToken))
                .reason(updateOfferRequest.getResultReason())
                .product(updateOfferRequest.getProductId())
                .updateDate(LocalDateTime.now())
                .documentNumber(updateOfferRequest.getDocument())
                .build();

        creationHistoryService.createCreationHistory(historic);

        LOGGER.info("Finalizando metodo CreditBoardService.updateOffer, saida: {}", response);

        return response;
    }

    public void removeOffer(RemoveOfferRequest removeOfferRequest, String bearerToken){
        LOGGER.info("Iniciando metodo CreditBoardService.removeOffer, entrada: {}", removeOfferRequest);

        availableLimitClient.removeSubLimitOffer(removeOfferRequest);

        var historic = CreationHistoryRequest.builder()
                .approvedAmount(BigDecimal.ZERO)
                .username(getUserName(bearerToken))
                .reason(removeOfferRequest.getDenialReason())
                .product(removeOfferRequest.getProductId())
                .updateDate(LocalDateTime.now())
                .documentNumber(removeOfferRequest.getDocument())
                .build();

        creationHistoryService.createCreationHistory(historic);

        LOGGER.info("Finalizando metodo CreditBoardService.removeOffer");
    }

    public MessageResponse createOffer(CreateLimitRequest createLimitRequest, String bearerToken){
        LOGGER.info("Iniciando metodo CreditBoardService.createOffer, entrada: {}", createLimitRequest);

        validateElegibleCreateOffer(createLimitRequest);

        var response = availableLimitClient.createOffer(createLimitRequest);

        var historic = CreationHistoryRequest.builder()
                .approvedAmount(BigDecimal.valueOf(createLimitRequest.getAvailableAmount()))
                .username(getUserName(bearerToken))
                .product(createLimitRequest.getProductId())
                .updateDate(LocalDateTime.now())
                .documentNumber(createLimitRequest.getDocument())
                .reason("Criação de Limite")
                .build();

        creationHistoryService.createCreationHistory(historic);

        LOGGER.info("Finalizando metodo CreditBoardService.createOffer, saida: {}", response);

        return response;
    }

    private static void validateElegibleCreateOffer(CreateLimitRequest createLimitRequest) {
        List<Boolean> listBoolean = new ArrayList<>();
        Arrays.stream(SubLimitsCreate.values())
                .forEach(p -> {
                    listBoolean.add( (p.getSubProduct().equals(createLimitRequest.getSubProductId())
                            && p.getProduct().equals(createLimitRequest.getProductId())) );
                });

        if(listBoolean.stream().noneMatch(Boolean::booleanValue))
            throw new CustomException(SUBLIMIT_NOT_SHOULD_CREATE_OFFER);
    }

    private String getUserName(String bearerToken) {
        var token = bearerToken.substring(7);
        var email = jwtService.extractEmailFromToken(token);
        return email.split("@")[0];
    }
}
