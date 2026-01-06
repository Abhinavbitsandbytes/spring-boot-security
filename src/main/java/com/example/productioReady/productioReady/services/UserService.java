package com.example.productioReady.productioReady.services;

import com.example.productioReady.productioReady.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with email "+ username + " not found"));
    }
}
// UserDetailsService is a core interface in Spring Security that is used to retrieve user-related data. It has a single method, loadUserByUsername, which is called during the authentication process to load user details based on the provided username (or email in this case). The UserService class implements this interface to provide custom user retrieval logic using the UserRepository.
// In the loadUserByUsername method, we attempt to find a user by their email using the UserRepository. If the user is found, it is returned as a UserDetails object. If not, a UsernameNotFoundException is thrown, indicating that the user does not exist in the system. This integration allows Spring Security to authenticate users based on the data stored in the database.
//By implementing UserDetailsService, the UserService class enables Spring Security to use the custom user retrieval logic defined in the loadUserByUsername method during the authentication process.


