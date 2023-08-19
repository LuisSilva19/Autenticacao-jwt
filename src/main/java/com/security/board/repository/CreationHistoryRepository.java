package com.security.board.repository;

import com.security.board.model.CreationHistory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreationHistoryRepository extends JpaRepository<CreationHistory, Long> {
    List<CreationHistory> findByDocumentNumber(String documentNumber);
    List<CreationHistory> findByUpdateDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);


}
