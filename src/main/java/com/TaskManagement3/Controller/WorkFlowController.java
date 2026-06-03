package com.TaskManagement3.Controller;


import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.TaskManagement3.Entity.WorkFlow;
import com.TaskManagement3.Service.WorkFlowService;
import com.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement3.Enum.Role;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkFlowController {

    @Autowired
    private WorkFlowService workflowService;

    @PostMapping("/create")
    public ResponseEntity<WorkFlow> createWorkFlow(@RequestBody WorkFlow workFlow){
        return ResponseEntity.ok(workflowService.createWorkFlow(workFlow));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkFlow>> getAllList(){
        return ResponseEntity.ok(workflowService.getAllwork());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlow> getWorkById(@PathVariable Long id){
        return ResponseEntity.ok(workflowService.getWorkById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkFlow> updateWork(@PathVariable Long id, @RequestBody WorkFlow workFlow){
        return ResponseEntity.ok(workflowService.updateWorkFlow(id, workFlow));
    }

    @GetMapping("/{id}/transactions/{from}")
    public ResponseEntity<Boolean> allowed(@PathVariable Long id,
            @RequestParam IssueStatus from, @RequestParam IssueStatus to, @RequestBody Set<Role> userRole){
        return ResponseEntity.ok(workflowService.isTransacionAllwed(id, from, to, userRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        workflowService.deletework(id);
        return ResponseEntity.ok("Deleted");
    }
}
