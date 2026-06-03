package com.TaskManagement3.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagement3.Entity.WorkFlow;
import com.TaskManagement3.Entity.WorkFlowTransaction;
import com.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement3.Enum.Role;
import com.TaskManagement3.Repository.WorkFlowRepository;
import com.TaskManagement3.Repository.WorkFlowTransactionRepository;


@Service
public class WorkFlowService {
	
	@Autowired
	private WorkFlowRepository workFllowRepo;
	
	@Autowired
	private WorkFlowTransactionRepository workFlowTransactionRepo;

	private WorkFlowTransaction[] transactions;
	
	@Transactional
	public WorkFlow createWorkFlow(WorkFlow wf) {
		
		if(wf.getTransaction() !=null) {
			wf.getTransaction().forEach(t-> t.setWorkFlow(wf));
		}
		return workFllowRepo.save(wf);
	}
	
	public List<WorkFlow>getAllwork(){
		return workFllowRepo.findAll();
	}
	
	public WorkFlow getWorkById(Long id) {
		return workFllowRepo.findById(id).orElseThrow(()-> new RuntimeException("WorkFlow not found "+id));
	}
	
	
	
	@Transactional
	public WorkFlow updateWorkFlow(Long id, WorkFlow updated) {
		
		WorkFlow wf= getWorkById(id);
		
		wf.setName(updated.getName());
		wf.setDescriptions(updated.getDescriptions());
		
		wf.getTransaction().clear();
		
		if(updated.getTransaction() !=null) {
			for(WorkFlowTransaction t:updated.getTransaction()) {
				t.setWorkFlow(wf);
				wf.getTransaction().add(t);
			}
		}
		
		return workFllowRepo.save(wf);
		
	}
	
	public void deletework(Long id) {
		workFllowRepo.deleteById(id);
	}
	
	
	public boolean isTransacionAllwed(Long workFlowId,IssueStatus from,IssueStatus to,Set<Role>userRole) {
		
		List<WorkFlowTransaction> transaction= workFlowTransactionRepo.findByWorkFlowIdAndFrom(workFlowId, from);
		
		for(WorkFlowTransaction t:transactions ) {
			
			if(!t.getTo().equals(t)) {
				continue;
			}
			
			if(t.getAllowedRole()==null || t.getAllowedRole().isEmpty()) {
				return true;
			}
			
			for(Role role:userRole) {
				if(t.getAllowedRole().contains(userRole)) {
					
					return true;
				}
				
			}
			return false;
		}
		return false;
		
	}
	

}
