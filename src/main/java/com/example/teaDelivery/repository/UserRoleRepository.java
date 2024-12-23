package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.Role;
import com.example.teaDelivery.models.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findRoleByName(UserRoles role);
}
