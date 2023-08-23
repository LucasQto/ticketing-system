package dev.lucas.authclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.lucas.authclient.model.user.User;
import dev.lucas.authclient.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return null;
        } 

        return user;
    }

    public void createUser(User user) {
        String encondedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encondedPassword);
        userRepository.save(user);
    }
    
}
