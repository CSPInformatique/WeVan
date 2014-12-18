package com.cspinformatique.wevan.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.wevan.security.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
