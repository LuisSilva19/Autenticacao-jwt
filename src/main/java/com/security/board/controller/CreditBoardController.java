package com.security.board.controller;

import com.security.board.model.request.CreateLimitRequest;
import com.security.board.model.request.RemoveOfferRequest;
import com.security.board.model.request.UpdateLimitRequest;
import com.security.board.model.request.UpdateOfferRequest;
import com.security.board.model.response.AvailableLimitResponse;
import com.security.board.model.response.MessageResponse;
import com.security.board.service.CreditBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/credit/board")
@Validated
@SecurityRequirement(name = "javainuseapi")
public class CreditBoardController {

    private final CreditBoardService creditBoardService;
    private static final String AUTHORIZATION = "Authorization";

    @GetMapping("/limit")
    @Operation(summary = "Busca de limite por cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Limite nao encontrado"),
            @ApiResponse(responseCode = "400", description = "CPF invalido"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<AvailableLimitResponse> getLimitByDocumentNumber(@CPF @RequestHeader("x-document-number") String documentNumber) {
        return ResponseEntity.ok(creditBoardService.getLimitByDocumentNumber(documentNumber));
    }

    @PutMapping("/update-limit")
    @Operation(summary = "Atualização de limite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "404", description = "Limite nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<MessageResponse> updateLimit(@RequestBody UpdateLimitRequest updateLimitRequest,
                                                       HttpServletRequest request) {
        return ResponseEntity.accepted().body(creditBoardService.updateLimit(updateLimitRequest,
                request.getHeader(AUTHORIZATION)));
    }

    @PutMapping("/update-offer")
    @Operation(summary = "Atualização de oferta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "404", description = "Oferta nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<MessageResponse> updateOffer(@RequestBody UpdateOfferRequest updateOfferRequest,
                                                       HttpServletRequest request) {
        return ResponseEntity.accepted().body(creditBoardService.updateOffer(updateOfferRequest,
                request.getHeader(AUTHORIZATION)));
    }

    @DeleteMapping("/sublimit-offer")
    @Operation(summary = "Exclusão de oferta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "404", description = "Oferta nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<MessageResponse> removeOffer(@RequestBody RemoveOfferRequest removeOfferRequest,
                                                       HttpServletRequest request) {
        creditBoardService.removeOffer(removeOfferRequest, request.getHeader(AUTHORIZATION));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/sublimit-offer")
    @Operation(summary = "Criação de oferta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<MessageResponse> createOffer(@RequestBody CreateLimitRequest createLimitRequest,
                                                       HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(creditBoardService.createOffer(createLimitRequest,
                request.getHeader(AUTHORIZATION)));
    }
}
