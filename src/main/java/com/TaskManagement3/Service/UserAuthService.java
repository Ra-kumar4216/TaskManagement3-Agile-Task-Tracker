package com.TaskManagement3.Service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskManagement3.DTO.RegisterRequestDTO;
import com.TaskManagement3.DTO.AuthResponseDTO;
import com.TaskManagement3.DTO.LoginRequestDTO;
import com.TaskManagement3.Entity.UserAuth;
import com.TaskManagement3.Repository.UserAuthRepository;
import com.TaskManagement3.Security.EmailService;
import com.TaskManagement3.Security.JWTUtil;
import com.TaskManagement3.Security.TokenBlockListService;
import com.ning.http.client.providers.apache.ApacheResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepository userRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenBlockListService tokenBlockListService;

    private UserAuth user;
    

    public String register(RegisterRequestDTO register) {
    	
        if (userRepo.findByUserOfficialEmail(register.useOfficialEmail).isPresent()) {
        	throw new RuntimeException("User Already exist");
        }
        UserAuth user = new UserAuth();
        user.setUserName(register.userName);
        user.setUserOfficialEmail(register.useOfficialEmail);
        user.setPassword(passwordEncoder.encode(register.password));
        user.setRole(register.role);

        userRepo.save(user);

        return "User register successfully";

    }

    public AuthResponseDTO login(LoginRequestDTO login) {
        
        UserAuth user = userRepo.findByUserOfficialEmail(login.getUserOfficialEmail())
        		.orElseThrow(()-> new RuntimeException("User not found"));
        
        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
        	throw new RuntimeException("Invalid Credential");
        }
        
        String token = jwtUtil.generateToken(user);
        
        return new AuthResponseDTO(token,"Login successful");
    }

    public void forgotPassword(String userOfficialEmail) {
        
        UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));
        
        String token = UUID.randomUUID().toString();
        
        user.setResetToken(token);
        user.setRestTokenExpire(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        
        userRepo.save(user);
        
        emailService.sendResetPasswordMail(userOfficialEmail, token);
        
    }
    public void resetPassword(String token,String newPassword) {
    	
    	UserAuth user = userRepo.findByResetToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
    	
        if (user.getRestTokenExpire().before(new Date())) {
            throw new RuntimeException("Token expired");
        }
    	
    	user.setPassword(passwordEncoder.encode(newPassword));
    	user.setResetToken(null);
    	user.setRestTokenExpire(null);
    	
    	userRepo.save(user);
    }
    
    public String logOut(HttpServletRequest request) {
        String header= request.getHeader("Authorization");
        String token = jwtUtil.extractToken(header);
        if (token != null) {
            tokenBlockListService.addToBlockList(token);
        }
        return "LoggedOut successfully";
    }
    
    }