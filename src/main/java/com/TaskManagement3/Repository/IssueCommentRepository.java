package com.TaskManagement3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TaskManagement3.Entity.IssueComment;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment, Long>{
	List<IssueComment>findByIssueIdOrderdByAtAsc(Long issueId);
	
	
}