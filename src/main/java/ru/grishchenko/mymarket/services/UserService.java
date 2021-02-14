package ru.grishchenko.mymarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grishchenko.mymarket.configs.SecurityConfig;
import ru.grishchenko.mymarket.dto.UserDto;
import ru.grishchenko.mymarket.models.Role;
import ru.grishchenko.mymarket.models.User;
import ru.grishchenko.mymarket.repositories.UserRepository;


import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public String getAliasByUserName(String userName) {
        return findByUsername(userName).get().getAlias();
    }

    public Long getIdByUserName(String userName) {
        return findByUsername(userName).get().getId();
    }

    public void registerNewUser(UserDto userDto) {
        userDto.setPassword(getPassHash(userDto.getPassword()));
        User newUser = new User(userDto);
        newUser.getRoles().add(roleService.getRoleForNewUser());
        userRepository.save(newUser);
    }

    private String getPassHash(String pass) {
        return encoder.encode(pass);
    }
}