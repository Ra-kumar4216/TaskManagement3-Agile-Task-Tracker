package com.TaskManagement3.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement3.Entity.Issue;
import com.TaskManagement3.Entity.Sprint;
import com.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement3.Enum.SprintState;
import com.TaskManagement3.Repository.IssueRepository;
import com.TaskManagement3.Repository.SprintRepository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class SprintService {

    @Autowired
    private IssueRepository issueRepo;
    
    @Autowired
    private SprintRepository sprintRepo;

    public Sprint createSprint(Sprint sprint) {
        sprint.setSprintState(SprintState.PLANNED);
        return sprintRepo.save(sprint);
    }
    
    

    @Transactional
    public Issue assignIssueToSprint(Long sprintId, Long issueId) {
        Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint Not Found"));
        
        Issue issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("issue Not Found"));
        
   
        if (sprint.getSprintState() ==SprintState.COMPLETED) {
        	throw new RuntimeException("can not add tasks to completed sprint");
        }
        
        issue.setSprintId(sprintId);
        
        return issueRepo.save(issue);
    }
    
    
    
    @Transactional
    public Sprint startSprint(Long sprintId) {
    	 Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint Not Found"));
         
    	 if(sprint.getSprintState() != SprintState.PLANNED) {
    		 throw new RuntimeException("Sprint can not be started");
    		 
    	 }
    	 
    	 sprint.setSprintState(SprintState.ACTIVE);
    	 
    	 if(sprint.getStartDate()==null) {
    		 sprint.setStartDate(LocalDateTime.now());
    	 }
    	 
    	 return sprintRepo.save(sprint);
    }
    
    @Transactional
    public Sprint endSprint(Long sprintId) {
    	
    	Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint Not Found"));
    	
    	sprint.setSprintState(SprintState.COMPLETED);
    	
    	if (sprint.getEndDate() == null) {
    		sprint.setStartDate(LocalDateTime.now());
    	}
    	
    	List<Issue>issues= issueRepo.findBySprintId(sprintId);
    	
    	for(Issue i: issues) {
    		if(!i.getIssueStatus().name().equals(IssueStatus.DONE)) {
    			i.setSprintId(null);
    			issueRepo.save(i);
    		}
    	}
    	
    	return sprintRepo.save(sprint);
    }
    
    public Map<String, Object> getBurnDownData(Long sprintId){
    	
    	Sprint sprint= sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("sprint not found"));
    	
    	LocalDateTime start= sprint.getStartDate();
    	
    	LocalDateTime end= sprint.getEndDate() !=null? sprint.getEndDate():LocalDateTime.now();
    	
    	List<Issue> issues= issueRepo.findBySprintId(sprintId);
    	
    	int totalTask= issues.size();
    	
    	Map<String,Integer>chart=new LinkedHashMap<>();
    	
    	LocalDateTime curser= start;
    	
    	while(!curser.isAfter(end)) {
    		
    		int completed= (int)issues.stream().filter(i->i.getIssueStatus().equals(IssueStatus.DONE)).count();
    		
    		int remaining=totalTask-completed;
    		
    		chart.put(curser.toString(),remaining );
    		curser=curser.plusDays(1);
    	}
    	Map<String,Object>response= new HashMap<>();
    	
    	response.put("sprintId", sprintId);
    	response.put("startDate", start);
    	response.put("endDate", end);
    	response.put("burnDown", chart);
    	
    	return response;
    }
    	
    }
        
       