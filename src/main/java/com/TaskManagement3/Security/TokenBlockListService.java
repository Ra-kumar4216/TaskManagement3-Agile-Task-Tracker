package com.TaskManagement3.Security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBlockListService {

    private final Set<String> blockLstToken = ConcurrentHashMap.newKeySet();

    public void blockToken(String token) {
        blockLstToken.add(token);
    }

    public boolean isBlock(String token) {
        return blockLstToken.contains(token);
    }

	public void addToBlockList(String token) {
		// TODO Auto-generated method stub
		
	}
}