package com.TaskManagement3.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TaskManagement3.Entity.BoardCard;

@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard, Long> {

    List<BoardCard> findByBoardIdAndColumnIdOrderByPosition(Long boardId, Long columnId);
    
    long countByBoardIdAndColumnId(Long boardId, Long columnId);
    
    Optional<BoardCard> findByIssueId(Long issueId);
}