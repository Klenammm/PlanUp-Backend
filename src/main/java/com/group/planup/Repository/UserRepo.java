package com.group.planup.Repository;

import com.group.planup.Models.Users;
import com.group.planup.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    List<Users> findByRole(Role role); // Optional: get users by role
}
