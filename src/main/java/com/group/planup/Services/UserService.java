package com.group.planup.Services;

import com.group.planup.Models.Role;
import com.group.planup.Models.Users;
import com.group.planup.Repository.UserRepo;
import com.group.planup.Services.JWTService;
import com.group.planup.config.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user){
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
        }

        user.setPassword(encoder.encode(user.getPassword()));

        //  Set role, default to USER if null
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        return repo.save(user);
    }

    public String verify(Users user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(auth.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }else{
            return "Failed";
        }
    }
}
