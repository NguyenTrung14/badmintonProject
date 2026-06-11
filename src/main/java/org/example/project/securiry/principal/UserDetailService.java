package org.example.project.securiry.principal;

import lombok.RequiredArgsConstructor;
import org.example.project.exception.HttpNotFoundException;
import org.example.project.model.entity.User;
import org.example.project.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()->new HttpNotFoundException("User not found"));
        Collection<GrantedAuthority> role= List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()));

        return UserPrincipal.builder()
                .user(user)
                .authorities(role)
                .build();
    }
}
