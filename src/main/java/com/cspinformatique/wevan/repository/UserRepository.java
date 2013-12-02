package com.cspinformatique.wevan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.wevan.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
