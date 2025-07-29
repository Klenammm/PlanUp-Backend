package com.group.planup.Controllers;
import com.group.planup.Models.Users;
import com.group.planup.Repository.UserRepo;
import com.group.planup.Services.UserService;
import com.group.planup.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/api/register")
    public Users register(@RequestBody Users user){
        return userService.register(user);
    }

    @PostMapping("/api/login")
    public String login(@RequestBody Users user){
        return userService.verify(user);
    }

    @GetMapping("api/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @GetMapping("api/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        return userRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("api/users/{id}/profile")
    public ResponseEntity<?> updateUserProfile(@PathVariable int id, @RequestBody UserProfile profile) {
        Optional<Users> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        Users user = userOpt.get();
        user.setName(profile.getName());
        user.setAvatar(profile.getAvatar());
        userRepo.save(user);
        return ResponseEntity.ok("Profile updated");
    }

    @GetMapping("api/me")
    public ResponseEntity<Users> getCurrentUser(@AuthenticationPrincipal Users user) {
        String username = user.getUsername();
        Users users = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return ResponseEntity.ok(users);
    }

}
