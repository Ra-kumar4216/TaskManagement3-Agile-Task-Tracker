package com.TaskManagement3.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement3.Entity.Issue;
import com.TaskManagement3.Entity.Sprint;
import com.TaskManagement3.Enum.IssueType;
import com.TaskManagement3.Enum.SprintState;
import com.TaskManagement3.Repository.IssueRepository;
import com.TaskManagement3.Repository.SprintRepository;

import jakarta.transaction.Transactional;

@Service
public class BackLogService {

	@Autowired
	private IssueRepository issueRepo; 
	
	@Autowired
	private SprintRepository sprintRepo;
	
	public List<Issue> getBackLog(Long projectId){
		List<Issue> issues= issueRepo.findByProjectIdAndSprintIdNullOrderByBackLogPosition(projectId);
		return issues;
	}
	
	@Transactional
	public void recordBackLog(Long projectId,List<Long> orderedIssueIds) {
		int pos=0;
		for(Long issueId:orderedIssueIds) {
			Issue issue= issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("issue not found"));
			
			issue.setBackLogPosition(pos++);
			issueRepo.save(issue);
		}
	}
	
	@Transactional
	public Issue addIssueToSprint(Long issueId,Long sprintId) {
		
		Issue issue= issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("issue not found"));
		Sprint sprint=sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("sprint not found"));
		
		if(!sprint.getSprintState().equals(SprintState.PLANNED)&& !sprint.getSprintState().equals(SprintState.ACTIVE)){
			throw new RuntimeException("can not add issue to sprint");
		}
		
		issue.setSprintId(sprintId);
		issue.setBackLogPosition(null);
		
		issueRepo.save(issue);
		
		return issue;
	}
	
	@Transactional
	public Map<String,Object>getBackLogHierArchy(Long projectId){
		
		List<Issue>backLog= getBackLog(projectId);
		Map<Long,Map<String,Object>> epicMap= new LinkedHashMap<>();
		Map<Long,Issue>storyMap= new HashMap<>();
		
		
		
		
		for(Issue issue:backLog) {
			if(issue.getIssueType()==IssueType.EPIC) {
				Map<String,Object> epicData=new LinkedHashMap<>();
				epicData.put("epics", issue);
				epicData.put("stories", new ArrayList<Issue>());
				epicData.put("subTasks", new HashMap<String,List<Issue>>());
				
				epicMap.put(issue.getId(), epicData);
			}
			
			if(issue.getIssueType() == IssueType.STORY) {
				storyMap.put(issue.getId(), issue);
			}
		}
		
		for(Issue issue:backLog) {
			
			if(issue.getIssueType() == IssueType.SUBTASK) {
				Long parentIssueId= issue.getPareentIssueId();
				
				if(parentIssueId !=null &&storyMap.containsKey(parentIssueId)){
					
					for (Map<String,Object>epicData:epicMap.values()) {
						
						List<Issue>stories= (List<Issue>)epicData.get("stories");
						
						boolean belongToEpic=stories.stream().anyMatch(s-> s.getId().equals(parentIssueId));
						
						if(belongToEpic) {
							Map<Long,List<Issue>>subMap= (Map<Long,List<Issue>>)epicData.get("Subtasks");
							
							subMap.computeIfAbsent(parentIssueId, k-> new ArrayList<>()).add(issue);
							
							break;
							
						}
					}
				}
			}
		}
		
		return Collections.singletonMap("epic",epicMap);
	}
}