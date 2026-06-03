package com.TaskManagement3.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="issue",indexes= {@Index(name="idx_issue_key",columnList="issueKey"),
		                      @Index(name="idx_assignee_email",columnList="assigneeEmail")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueComment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="issue_id",nullable=false)
    private Long issueId;
    
    private String authoeEmail;
    
    @Column(length=5000)
    private String body;
    
    private LocalDateTime creadAt= LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getAuthoeEmail() {
		return authoeEmail;
	}

	public void setAuthoeEmail(String authoeEmail) {
		this.authoeEmail = authoeEmail;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getCreadAt() {
		return creadAt;
	}

	public void setCreadAt(LocalDateTime creadAt) {
		this.creadAt = creadAt;
	}
    
    
    
    

}
