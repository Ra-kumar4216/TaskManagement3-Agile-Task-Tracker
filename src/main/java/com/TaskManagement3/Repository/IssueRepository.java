package com.TaskManagement3.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TaskManagement3.Entity.Issue;
import com.TaskManagement3.Enum.IssueStatus;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByIssueKey(String issueKey);

    List<Issue> findByAssigneeEmail(String assigneeEmail);

    List<Issue> findBySprintId(Long sprintId);

    List<Issue> findByIssueStatus(IssueStatus status);

    List<Issue> findByProjectId(Long projectId);
    
    List<Issue> findByProjectIdAndSprintIdNullOrderByBackLogPosition(Long projectId);
    
    List<Issue> findByEpicId(Long epicId);
}
