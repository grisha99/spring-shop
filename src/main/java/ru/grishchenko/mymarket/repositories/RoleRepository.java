package ru.grishchenko.mymarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.grishchenko.mymarket.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
