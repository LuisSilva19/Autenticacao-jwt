package com.security.board.controller;

import com.security.board.model.request.CreationHistoryRequest;
import com.security.board.model.response.CreationHistoryResponse;
import com.security.board.service.CreationHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/credit/creationHistory")
@AllArgsConstructor
@Validated
@SecurityRequirement(name = "javainuseapi")
public class CreationHistoryController {
    private final CreationHistoryService creationHistoryService;

    @GetMapping("/{id}")
    @Operation(summary = "Busca histórico de proposta por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Proposta nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    @Parameter(name = "id", required = true, schema = @Schema(implementation = Long.class))
    public ResponseEntity<CreationHistoryResponse> findByIdCreationHistory(@PathVariable Long id) {
        return ResponseEntity.ok(creationHistoryService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Busca todo o histórico de proposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Proposta nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<List<CreationHistoryResponse>> findAllCreationHistory() {
        return ResponseEntity.ok(creationHistoryService.findAll());
    }


    @GetMapping("/by-identification")
    @Operation(summary = "Busca histórico de proposta por cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Proposta nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<List<CreationHistoryResponse>> findByDocumentNumber(
                @CPF @RequestHeader("x-document-number") String documentNumber) {
        return ResponseEntity.ok(creationHistoryService.findByDocumentNumber(documentNumber));
    }

    @GetMapping("/by-date")
    @Operation(summary = "Busca histórico de proposta por data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Proposta nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<List<CreationHistoryResponse>> findByDate(@RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(creationHistoryService.findByDate(startDate));
    }

    @GetMapping("/by-range-date")
    @Operation(summary = "Busca histórico de proposta por data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Proposta nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<List<CreationHistoryResponse>> findByRangeDate(@RequestParam("startDate") String startDate,
                                                                         @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(creationHistoryService.findByRangeDate(startDate,endDate));
    }

    @PostMapping
    @Operation(summary = "Insere histórico de proposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor")
    })
    public ResponseEntity<CreationHistoryResponse> createLogin(@Valid @RequestBody CreationHistoryRequest creationHistory) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creationHistoryService.createCreationHistory(creationHistory));
    }
}
