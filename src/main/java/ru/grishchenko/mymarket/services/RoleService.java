package ru.grishchenko.mymarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.grishchenko.mymarket.models.Role;
import ru.grishchenko.mymarket.repositories.RoleRepository;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleForNewUser() {
        return roleRepository.getOne(1L);
    }
}
