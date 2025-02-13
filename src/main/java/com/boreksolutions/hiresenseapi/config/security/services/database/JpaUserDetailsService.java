package com.boreksolutions.hiresenseapi.config.security.services.database;


import com.boreksolutions.hiresenseapi.config.security.user.SecurityUser;
import com.boreksolutions.hiresenseapi.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) {
        var u = userRepository.findByEmail(username);

        return u.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}