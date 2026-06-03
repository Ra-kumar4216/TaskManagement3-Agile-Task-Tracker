package com.TaskManagement3.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.List;
import java.util.Map;

import com.TaskManagement3.Entity.Issue;
import com.TaskManagement3.Entity.IssueComment;
import com.TaskManagement3.Entity.Sprint;
import com.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement3.Service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor

public class IssueController {

    private final IssueService issueService;

    @PostMapping("/create")
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue, 
                                           @RequestParam(required=false) Set<String> labels) {
    	Issue created= issueService.createIssue(issue, labels);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getIssueById(id));
    }

    @GetMapping("/assigneeEmail")
    public ResponseEntity<List<Issue>> getIssueByEmail(@RequestParam String assigneeEmail) {
        return ResponseEntity.ok(issueService.getIssueByAssigneeEmail(assigneeEmail));
    }

    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<Issue>> getIssueBySprintId(@PathVariable Long sprintId) {
        return ResponseEntity.ok(issueService.getIssueBySprintId(sprintId));
    }

    @GetMapping("/issueStatus)")
    public ResponseEntity<List<Issue>>getIssueByStatus(@RequestParam IssueStatus status){
    	return ResponseEntity.ok(issueService.getIssueByStatus(status));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable Long id, @RequestParam IssueStatus issueStatus) {
        return ResponseEntity.ok(issueService.updateIssueStatus(id, issueStatus));
    }

   
    @PostMapping("/comment/{issueId}")
    public ResponseEntity<IssueComment>addComment(@PathVariable Long issueId, 
                                                @RequestParam String authoremail, 
                                                @RequestBody String body) {
        return ResponseEntity.ok(issueService.addComments(issueId, authoremail, body));
    }

   
    @PostMapping("/sprint")
    public ResponseEntity<Sprint>createSprint(@RequestBody Sprint sprint) {
        return ResponseEntity.ok(issueService.creatSprint(sprint));
    }

    @PostMapping("/search")
    public ResponseEntity<List<Issue>>search(@RequestBody Map<String, String> filters) {
        return ResponseEntity.ok(issueService.search(filters));
    }
}