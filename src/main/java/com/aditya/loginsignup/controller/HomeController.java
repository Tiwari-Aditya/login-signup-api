package com.aditya.loginsignup.controller;

import com.aditya.loginsignup.dto.LogintDto;
import com.aditya.loginsignup.dto.SignUpDto;
import com.aditya.loginsignup.entity.Role;
import com.aditya.loginsignup.entity.User;
import com.aditya.loginsignup.repository.RoleRepository;
import com.aditya.loginsignup.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HomeController {

    private AuthenticationManager authenticationManager;
//    @Autowired
    private UserRepository userRepository;
//    @Autowired
    private RoleRepository roleRepository;
//    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LogintDto logintDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logintDto.getUserName(), logintDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User Login Sucessfully.",HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser (@RequestBody SignUpDto signUpDto){
        if(userRepository.existsByUserName(signUpDto.getUserName())){
            return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
        }
        // creating user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUserName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }

}
