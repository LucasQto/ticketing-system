package dev.lucas.authclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.lucas.authclient.config.TokenService;
import dev.lucas.authclient.model.user.AuthResponseDTO;
import dev.lucas.authclient.model.user.AuthenticationDTO;
import dev.lucas.authclient.model.user.ResgiterDTO;
import dev.lucas.authclient.model.user.User;
import dev.lucas.authclient.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated ResgiterDTO data) {
        if(userService.loadUserByUsername(data.getUsername()) != null) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User(data.getUsername(), data.getPassword(), data.getEmail(), data.getRole());
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }
    
}
