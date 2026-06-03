package com.TaskManagement3.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement3.Entity.UserProfileUpdate;
import com.TaskManagement3.Service.UserProfileUpdateService;

import lombok.RequiredArgsConstructor;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/user_profile_update")
@RequiredArgsConstructor
public class UserProfileUpdateController {
	
	@Autowired
	private UserProfileUpdateService userProfileService;
	
	@PutMapping("/updateUserProfile/{email}" )
	public ResponseEntity<UserProfileUpdate>updateUserProfile(@RequestBody UserProfileUpdate userProfile,@PathVariable Spring userofficialEmail){
		
		return null;
		
	}

}
