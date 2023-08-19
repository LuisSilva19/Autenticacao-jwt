package com.security.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCodes {
    CREATION_HISTORY_NOT_FOUND(404, "creationHistoryNotFound", "Histórico de proposta não encontrado"),
    LIMIT_NOT_FOUND(404, "LimitNotFound", "Limite não encontrado"),
    INTERNAL_SERVER_ERROR(500, "InternalServerError", "Erro do Servidor Interno"),
    OPERATION_NOT_FOUND(404,"OperationNotFound","Operacao nao encontrada"),
    NO_IDENTIFICATION_NUMBER_FOUND(404, "noIdentificationNumberFound", "Documento nao encontrado na base de dados"),
    EXISTING_OFFER(400, "existingOffer", "Oferta já existente"),
    CREATION_HISTORY_NOT_FOUND_TODAY(404, "creationHistoryNotFoundToday", "Histórico de proposta não encontrado na data inserida"),
    EMAIL_OR_SENHA_INVALID(400, "EmailOrSenhaInvalid","Email ou senha inválido"),
    TOKEN_INVALID(400, "tokenInvalid", "Token invalido"),
    CPF_INVALIDO(400, "invalidDocumentNumber", "CPF invalido"),
    SUBLIMIT_NOT_SHOULD_CREATE_OFFER(400, "sublimit", "Este SubLimit não pode criar ofertas!");

    private final int status;
    private final String errorCode;
    private final String message;

}
