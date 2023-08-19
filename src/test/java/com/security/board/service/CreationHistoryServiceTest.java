package com.security.board.service;

import com.security.board.exception.CustomException;
import com.security.board.mapper.CreationHistoryMapper;
import com.security.board.model.CreationHistory;
import com.security.board.model.request.CreationHistoryRequest;
import com.security.board.repository.CreationHistoryRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreationHistoryServiceTest {

    @InjectMocks
    CreationHistoryService creationHistoryService;
    @Mock
    CreationHistoryRepository creationHistoryRepository;
    @Mock
    CreationHistoryMapper creationHistoryMapper;

    @Test
    void should_find_by_id(){
        Mockito.when(creationHistoryRepository.findById(1L)).thenReturn(Optional.of(
                new CreationHistory(1L,"12345678911", LocalDateTime.now(),"", BigDecimal.ZERO,"","")));

        Assertions.assertDoesNotThrow(() -> creationHistoryService.findById(1L));
    }

    @Test
    void should_not_find_by_id(){
        Mockito.when(creationHistoryRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomException.class, () ->  creationHistoryService.findById(1L));
    }

    @Test
    void should_find_by_documentNumber(){
        Mockito.when(creationHistoryRepository.findByDocumentNumber(Mockito.anyString())).thenReturn(List.of(new CreationHistory()));

        Assertions.assertDoesNotThrow(() -> creationHistoryService.findByDocumentNumber("12345678910"));
    }

    @Test
    void should_not_find_by_documentNumber(){
        Mockito.when(creationHistoryRepository.findByDocumentNumber(Mockito.anyString())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(CustomException.class ,() -> creationHistoryService.findByDocumentNumber("12345678910"));
    }

    @Test
    void should_find_all(){
        Mockito.when(creationHistoryRepository.findAll()).thenReturn(List.of(new CreationHistory()));

        Assertions.assertDoesNotThrow(() -> creationHistoryService.findAll());
    }

    @Test
    void should_not_find_all(){
        Mockito.when(creationHistoryRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(CustomException.class ,() -> creationHistoryService.findAll());
    }

    @Test
    void should_find_by_date(){
        Mockito.when(creationHistoryRepository.findByUpdateDateBetween(Mockito.any(),Mockito.any())).thenReturn(List.of(new CreationHistory()));

        Assertions.assertDoesNotThrow(() -> creationHistoryService.findByDate("2023-08-11"));
    }

    @Test
    void should_not_find_by_date(){
        Mockito.when(creationHistoryRepository.findByUpdateDateBetween(Mockito.any(),Mockito.any())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(CustomException.class ,() -> creationHistoryService.findByDate("2023-08-11"));
    }

    @Test
    void should_find_by_range_date(){
        Mockito.when(creationHistoryRepository.findByUpdateDateBetween(Mockito.any(),Mockito.any())).thenReturn(List.of(new CreationHistory()));

        Assertions.assertDoesNotThrow(() -> creationHistoryService.findByRangeDate("2023-08-11", "2023-08-12"));
    }

    @Test
    void should_not_find_by_range_date(){
        Mockito.when(creationHistoryRepository.findByUpdateDateBetween(Mockito.any(),Mockito.any())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(CustomException.class ,() -> creationHistoryService.findByRangeDate("2023-08-11", "2023-08-12"));
    }

    @Test
    void should_create_historic(){
        Assertions.assertDoesNotThrow(() -> creationHistoryService.createCreationHistory(new CreationHistoryRequest()));
    }

}
