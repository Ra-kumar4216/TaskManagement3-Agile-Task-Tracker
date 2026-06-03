package com.TaskManagement3.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="work_flows")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFlow {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	@Column(length=2000)
	private String descriptions;
	
	private LocalDateTime createdAt= LocalDateTime.now();
	
	@OneToMany(mappedBy="workflow",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<WorkFlowTransaction>transaction= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<WorkFlowTransaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<WorkFlowTransaction> transaction) {
		this.transaction = transaction;
	}

	public List<WorkFlow> getTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object setWorkFlow(WorkFlow wf) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	

	
	}
