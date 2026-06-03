package com.TaskManagement3.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement3.DTO.UserProfileUpdatedDTO;
import com.TaskManagement3.Entity.UserProfileUpdate;
import com.TaskManagement3.Repository.UserProfileUpdateRepository;

@Service
public class UserProfileUpdateService {

	@Autowired
	private UserProfileUpdateRepository userProfileUpdateRepo;
	private UserProfileUpdatedDTO prfileUpdate;

	public UserProfileUpdatedDTO updateUserProfile(String userOfficialEmail, UserProfileUpdatedDTO profileUpdate) {
		
		UserProfileUpdate userProfile = userProfileUpdateRepo.findByUserOfficialEmail(userOfficialEmail)
										  .orElseThrow(()-> new RuntimeException("User not found"));
		
		userProfile.setUserName(profileUpdate.getUserName());
		userProfile.setDepartment(profileUpdate.getDeartment());
		userProfile.setDesignation(profileUpdate.getDesignation());
		userProfile.setOrganizationName(profileUpdate.getOrganizationName());
		userProfile.setActive(profileUpdate.isActive());
		
		userProfileUpdateRepo.save(userProfile);
		
		return toDTO(userProfile);
		
	}
	public List<UserProfileUpdatedDTO> getAllProfile(){
		return userProfileUpdateRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	public UserProfileUpdate getUserEmail(String userOfficialEmail) {
		return userProfileUpdateRepo.findByUserOfficialEmail(userOfficialEmail)
								   .orElseThrow(() -> new RuntimeException("user not found"));
	}
	
	private UserProfileUpdatedDTO toDTO(UserProfileUpdate userProfileUpdate) {
		
		UserProfileUpdatedDTO dto= new UserProfileUpdatedDTO();
		
		dto.setId(userProfileUpdate.getId());
		dto.setUserName(userProfileUpdate.getUserName());
		dto.setUserOfficialEmail(userProfileUpdate.getUserOfficialEmail());
		dto.setDeartment(userProfileUpdate.getDepartment());
		dto.setDesignation(userProfileUpdate.getDesignation());
		dto.setOrganizationName(userProfileUpdate.getOrganizationName());
		dto.setActive(userProfileUpdate.isActive());
		
		return dto;
		
		
		}

}
