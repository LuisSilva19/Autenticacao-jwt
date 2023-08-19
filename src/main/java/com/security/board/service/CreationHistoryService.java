package com.security.board.service;

import com.security.board.exception.CustomException;
import com.security.board.exception.ExceptionCodes;
import com.security.board.mapper.CreationHistoryMapper;
import com.security.board.model.CreationHistory;
import com.security.board.model.request.CreationHistoryRequest;
import com.security.board.model.response.CreationHistoryResponse;
import com.security.board.repository.CreationHistoryRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreationHistoryService{

    private CreationHistoryRepository creationHistoryRepository;
    private CreationHistoryMapper creationHistoryMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreationHistoryService.class);
    private static final String PATTERN_DATE = "yyyy-MM-dd";

    public CreationHistoryResponse findById(Long id) {
        LOGGER.info("Iniciando metodo CreationHistoryService.findById, id: {}", id);

        var find = creationHistoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCodes.CREATION_HISTORY_NOT_FOUND));

        var creationHistoryResponse = creationHistoryMapper.toCreationHistoryResponse(find);
        LOGGER.info("Finalizando metodo CreationHistoryService.findById, saida: {}", creationHistoryResponse);

        return creationHistoryResponse;
    }

    public List<CreationHistoryResponse> findByDocumentNumber(String documentNumber) {
        LOGGER.info("Iniciando metodo CreationHistoryService.findByDocumentNumber, documentNumber: {}", documentNumber);

        var historyList = creationHistoryRepository.findByDocumentNumber(documentNumber);
        if (historyList.isEmpty()) {
            throw new CustomException(ExceptionCodes.NO_IDENTIFICATION_NUMBER_FOUND);
        }

        var listCreationHistoryResponse = historyList.stream()
                .map(creationHistoryMapper::toCreationHistoryResponse)
                .toList();
        LOGGER.info("Finalizando metodo CreationHistoryService.findByDocumentNumber, saida: {}", listCreationHistoryResponse);

        return listCreationHistoryResponse;
    }

    public List<CreationHistoryResponse> findAll() {
        LOGGER.info("Iniciando metodo CreationHistoryService.findAll");

        var historyList = creationHistoryRepository.findAll();
        if (historyList.isEmpty()) {
            throw new CustomException(ExceptionCodes.CREATION_HISTORY_NOT_FOUND);
        }

        var listCreationHistoryResponse = historyList.stream()
                .map(creationHistoryMapper::toCreationHistoryResponse)
                .toList();

        LOGGER.info("Finalizando metodo CreationHistoryService.findAll, saida: {}", listCreationHistoryResponse);

        return listCreationHistoryResponse;
    }

    public CreationHistoryResponse createCreationHistory(CreationHistoryRequest creationHistoryRequest) {
        LOGGER.info("Iniciando metodo CreationHistoryService.createCreationHistory, entrada: {}", creationHistoryRequest);

        var creationHistory = creationHistoryMapper.toCreationHistory(creationHistoryRequest);
        CreationHistory save = creationHistoryRepository.save(creationHistory);

        CreationHistoryResponse creationHistoryResponse = creationHistoryMapper.toCreationHistoryResponse(save);

        LOGGER.info("Finalizando metodo CreationHistoryService.createCreationHistory, saida: {}", creationHistoryResponse);

        return creationHistoryResponse;
    }

    public List<CreationHistoryResponse> findByDate(String updateDate) {
        LOGGER.info("Iniciando metodo CreationHistoryService.findByDate, updateDate: {}", updateDate);

        var date = LocalDate.parse(updateDate, DateTimeFormatter.ofPattern(PATTERN_DATE));
        var startOfDay = date.atStartOfDay();
        var endOfDay = date.plusDays(1).atStartOfDay().minusSeconds(1);

        var historyList = creationHistoryRepository.findByUpdateDateBetween(startOfDay, endOfDay);
        if (historyList.isEmpty()) {
            throw new CustomException(ExceptionCodes.CREATION_HISTORY_NOT_FOUND_TODAY);
        }

        var listCreationHistoryResponse = historyList.stream()
                .map(creationHistoryMapper::toCreationHistoryResponse)
                .toList();

        LOGGER.info("Finalizando metodo CreationHistoryService.findByDate, saida: {}", listCreationHistoryResponse);

        return listCreationHistoryResponse;
    }

    public List<CreationHistoryResponse> findByRangeDate(String startDate, String endDate) {
        LOGGER.info("Iniciando metodo CreationHistoryService.findByDate, updateDate:");

        var startDateFormated = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(PATTERN_DATE)).atStartOfDay();
        var endDateFormated = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(PATTERN_DATE))
                .plusDays(1).atStartOfDay().minusSeconds(1);

        var historyList = creationHistoryRepository.findByUpdateDateBetween(startDateFormated, endDateFormated);
        if (historyList.isEmpty()) {
            throw new CustomException(ExceptionCodes.CREATION_HISTORY_NOT_FOUND_TODAY);
        }

        var listCreationHistoryResponse = historyList.stream()
                .map(creationHistoryMapper::toCreationHistoryResponse)
                .toList();

        LOGGER.info("Finalizando metodo CreationHistoryService.findByDate, saida: {}", listCreationHistoryResponse);

        return listCreationHistoryResponse;
    }

}
