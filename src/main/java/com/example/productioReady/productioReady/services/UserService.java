package com.example.productioReady.productioReady.services;

import com.example.productioReady.productioReady.dto.SignUpDTO;
import com.example.productioReady.productioReady.dto.UserDTO;
import com.example.productioReady.productioReady.entities.UserEntity;
import com.example.productioReady.productioReady.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
       Optional<UserEntity> user = userRepository.findByEmail((signUpDTO.getEmail()));
        if(user.isPresent()){
            throw new BadCredentialsException("User with email " + signUpDTO.getEmail() + " already exists");
        }

        UserEntity toBeCreatedUser = modelMapper.map(signUpDTO, UserEntity.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        UserEntity savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDTO.class);

        // now if you will hit
        // http://localhost:8080/auth/signup
        //        with body
        //{"name": "uijj",
        //    "email": "abhi@gmail.com",
        //   "password": 1234
        //}

        //   output
        //{
        //    "email": "abhi@gmail.com",
        //    "id": 1,
        //    "password": "$2a$10$/XNye6JD8Ka89Odm7Chv2uwmakijTl81GDp/SfDFWi.WemyvNdi9a"
        //}
    }

}
// UserDetailsService is a core interface in Spring Security that is used to retrieve user-related data. It has a single method, loadUserByUsername, which is called during the authentication process to load user details based on the provided username (or email in this case). The UserService class implements this interface to provide custom user retrieval logic using the UserRepository.
// In the loadUserByUsername method, we attempt to find a user by their email using the UserRepository. If the user is found, it is returned as a UserDetails object. If not, a UsernameNotFoundException is thrown, indicating that the user does not exist in the system. This integration allows Spring Security to authenticate users based on the data stored in the database.
//By implementing UserDetailsService, the UserService class enables Spring Security to use the custom user retrieval logic defined in the loadUserByUsername method during the authentication process.


