package com.TaskManagement3.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.TaskManagement3.Entity.UserAuth;
import com.TaskManagement3.Repository.UserAuthRepository;
import com.alibaba.nacos.config.server.model.User;
import com.alibaba.nacos.console.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAuthRepository userRepo;
    private User User;

    @Override
    public UserDetails loadUserByUsername(String userOfficialEmail) throws UsernameNotFoundException {
        UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with Email : " + userOfficialEmail));

        return new CustomUserDetails(User);

    }
}