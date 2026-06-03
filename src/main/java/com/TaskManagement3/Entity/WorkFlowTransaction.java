package com.TaskManagement3.Entity;

import java.util.HashSet;
import java.util.Set;

import com.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement3.Enum.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="worhflow_transactions",indexes= {@Index(name="idx_wf_from_to",columnList="workFlow_id,from,to")})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFlowTransaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false)
	private IssueStatus from;
	
	@Column(nullable=false)
	private IssueStatus to;
	
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="workFlow_id",nullable=false)
	private WorkFlow workFlow;
	
	@Column(name="role")
	private Set<Role>allowedRole= new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IssueStatus getFrom() {
		return from;
	}

	public void setFrom(IssueStatus from) {
		this.from = from;
	}

	public IssueStatus getTo() {
		return to;
	}

	public void setTo(IssueStatus to) {
		this.to = to;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public Set<Role> getAllowedRole() {
		return allowedRole;
	}

	public void setAllowedRole(Set<Role> allowedRole) {
		this.allowedRole = allowedRole;
	}
	
	
	
}