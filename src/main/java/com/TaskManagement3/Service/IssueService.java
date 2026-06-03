package com.TaskManagement3.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagement3.Entity.Issue;
import com.TaskManagement3.Entity.IssueComment;
import com.TaskManagement3.Entity.Sprint;
import com.TaskManagement3.Enum.IssuePriority;
import com.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement3.Enum.IssueType;
import com.TaskManagement3.Enum.SprintState;
import com.TaskManagement3.Repository.IssueCommentRepository;
import com.TaskManagement3.Repository.IssueRepository;
import com.TaskManagement3.Entity.Label;
import com.TaskManagement3.Repository.LabelRepository;
import com.TaskManagement3.Repository.SprintRepository;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private IssueCommentRepository issueCommentRepo;

    @Autowired
    private LabelRepository labelRepo;

    @Autowired
    private SprintRepository sprintRepo;

    // Helper method for unique key
    private String generateKey(Long id) {
        return "PROJECT-" + id;
    }

    @Transactional
    public Issue createIssue(Issue issue, Set<String> labelName) {
        // Setting default values
        issue.setIssueType(issue.getIssueType() != null ? issue.getIssueType() : IssueType.TASK);
        issue.setPriority(issue.getPriority() != null ? issue.getPriority() : IssuePriority.MEDIUM);
        issue.setIssueStatus(IssueStatus.OPEN);
        
        if (labelName != null) {
            for (String name : labelName) {
                Optional<Label> maybeLabel = labelRepo.findByName(name);
                Label label = maybeLabel.orElseThrow(() -> new RuntimeException("label not found"));
                issue.getLabels().add(label);
            }
        }
        
        // Double save logic to generate and set the Issue Key
        Issue saved = issueRepo.save(issue);
        saved.setIssueKey(generateKey(saved.getId()));
        return issueRepo.save(saved);
    }

    public Issue getIssueById(Long id) {
        return issueRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("issue not found"));
    }

    public List<Issue> getIssueByAssigneeEmail(String assigneeEmail){
        return issueRepo.findByAssigneeEmail(assigneeEmail);
    }
    public List<Issue> getIssueBySprintId(Long sprintId){
    	return issueRepo.findBySprintId(sprintId);
    }
    public List<Issue>getIssueByStatus(IssueStatus status){
    	return issueRepo.findByIssueStatus(status);
    }
    
    @Transactional
    public IssueComment addComments(Long issueId,String authorEmail,String body) {
        
        Issue issue = getIssueById(issueId);
        
        IssueComment comments = new IssueComment();
        comments.setIssueId(issue.getId());
        comments.setAuthoeEmail(authorEmail);
        comments.setBody(body);
        
        return issueCommentRepo.save(comments);
    }
    
    
    @Transactional
    public Issue updateIssueStatus(Long id,IssueStatus issueStatus) {
        
        Issue issue = getIssueById(id);
        
        if(issueStatus == null) {
            throw new RuntimeException("Status can not be null");
        }
        
        issue.setIssueStatus(issueStatus);
        
        issue.setUpdateAt(java.time.LocalDateTime.now());
        
        return issueRepo.save(issue);
    }
    
    @Transactional
    public Sprint creatSprint(Sprint sprint) {
    	if(sprint.getSprintState()==null) {
    		sprint.setSprintState(SprintState.PLANNED);
    	}
    	
    	return sprintRepo.save(sprint);
    }
    
    
    public List<Issue>search(Map<String,String>filter){
    	
    	List<Issue>issue=issueRepo.findAll();
    	
    	if(filter.containsKey("assigneeEmail")) {
    		String email=filter.get("assigneeEmail");
    		
    		issue=issue.stream().filter(i-> email.equalsIgnoreCase(i.getAssigneeEmail())).collect(Collectors.toList());
    		
    	}
    	
    	if(filter.containsKey("sprint")) {
    		Long sprintId=Long.valueOf(filter.get("sprint"));
    		issue=issue.stream().filter(i->sprintId.equals(i.getSprintId())).collect(Collectors.toList());
    	}
    	String statusStr= filter.get("status");
    	
    	if(statusStr!=null && !statusStr.trim().isEmpty())   {
    		try {
    			IssueStatus issueStatus= IssueStatus.valueOf(statusStr.trim().toUpperCase());
    			
    			issue= issue.stream().filter(i->i.getIssueStatus()==issueStatus).collect(Collectors.toList());
    		} catch (Exception e) {
    			throw new RuntimeException("invalid status filter");
   
    		}
    	}
    	return issue;
    }

}