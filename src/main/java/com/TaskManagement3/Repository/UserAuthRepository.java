package com.TaskManagement3.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TaskManagement3.Entity.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
	
    Optional<UserAuth>findByUserOfficialEmail(String userOfficialEmail);
    Optional<UserAuth>findByResetToken(String resetToken);
}