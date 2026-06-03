package com.TaskManagement3.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TaskManagement3.Entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByProjectKey(String projectKey);
    
} 