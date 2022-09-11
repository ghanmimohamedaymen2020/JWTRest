package com.project.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.jwt.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

}
