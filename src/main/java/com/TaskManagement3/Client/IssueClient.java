package com.TaskManagement3.Client;

import com.TaskManagement3.Enum.IssueStatus;

public interface IssueClient {

    void updateStatus(Long id, IssueStatus issueStatus, String performBy);
    
    void addComments(Long id, String author, String body);
    
}