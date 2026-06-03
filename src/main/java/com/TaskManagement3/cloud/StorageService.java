package com.TaskManagement3.cloud;

public interface StorageService {
	
    String store(byte[] fileData, String relativeFolder);
    byte[] read(String storagePath);
    void delete(String cloudId);
}